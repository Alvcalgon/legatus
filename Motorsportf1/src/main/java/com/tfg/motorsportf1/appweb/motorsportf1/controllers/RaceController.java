package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tfg.motorsportf1.appweb.motorsportf1.forms.RaceForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.RaceService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/race")
public class RaceController {

	//private static final Log log = LogFactory.getLog(RaceController.class);
	
	@Autowired
	private RaceService raceService;
	
	@Autowired
	private UtilityService utilityService;
	
	public RaceController() {
		super();
	}
	
	
	@GetMapping("/display")
	public ModelAndView display(@RequestParam("season") String season,
								@RequestParam("event") String event) {			
		ModelAndView result;
		Object race;
		Date dateRace;
		
		race = this.raceService.findOne(season, event);
		
		if (race != null) {
			result = new ModelAndView("race/display");
			
			result.addObject("race", race);
		} else {
			result = this.getModelAndView(
					this.raceService.findBySeason(UtilityService.LAST_SEASON)
			);
		}
		
		return result;
	}
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
							 @RequestParam("season") Optional<String> season,
							 @RequestParam("event") Optional<String> event) {
		String val_season, val_event;
		int valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;
		RaceForm raceForm;
		
		val_season = season.orElse("");
		val_event = event.orElse("");
				
		if (!StringUtil.isBlank(val_event) && !StringUtil.isBlank(val_season)) {
	
			mapa = this.raceService.findBySeasonAndEvent(val_season, val_event, selectedPage);
			
		} else if (!StringUtil.isBlank(val_season)) {
			
			mapa = this.raceService.findBySeason(val_season, selectedPage);
			
		} else if (!StringUtil.isBlank(val_event)) {			
			
			mapa = this.raceService.findByEvent(val_event, selectedPage);
							
		} else {
			
			mapa = this.raceService.findBySeason(UtilityService.LAST_SEASON,
					                             selectedPage);
			
		}
		
		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");
		
		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);
		
		raceForm = new RaceForm(valid_offset, val_season, val_event);
		
		result = this.getModelAndView(mapa, raceForm);
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute RaceForm raceForm, BindingResult binding) {
		ModelAndView result;
		String season, event;
		Integer offset;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			result = this.getModelAndView(this.raceService.findBySeason(
					UtilityService.LAST_SEASON)
			);
		} else {
			// Si no hay errores de validacion, se filtran los resultados según los
			// parametros de busquedas
			season = raceForm.getSeason().trim();
			event = raceForm.getEvent().trim();
			offset = raceForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(season),
							   Optional.ofNullable(event));
		}
		
		return result;
	}
		
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute RaceForm raceForm) {
		Map<String, List<Object>> mapa;
		ModelAndView result;
		
		mapa = this.raceService.findBySeason(UtilityService.LAST_SEASON, 
						   Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa, RaceForm raceForm) {
		int totalPages, totalElements, valid_selectedPage;
		List<Object> races;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;

		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");

		totalPages = (int) dataPage.get(UtilityService.POS_TOTAL_PAGES);
		pages = this.utilityService.getPages(totalPages);

		totalElements = (int) dataPage.get(UtilityService.POS_TOTAL_ELEMENTS);

		valid_selectedPage = (int) dataPage.get(UtilityService.POS_OFFSET);

		raceForm.setOffset(valid_selectedPage);

		races = this.utilityService.getFromMap2(mapa, "races");

		result = new ModelAndView("race/list");

		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("races", races);
		result.addObject("raceForm", raceForm);

		return result;
	}

	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa) {
		ModelAndView result;
		RaceForm raceForm;

		raceForm = new RaceForm();
		result = this.getModelAndView(mapa, raceForm);

		return result;
	}
	
}
