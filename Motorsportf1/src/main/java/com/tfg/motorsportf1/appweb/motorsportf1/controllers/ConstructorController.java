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
		ConstructorJson mapa;

		String valName = name.orElse(UtilityService.CADENA_VACIA);
		String valNationality = nationality.orElse(UtilityService.CADENA_VACIA);

		int validOffset = this.utilityService.getValidOffset(selectedPage);
		
		if (!StringUtil.isBlank(valName) && !StringUtil.isBlank(valNationality)) {
			
			mapa = this.constructorService.findByParameters(valName,
															valNationality,
															Optional.of(validOffset));
		} else if (!StringUtil.isBlank(valName)) {
			
			mapa = this.constructorService.findByName(valName, Optional.of(validOffset));
		} else if (!StringUtil.isBlank(valNationality)) {
			
			mapa = this.constructorService.findByNationality(valNationality, Optional.of(validOffset));
		} else {
		
			mapa = this.constructorService.findAll(Optional.of(validOffset));
		}
		
		validOffset = this.utilityService.getValidOffset(selectedPage, mapa.getTotalElements());
		log.info("Pagina seleccionada: " + validOffset);
		
		ConstructorForm constructorForm = new ConstructorForm(validOffset,
															  valName,
															  valNationality);

		ModelAndView result = this.getModelAndView(mapa, constructorForm);

		return result;
	}

	@GetMapping("/display")
	public ModelAndView display(@RequestParam("name") String name) {			
		ModelAndView result = null;
		
		String nameTrim = name.trim();
		
		Constructor constructor = this.constructorService.findOne(nameTrim);
		
		try {
			if (constructor != null) {
				result = new ModelAndView("constructor/display");
				
				Integer driversTitles = this.constructorStandingService.findDriversTitlesByConstructorAPI(
						nameTrim
				);
				
				Integer constructorTitles = this.constructorStandingService.
						findCountByConstructorAndPosition(nameTrim, "1");
				
				Integer poles = this.resultService.findCountByGridAndConstructor(nameTrim, "1");
				
				Integer victories = this.resultService.findCountByPositionAndConstructor(nameTrim, "1");
				
				Integer races = this.resultService.findCountByConstructor(nameTrim);
				
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
			log.error("Error al mostrar escudería", oops);
		}
		
		return result;
	}
	
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute ConstructorForm constructorForm,
							   BindingResult binding) {
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
		ConstructorJson mapa = this.constructorService.findAll();
		
		ModelAndView result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(ConstructorJson json, ConstructorForm constructorForm) {
		int totalPages = json.getTotalPages();
		List<Integer> pages = this.utilityService.getPages(totalPages);

		int totalElements = json.getTotalElements();

		int valid_selectedPage = constructorForm.getOffset();
		log.info("Valid offset: " + valid_selectedPage);

		List<Constructor> constructors = Arrays.asList(json.getContent());

		ModelAndView result = new ModelAndView("constructor/list");

		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("constructors", constructors);
		result.addObject("constructorForm", constructorForm);

		return result;
	}

	protected ModelAndView getModelAndView(ConstructorJson json) {	
		ConstructorForm constructorForm = new ConstructorForm();
		
		ModelAndView result = this.getModelAndView(json, constructorForm);

		return result;
	}

}
