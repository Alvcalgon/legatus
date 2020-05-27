package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.Arrays;
import java.util.List;
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

import com.tfg.motorsportf1.appweb.motorsportf1.bean.ConstructorJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Constructor;
import com.tfg.motorsportf1.appweb.motorsportf1.forms.ConstructorForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.ConstructorService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.ConstructorStandingService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.ResultService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/constructor")
public class ConstructorController {

	private static final Log log = LogFactory.getLog(ConstructorController.class);

	@Autowired
	private ConstructorService constructorService;

	@Autowired
	private ResultService resultService;
	
	@Autowired
	private ConstructorStandingService constructorStandingService;
	
	@Autowired
	private UtilityService utilityService;

	public ConstructorController() {
		super();
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
			@RequestParam("name") Optional<String> name,
			@RequestParam("nationality") Optional<String> nationality) {
		String val_nationality, val_name;
		int valid_offset;
		ConstructorJson mapa;
		ModelAndView result;
		ConstructorForm constructorForm;

		val_name = name.orElse(UtilityService.CADENA_VACIA);
		val_nationality = nationality.orElse(UtilityService.CADENA_VACIA);

		if (!StringUtil.isBlank(val_name) && !StringUtil.isBlank(val_nationality)) {
			
			mapa = this.constructorService.findByParameters(val_name, val_nationality, selectedPage);
		
		} else if (!StringUtil.isBlank(val_name)) {
			
			mapa = this.constructorService.findByName(val_name, selectedPage);
		
		} else if (!StringUtil.isBlank(val_nationality)) {
			
			mapa = this.constructorService.findByNationality(val_nationality, selectedPage);
		
		} else {
		
			mapa = this.constructorService.findAll(selectedPage);
		
		}

		valid_offset = (int) mapa.getNumber();

		constructorForm = new ConstructorForm(valid_offset, val_name, val_nationality);

		result = this.getModelAndView(mapa, constructorForm);

		return result;
	}

	@GetMapping("/display")
	public ModelAndView display(@RequestParam("name") String name) {			
		ModelAndView result;
		Constructor constructor;
		String nameTrim;
		Integer driversTitles;
		Integer constructorTitles;
		Integer poles;
		Integer victories;
		Integer races;
		
		nameTrim = name.trim();
		
		constructor = this.constructorService.findOne(nameTrim);
		
		try {
			if (constructor != null) {
				result = new ModelAndView("constructor/display");
				
				driversTitles = this.constructorStandingService.findDriversTitlesByConstructorAPI(nameTrim);
				constructorTitles = this.constructorStandingService.findCountByConstructorAndPosition(nameTrim, "1");
				poles = this.resultService.findCountByGridAndConstructor(nameTrim, "1");
				victories = this.resultService.findCountByPositionAndConstructor(nameTrim, "1");
				races = this.resultService.findCountByConstructor(nameTrim);
				
				result.addObject("driversTitles", driversTitles);
				result.addObject("constructorTitles", constructorTitles);
				result.addObject("victories", victories);
				result.addObject("poles", poles);
				result.addObject("races", races);
				result.addObject("constructor", constructor);
			} else {
				result = this.getModelAndView(this.constructorService.findAll());
			}
		} catch (Throwable oops) {
			result = this.getModelAndView(this.constructorService.findAll());
		}
		
		return result;
	}
	
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute ConstructorForm constructorForm, BindingResult binding) {
		ModelAndView result;
		String nationality, name;
		Integer offset;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos las escuderias
			result = this.getModelAndView(this.constructorService.findAll(),
										  constructorForm);
		} else {
			// Si no hay errores de validacion, se filtran las escuderias según los
			// parametros de busquedas
			nationality = constructorForm.getNationality().trim();
			name = constructorForm.getName().trim();
			offset = constructorForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(name),
							   Optional.ofNullable(nationality));
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute ConstructorForm constructorForm) {
		ConstructorJson mapa;
		ModelAndView result;
		
		mapa = this.constructorService.findAll();
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(ConstructorJson json, ConstructorForm constructorForm) {
		int totalPages, totalElements, valid_selectedPage;
		List<Constructor> constructors;
		ModelAndView result;
		List<Integer> pages;

		totalPages = json.getTotalPages();
		pages = this.utilityService.getPages(totalPages);

		totalElements = json.getTotalElements();

		valid_selectedPage = json.getNumber() + 1;
		log.info("Valid offset: " + valid_selectedPage);

		constructorForm.setOffset(valid_selectedPage);

		constructors = Arrays.asList(json.getContent());

		result = new ModelAndView("constructor/list");

		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("constructors", constructors);
		result.addObject("constructorForm", constructorForm);

		return result;
	}

	protected ModelAndView getModelAndView(ConstructorJson json) {
		ModelAndView result;
		ConstructorForm constructorForm;

		constructorForm = new ConstructorForm();
		result = this.getModelAndView(json, constructorForm);

		return result;
	}

}
