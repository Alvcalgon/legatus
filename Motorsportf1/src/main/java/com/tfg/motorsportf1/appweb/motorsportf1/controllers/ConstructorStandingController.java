package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.Arrays;
import java.util.List;
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

import com.tfg.motorsportf1.appweb.motorsportf1.bean.ConstructorStandingJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.ConstructorStanding;
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
		ConstructorStandingJson json;
	
		String val_season = season.orElse(UtilityService.CADENA_VACIA);
		String val_position = position.orElse(UtilityService.CADENA_VACIA);
		String val_constructor = constructor.orElse(UtilityService.CADENA_VACIA);

		if (!StringUtil.isBlank(val_season)) {
			json = this.constructorStandingService.findBySeason(val_season,
																selectedPage);
		} else if (!StringUtil.isBlank(val_position)) {
			json = this.constructorStandingService.findByPosition(val_position, 
																  selectedPage);
		} else if (!StringUtil.isBlank(val_constructor)) {
			json = this.constructorStandingService.findByConstructor(val_constructor, 
																	 selectedPage);
		} else {
			json = this.constructorStandingService.findBySeason(UtilityService.LAST_SEASON, 
																selectedPage);
		}

		int valid_offset = json.getNumber();

		ConstructorStandingForm constructorStandingForm = new ConstructorStandingForm(valid_offset,
															  val_season,
															  val_position,
															  val_constructor);

		ModelAndView result = this.getModelAndView(json, constructorStandingForm);

		return result;
	}


	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute ConstructorStandingForm constructorStandingForm, BindingResult binding) {
		ModelAndView result;
		String season, position, constructor;
		Integer offset;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envia la clasificación general de 2018
			result = this.getModelAndView(
					        this.constructorStandingService.findBySeason(
							UtilityService.LAST_SEASON), constructorStandingForm);
			
		} else {
			// Si no hay errores de validacion, se filtran los constructor
			// standing según los parametros de busquedas
			season = constructorStandingForm.getSeason();
			position = constructorStandingForm.getPosition();
			constructor = constructorStandingForm.getConstructor();
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
		int offset = constructorStandingForm.getOffset();
		
		ConstructorStandingJson json = this.constructorStandingService.findBySeason(
				  UtilityService.LAST_SEASON,
				  Optional.ofNullable(offset));
	
		ModelAndView result = this.getModelAndView(json);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(ConstructorStandingJson json,
										   ConstructorStandingForm constructorStandingForm) {
		List<Integer> pages;
		List<ConstructorStanding> constructorsStanding;
		
		int totalPages = json.getTotalPages();
		pages = this.utilityService.getPages(totalPages);
		
		int totalElements = json.getTotalElements();
						
		int valid_selectedPage = constructorStandingForm.getOffset() + 1;
		
		constructorStandingForm.setOffset(valid_selectedPage);
			
		constructorsStanding = Arrays.asList(json.getContent());
		
		ModelAndView result = new ModelAndView("constructorStanding/list");
		
		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("constructorsStanding", constructorsStanding);
		result.addObject("constructorStandingForm", constructorStandingForm);

		return result;
	}

	protected ModelAndView getModelAndView(ConstructorStandingJson json) {
		ConstructorStandingForm constructorStandingForm = new ConstructorStandingForm();
		
		ModelAndView result = this.getModelAndView(json, constructorStandingForm);

		return result;
	}

}
