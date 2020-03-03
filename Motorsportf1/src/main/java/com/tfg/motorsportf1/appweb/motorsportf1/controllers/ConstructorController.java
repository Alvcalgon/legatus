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

import com.tfg.motorsportf1.appweb.motorsportf1.forms.ConstructorForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.ConstructorService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/constructor")
public class ConstructorController {

	// private static final Log log =
	// LogFactory.getLog(ConstructorController.class);

	@Autowired
	private ConstructorService constructorService;

	@Autowired
	private UtilityService utilityService;

	public ConstructorController() {
		super();
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
			@RequestParam("limit") Optional<Integer> limit, @RequestParam("name") Optional<String> name,
			@RequestParam("country") Optional<String> country) {
		String val_country, val_name;
		int valid_limit, valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;
		ConstructorForm constructorForm;

		val_name = name.orElse("");
		val_country = country.orElse("");

		if (!StringUtil.isBlank(val_name) && !StringUtil.isBlank(val_country)) {
			mapa = this.constructorService.findByParameters(val_name, val_country, selectedPage, limit);
		} else if (!StringUtil.isBlank(val_name)) {
			mapa = this.constructorService.findByName(val_name, selectedPage, limit);
		} else if (!StringUtil.isBlank(val_country)) {
			mapa = this.constructorService.findByCountry(val_country, selectedPage, limit);
		} else {
			mapa = this.constructorService.findAll(selectedPage, limit);
		}

		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");

		valid_limit = (int) dataPage.get(UtilityService.POS_LIMIT);
		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);

		constructorForm = new ConstructorForm(valid_offset, valid_limit, val_name, val_country);

		result = this.getModelAndView(mapa, constructorForm);

		return result;
	}

	@GetMapping("/display")
	public ModelAndView display(@RequestParam("name") String name) {			
		ModelAndView result;
		Object constructor;
		
		constructor = this.constructorService.findOne(name.trim());
		
		if (constructor != null) {
			result = new ModelAndView("constructor/display");
			
			result.addObject("constructor", constructor);
		} else {
			result = this.list(Optional.empty(), 
							   Optional.empty(),
							   Optional.empty(),
							   Optional.empty());
		}
		
		return result;
	}
	
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute ConstructorForm constructorForm, BindingResult binding) {
		ModelAndView result;
		String country, name;
		Integer offset, limit;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			result = this.list(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER),
					   		   Optional.of(UtilityService.DEFAULT_LIMIT),
					   		   Optional.empty(),
					   		   Optional.empty());
		} else {
			// Si no hay errores de validacion, se filtran los pilotos según los
			// parametros de busquedas
			country = constructorForm.getCountry().trim();
			name = constructorForm.getName().trim();
			limit = constructorForm.getLimit();
			offset = constructorForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(limit),
							   Optional.ofNullable(name),
							   Optional.ofNullable(country));
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute ConstructorForm constructorForm) {
		Map<String, List<Object>> mapa;
		ModelAndView result;
		
		mapa = this.constructorService.findAll();
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa, ConstructorForm constructorForm) {
		int totalPages, totalElements, valid_selectedPage, valid_limit, limit;
		List<Object> constructors;
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

		constructorForm.setLimit(valid_limit);
		constructorForm.setOffset(valid_selectedPage);

		constructors = this.utilityService.getFromMap2(mapa, "constructors");

		result = new ModelAndView("constructor/list");

		result.addObject("totalElements", totalElements);
		result.addObject("limit", valid_limit);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("constructors", constructors);
		result.addObject("constructorForm", constructorForm);

		return result;
	}

	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa) {
		ModelAndView result;
		ConstructorForm constructorForm;

		constructorForm = new ConstructorForm();
		result = this.getModelAndView(mapa, constructorForm);

		return result;
	}

}
