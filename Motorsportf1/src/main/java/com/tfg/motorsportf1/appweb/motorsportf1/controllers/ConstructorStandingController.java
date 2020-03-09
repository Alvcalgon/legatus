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

import com.tfg.motorsportf1.appweb.motorsportf1.forms.ConstructorStandingForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.ConstructorStandingService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/constructor-standing")
public class ConstructorStandingController {

	//private static final Log log = LogFactory.getLog(ConstructorStandingController.class);

	@Autowired
	private ConstructorStandingService constructorStandingService;

	@Autowired
	private UtilityService utilityService;

	
	public ConstructorStandingController() {
		super();
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
							 @RequestParam("season") Optional<String> season,
							 @RequestParam("position") Optional<String> position,
							 @RequestParam("constructor") Optional<String> constructor) {
		String val_season, val_position, val_constructor;
		ConstructorStandingForm constructorStandingForm;
		int valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;

		val_season = season.orElse("");
		val_position = position.orElse("");
		val_constructor = constructor.orElse("");

		if (!StringUtil.isBlank(val_season)) {
			mapa = this.constructorStandingService.findBySeason(val_season,
																selectedPage);
		} else if (!StringUtil.isBlank(val_position)) {
			mapa = this.constructorStandingService.findByPosition(val_position, 
																  selectedPage);
		} else if (!StringUtil.isBlank(val_constructor)) {
			mapa = this.constructorStandingService.findByConstructor(val_constructor, 
																	 selectedPage);
		} else {
			mapa = this.constructorStandingService.findBySeason("2018", 
																selectedPage);
		}

		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");

		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);

		constructorStandingForm = new ConstructorStandingForm(valid_offset,
															  val_season,
															  val_position,
															  val_constructor);

		result = this.getModelAndView(mapa, constructorStandingForm);

		return result;
	}


	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute ConstructorStandingForm constructorStandingForm, BindingResult binding) {
		ModelAndView result;
		String season, position, constructor;
		Integer offset;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			result = this.list(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER),
							   Optional.empty(),
							   Optional.empty(),
							   Optional.empty());
			
		} else {
			// Si no hay errores de validacion, se filtran los constructor
			// standing según los parametros de busquedas
			season = constructorStandingForm.getSeason().trim();
			position = constructorStandingForm.getPosition().trim();
			constructor = constructorStandingForm.getConstructor().trim();
			offset = constructorStandingForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(season),
							   Optional.ofNullable(position),
							   Optional.ofNullable(constructor));	
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute ConstructorStandingForm constructorStandingForm) {
		Map<String, List<Object>> mapa;
		ModelAndView result;
		int offset;
		
		offset = constructorStandingForm.getOffset();
		
		mapa = this.constructorStandingService.findBySeason("2018", Optional.ofNullable(offset));
	
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa, ConstructorStandingForm constructorStandingForm) {
		int totalPages, totalElements, valid_selectedPage;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;
		List<Object> constructorsStanding;

		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");
		
		totalPages = (int) dataPage.get(UtilityService.POS_TOTAL_PAGES);
		pages = this.utilityService.getPages(totalPages);
		
		totalElements = (int) dataPage.get(UtilityService.POS_TOTAL_ELEMENTS);
						
		valid_selectedPage = (int) dataPage.get(UtilityService.POS_OFFSET);
		
		constructorStandingForm.setOffset(valid_selectedPage);
			
		constructorsStanding = this.utilityService.getFromMap2(mapa, "constructorsStanding");
		
		result = new ModelAndView("constructorStanding/list");
		
		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("constructorsStanding", constructorsStanding);
		result.addObject("constructorStandingForm", constructorStandingForm);

		return result;
	}

	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa) {
		ModelAndView result;
		ConstructorStandingForm constructorStandingForm;

		constructorStandingForm = new ConstructorStandingForm();
		result = this.getModelAndView(mapa, constructorStandingForm);

		return result;
	}

}
