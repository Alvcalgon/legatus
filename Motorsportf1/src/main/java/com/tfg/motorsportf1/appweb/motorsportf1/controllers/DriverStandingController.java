package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.ArrayList;
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

import com.tfg.motorsportf1.appweb.motorsportf1.bean.DriverStandingJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.DriverStanding;
import com.tfg.motorsportf1.appweb.motorsportf1.forms.DriverStandingForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.DriverStandingService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/driver-standing")
public class DriverStandingController {

	private static final Log log = LogFactory.getLog(DriverStandingController.class);
	
	@Autowired
	private DriverStandingService driverStandingService;
	
	@Autowired
	private  UtilityService utilityService;
	
	
	public DriverStandingController() {
		super();
	}
	
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
			 				 @RequestParam("season") Optional<String> season,
			 				 @RequestParam("position") Optional<String> position,
			 				 @RequestParam("driver") Optional<String> driver) {
		DriverStandingJson json;
	
		String valSeason = season.orElse(UtilityService.CADENA_VACIA);
		String valPosition = position.orElse(UtilityService.CADENA_VACIA);
		String valDriver = driver.orElse(UtilityService.CADENA_VACIA);

		int validOffset = this.utilityService.getValidOffset(selectedPage);
		
		if (!StringUtil.isBlank(valSeason)) {
			
			json = this.driverStandingService.findBySeason(valSeason,
														   selectedPage);
		} else if (!StringUtil.isBlank(valPosition)) {
			
			json = this.driverStandingService.findByPosition(valPosition, 
														     selectedPage);
		} else if (!StringUtil.isBlank(valDriver)) {
			
			json = this.driverStandingService.findByDriver(valDriver, 
														   selectedPage);
		} else {
			
			json = this.driverStandingService.findBySeason(UtilityService.LAST_SEASON, 
														   selectedPage);
		}

		validOffset = this.utilityService.getValidOffset(selectedPage, json.getTotalElements());
		log.info("Pagina seleccionada: " + validOffset);
		
		DriverStandingForm driverStandingForm = new DriverStandingForm(validOffset, valSeason,
																	   valPosition, valDriver);

		ModelAndView result = this.getModelAndView(json, driverStandingForm);

		return result;
	}
	

	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverStandingForm driverStandingForm,
							   BindingResult binding) {
		ModelAndView result;
		String season, position, driver;
		Integer offset;
		
		if (binding.hasErrors()) {
			result = this.list(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER),
							   Optional.empty(),
							   Optional.empty(),
							   Optional.empty());
		} else {
			// Si no hay errores de validacion, se filtran los constructor
			// standing según los parametros de busquedas
			season = driverStandingForm.getSeason();
			position = driverStandingForm.getPosition();
			driver = driverStandingForm.getDriver();
			offset = driverStandingForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset), Optional.ofNullable(season),
							   Optional.ofNullable(position), Optional.ofNullable(driver));
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute DriverStandingForm driverStandingForm) {
		Integer offset = driverStandingForm.getOffset();
		
		DriverStandingJson json = this.driverStandingService.findBySeason(UtilityService.LAST_SEASON,
				                                       Optional.ofNullable(offset));
		
		ModelAndView result = this.getModelAndView(json);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(DriverStandingJson json,
			                               DriverStandingForm driverStandingForm) {
		int totalPages = json.getTotalPages();
		List<Integer> pages = this.utilityService.getPages(totalPages);
		
		int totalElements = json.getTotalElements();
		
		int valid_selectedPage = driverStandingForm.getOffset();
		
		driverStandingForm.setOffset(valid_selectedPage);
		
		List<DriverStanding> driversStanding = new ArrayList<DriverStanding>(
					Arrays.asList(json.getContent())
		);
		
		ModelAndView result = new ModelAndView("driverStanding/list");
			
		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("driversStanding", driversStanding);
		result.addObject("driverStandingForm", driverStandingForm);

		return result;
	}

	protected ModelAndView getModelAndView(DriverStandingJson json) {
		DriverStandingForm driverStandingForm = new DriverStandingForm();
		
		ModelAndView result = this.getModelAndView(json, driverStandingForm);

		return result;
	}
	
}
