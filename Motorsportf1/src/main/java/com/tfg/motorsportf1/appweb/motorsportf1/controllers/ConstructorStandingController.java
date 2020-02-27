package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private static final Log log = LogFactory.getLog(ConstructorStandingController.class);

	@Autowired
	private ConstructorStandingService constructorStandingService;

	@Autowired
	private UtilityService utilityService;

	
	public ConstructorStandingController() {
		super();
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
							 @RequestParam("limit") Optional<Integer> limit,
							 @RequestParam("season") Optional<String> season,
							 @RequestParam("position") Optional<String> position,
							 @RequestParam("constructor") Optional<String> constructor) {
		String val_season, val_position, val_constructor;
		ConstructorStandingForm constructorStandingForm;
		int valid_limit, valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;

		val_season = season.orElse(null);
		val_position = position.orElse(null);
		val_constructor = constructor.orElse(null);

		if (!StringUtil.isBlank(val_season)) {
			mapa = this.constructorStandingService.findBySeason(val_season,
																selectedPage,
																limit);
		} else if (!StringUtil.isBlank(val_position)) {
			mapa = this.constructorStandingService.findByPosition(val_position, 
																  selectedPage,
																  limit);
		} else if (!StringUtil.isBlank(val_constructor)) {
			mapa = this.constructorStandingService.findByConstructor(val_constructor, 
																	 selectedPage,
																	 limit);
		} else {
			mapa = this.constructorStandingService.findBySeason("2018", 
																selectedPage,
																limit);
		}

		dataPage = mapa.get("dataPage");

		valid_limit = (int) dataPage.get(UtilityService.POS_LIMIT);
		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);

		constructorStandingForm = new ConstructorStandingForm(valid_offset, valid_limit, val_season, val_position,
				val_constructor);

		result = this.getModelAndView(mapa, constructorStandingForm);

		return result;
	}

	@PostMapping(value = "/list", params = "update")
	public ModelAndView update(@Valid @ModelAttribute ConstructorStandingForm constructorStandingForm, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se muestra la primera pagina
			result = this.list(Optional.of(UtilityService.DEFAULT_OFFSET_TO_API),
							   Optional.of(UtilityService.DEFAULT_LIMIT),
							   Optional.empty(),
							   Optional.empty(),
							   Optional.empty());
		} else {
			result = this.list(Optional.ofNullable(constructorStandingForm.getOffset()), 
					  		   Optional.ofNullable(constructorStandingForm.getLimit()),
					  		   Optional.ofNullable(constructorStandingForm.getSeason()),
					  		   Optional.ofNullable(constructorStandingForm.getPosition()),
					  		   Optional.ofNullable(constructorStandingForm.getConstructor()));
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute ConstructorStandingForm constructorStandingForm, BindingResult binding) {
		ModelAndView result;
		Map<String, List<Object>> mapa;
		String season, position, constructor;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			mapa = new HashMap<String, List<Object>>();
			
		} else {
			//TODO: Si no hay errores de validacion, se filtran los constructor
			// standing según los parametros de busquedas
			season = constructorStandingForm.getSeason().trim();
			position = constructorStandingForm.getPosition().trim();
			constructor = constructorStandingForm.getConstructor().trim();
			
			if (!StringUtil.isBlank(season)) {
				mapa = this.constructorStandingService.findBySeason(season,
						Optional.ofNullable(constructorStandingForm.getOffset()),
						Optional.ofNullable(constructorStandingForm.getLimit()));
			
			} else if (!StringUtil.isBlank(position)) {
				mapa = this.constructorStandingService.findByPosition(position,
								Optional.ofNullable(constructorStandingForm.getOffset()),
								Optional.ofNullable(constructorStandingForm.getLimit()));
			} else if (!StringUtil.isBlank(constructor)) {
				mapa = this.constructorStandingService.findByConstructor(constructor, 
						Optional.ofNullable(constructorStandingForm.getOffset()),
						Optional.ofNullable(constructorStandingForm.getLimit()));
			} else {
				//TODO: vacio no puede ir
				mapa = new HashMap<String, List<Object>>();
			}
			
		}
		
		result = this.getModelAndView(mapa, constructorStandingForm);
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute ConstructorStandingForm constructorStandingForm) {
		Map<String, List<Object>> mapa;
		ModelAndView result;
		
		mapa = this.constructorStandingService.findBySeason("2018",
									Optional.ofNullable(constructorStandingForm.getOffset()),
									Optional.ofNullable(constructorStandingForm.getLimit()));
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa, ConstructorStandingForm constructorStandingForm) {
		int totalPages, totalElements, valid_selectedPage, valid_limit, limit;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;
		List<Object> constructorsStanding;

		result = new ModelAndView("constructorStanding/list");
		
		if (!mapa.isEmpty()) {
			dataPage = mapa.get("dataPage");

			totalPages = (int) dataPage.get(UtilityService.POS_TOTAL_PAGES);
			pages = this.utilityService.getPages(totalPages);

			totalElements = (int) dataPage.get(UtilityService.POS_TOTAL_ELEMENTS);
			
			limit = (int) dataPage.get(UtilityService.POS_LIMIT);
			valid_limit = (limit > totalElements) ? totalElements : limit;
			
			valid_selectedPage = (int) dataPage.get(UtilityService.POS_OFFSET);
		
			constructorStandingForm.setLimit(valid_limit);
			constructorStandingForm.setOffset(valid_selectedPage);
			
			constructorsStanding = mapa.get("constructorsStanding");
			
			result.addObject("totalElements", totalElements);
			result.addObject("limit", valid_limit);
			result.addObject("selectedPage", valid_selectedPage);
			result.addObject("totalPages", totalPages);
			result.addObject("pages", pages);
			
		} else {
			constructorsStanding = new ArrayList<Object>();
		}

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
