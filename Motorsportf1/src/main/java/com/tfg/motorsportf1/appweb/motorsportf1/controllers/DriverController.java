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

import com.tfg.motorsportf1.appweb.motorsportf1.bean.DriverJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;
import com.tfg.motorsportf1.appweb.motorsportf1.forms.DriverForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.DriverService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.DriverStandingService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.ResultService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/driver")
public class DriverController {

	private static final Log log = LogFactory.getLog(DriverController.class);
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private DriverStandingService driverStandingService;
	
	@Autowired
	private ResultService resultService;
	
	@Autowired
	private UtilityService utilityService;
	
	
	public DriverController() {
		super();
	}
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
							 @RequestParam("fullname") Optional<String> fullname,
							 @RequestParam("nationality") Optional<String> nationality) {
		DriverJson json;
		
		String valFullname = fullname.orElse(UtilityService.CADENA_VACIA);
		String valNationality = nationality.orElse(UtilityService.CADENA_VACIA);
		
		int validOffset = this.utilityService.getValidOffset(selectedPage);
			
		if (!StringUtil.isBlank(valFullname) && !StringUtil.isBlank(valNationality)) {
			json = this.driverService.findByParameters(valFullname,
													   valNationality,
													   Optional.of(validOffset));
		} else if (!StringUtil.isBlank(valFullname)) {
			json = this.driverService.findByFullname(valFullname,
													 Optional.of(validOffset));
		} else if (!StringUtil.isBlank(valNationality)) {			
			json = this.driverService.findByNationality(valNationality,
														Optional.of(validOffset));
		} else {
			json = this.driverService.findAll(Optional.of(validOffset));
		}
		
		validOffset = this.utilityService.getValidOffset(selectedPage, json.getTotalElements());
		log.info("Selected page by user: " + validOffset);
		
		DriverForm driverForm = new DriverForm(validOffset, valFullname, valNationality);
		
		ModelAndView result = this.getModelAndView(json, driverForm);
		
		return result;
	}
	
	@GetMapping("/display")
	public ModelAndView display(@RequestParam("fullname") String fullname) {			
		ModelAndView result;
		
		String fullnameTrim = fullname.trim();
		
		Driver driver = this.driverService.findOne(fullnameTrim);
		
		try {
			if (driver != null) {
				result = new ModelAndView("driver/display");
								
				Integer races = this.resultService.findCountByDriver(fullnameTrim);
				Integer victories = this.resultService.findCountByPositionAndDriver(
						fullnameTrim, "1"
				);
				Integer podiums = this.driverService.getPodiums(fullnameTrim);
				Integer poles = this.resultService.findCountByGridAndDriver(fullnameTrim, "1");
				Integer titles = this.driverStandingService.findCountByDriverAndPosition(
						fullnameTrim, "1"
				);
				result.addObject("driver", driver);
				result.addObject("races", races);
				result.addObject("victories", victories);
				result.addObject("podiums", podiums);
				result.addObject("poles", poles);
				result.addObject("titles", titles);
			} else {
				result = this.getModelAndView(this.driverService.findAll());
			}
		} catch (Throwable oops) {
			result = this.getModelAndView(this.driverService.findAll());
			log.error("Error al recuperar piloto", oops);
		}
		
		return result;
	}
			
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverForm driverForm,
							   BindingResult binding) {
		ModelAndView result;
		String nationality, fullname;
		Integer offset;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			result = this.getModelAndView(this.driverService.findAll(), driverForm);
		} else {
			// Si no hay errores de validacion, se filtran los pilotos según los
			// parametros de busquedas
			nationality = driverForm.getNationality().trim();
			fullname = driverForm.getFullname().trim();
			offset = driverForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(fullname),
							   Optional.ofNullable(nationality));
		}
		
		return result;
	}
	
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute DriverForm driverForm) {
		DriverJson json = this.driverService.findAll();
		
		ModelAndView result = this.getModelAndView(json);
		
		return result;
	}
	
	
	protected ModelAndView getModelAndView(DriverJson json, 
										DriverForm driverForm) {
		int totalPages = json.getTotalPages();
		List<Integer> pages = this.utilityService.getPages(totalPages);
	
		int totalElements = json.getTotalElements();
				
		int validSelectedPage = driverForm.getOffset();
		
		List<Driver> drivers = Arrays.asList(json.getContent());
		
		ModelAndView result = new ModelAndView("driver/list");
	
		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", validSelectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("drivers", drivers);
		result.addObject("driverForm", driverForm);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(DriverJson json) {
		DriverForm driverForm = new DriverForm();
		
		ModelAndView result = this.getModelAndView(json, driverForm);
		
		return result;
	}
		
}
