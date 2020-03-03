package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

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

import com.tfg.motorsportf1.appweb.motorsportf1.forms.CircuitForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.CircuitService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/circuit")
public class CircuitController {

	//private static final Log log = LogFactory.getLog(CircuitController.class);

	@Autowired
	private CircuitService circuitService;

	@Autowired
	private UtilityService utilityService;

	
	public CircuitController() {
		super();
	}


	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
							 @RequestParam("limit") Optional<Integer> limit,
							 @RequestParam("type") Optional<String> type,
							 @RequestParam("location") Optional<String> location,
							 @RequestParam("season") Optional<String> season,
							 @RequestParam("name") Optional<String> name) {
		String val_type, val_location, val_season, val_name;
		int valid_limit, valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;
		CircuitForm circuitForm;
		
		val_name = name.orElse("");
		val_type = type.orElse("");
		val_season = season.orElse("");
		val_location = location.orElse("");
				
		if (!StringUtil.isBlank(val_name) 
				&& !StringUtil.isBlank(val_type)
				&& !StringUtil.isBlank(val_location)
				&& !StringUtil.isBlank(val_season)) {
			
			mapa = this.circuitService.findByType(val_type, selectedPage, limit);
			
		} else if (!StringUtil.isBlank(val_type)) {
			
			mapa = this.circuitService.findByType(val_type, selectedPage, limit);
			
		} else if (!StringUtil.isBlank(val_location)) {			
			
			mapa = this.circuitService.findByLocation(val_location, selectedPage, limit);
		
		} else if (!StringUtil.isBlank(val_season)) {			
				
			mapa = this.circuitService.findBySeason(val_season, selectedPage, limit);
				
		} else if (!StringUtil.isBlank(val_name)) {			
			
			mapa = this.circuitService.findByName(val_name, selectedPage, limit);
				
		} else {
			
			mapa = this.circuitService.findAll();
		
		}
		
		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");
		
		
		valid_limit = (int) dataPage.get(UtilityService.POS_LIMIT);
		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);
		
		circuitForm = new CircuitForm(valid_offset,
									  valid_limit,
									  val_type,
									  val_location,
									  val_season,
									  val_name);
		
		result = this.getModelAndView(mapa, circuitForm);
		
		return result;
	}
	
	@GetMapping("/display")
	public ModelAndView display(@RequestParam("name") String name) {			
		ModelAndView result;
		Object circuit;
		
		circuit = this.circuitService.findOne(name);
		
		if (circuit != null) {
			result = new ModelAndView("circuit/display");
			result.addObject("circuit", circuit);
		} else {
			result = this.list(Optional.empty(), 
							   Optional.empty(),
							   Optional.empty(),
							   Optional.empty(),
							   Optional.empty(),
							   Optional.empty());
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute CircuitForm circuitForm, BindingResult binding) {
		ModelAndView result;
		String name, type, location, season;
		Integer offset, limit;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			result = this.list(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER),
					   		   Optional.of(UtilityService.DEFAULT_LIMIT),
					   		   Optional.empty(),
					   		   Optional.empty(),
					   		   Optional.empty(),
					   		   Optional.empty());
		} else {
			// Si no hay errores de validacion, se filtran los pilotos según los
			// parametros de busquedas
			type = circuitForm.getType().trim();
			location = circuitForm.getLocation().trim();
			season = circuitForm.getSeason().trim();
			name = circuitForm.getName().trim();
			limit = circuitForm.getLimit();
			offset = circuitForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(limit),
							   Optional.ofNullable(type),
							   Optional.ofNullable(location),
							   Optional.ofNullable(season),
							   Optional.ofNullable(name));
		}
		
		return result;
	}
	
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute CircuitForm circuitForm) {
		Map<String, List<Object>> mapa;
		ModelAndView result;
		
		mapa = this.circuitService.findAll();
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa, CircuitForm circuitForm) {
		int totalPages, totalElements, valid_selectedPage, valid_limit, limit;
		List<Object> circuits;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;

		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");

		totalPages = (int) dataPage.get(UtilityService.POS_TOTAL_PAGES);
		pages = this.utilityService.getPages(totalPages);

		totalElements = (int) dataPage.get(UtilityService.POS_TOTAL_ELEMENTS);

		if (totalElements > 0) {
			limit = (int) dataPage.get(UtilityService.POS_LIMIT);
			valid_limit = (limit > totalElements) ? totalElements : limit;
		} else {
			valid_limit = 1;
		}

		valid_selectedPage = (int) dataPage.get(UtilityService.POS_OFFSET);

		circuitForm.setLimit(valid_limit);
		circuitForm.setOffset(valid_selectedPage);

		circuits = this.utilityService.getFromMap2(mapa, "circuits");

		result = new ModelAndView("circuit/list");

		result.addObject("totalElements", totalElements);
		result.addObject("limit", valid_limit);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("circuits", circuits);
		result.addObject("circuitForm", circuitForm);

		return result;
	}

	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa) {
		ModelAndView result;
		CircuitForm circuitForm;

		circuitForm = new CircuitForm();
		result = this.getModelAndView(mapa, circuitForm);

		return result;
	}

}
