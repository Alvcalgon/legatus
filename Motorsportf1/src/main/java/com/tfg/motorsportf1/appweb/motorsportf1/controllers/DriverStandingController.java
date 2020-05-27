package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.ArrayList;
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

import com.tfg.motorsportf1.appweb.motorsportf1.bean.DriverStandingJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.DriverStanding;
import com.tfg.motorsportf1.appweb.motorsportf1.forms.DriverStandingForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.DriverStandingService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/driver-standing")
public class DriverStandingController {

	//private static final Log log = LogFactory.getLog(DriverStandingController.class);
	
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
	
		String val_season = season.orElse(UtilityService.CADENA_VACIA);
		String val_position = position.orElse(UtilityService.CADENA_VACIA);
		String val_driver = driver.orElse(UtilityService.CADENA_VACIA);

		if (!StringUtil.isBlank(val_season)) {
			
			json = this.driverStandingService.findBySeason(val_season,
																selectedPage);
		} else if (!StringUtil.isBlank(val_position)) {
			
			json = this.driverStandingService.findByPosition(val_position, 
																  selectedPage);
		} else if (!StringUtil.isBlank(val_driver)) {
			
			json = this.driverStandingService.findByDriver(val_driver, 
														   selectedPage);
		} else {
			
			json = this.driverStandingService.findBySeason(UtilityService.LAST_SEASON, 
														   selectedPage);
		}

		int valid_offset = json.getNumber();

		DriverStandingForm driverStandingForm = new DriverStandingForm(valid_offset, val_season,
													val_position, val_driver);

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
		List<Integer> pages;
		List<DriverStanding> driversStanding;
		
		int totalPages = json.getTotalPages();
		pages = this.utilityService.getPages(totalPages);
		
		int totalElements = json.getTotalElements();
		
		int valid_selectedPage = driverStandingForm.getOffset() + 1;
		
		driverStandingForm.setOffset(valid_selectedPage);
		
		driversStanding = new ArrayList<DriverStanding>(Arrays.asList(json.getContent()));
		
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
		ModelAndView result;
		DriverStandingForm driverStandingForm;

		driverStandingForm = new DriverStandingForm();
		result = this.getModelAndView(json, driverStandingForm);

		return result;
	}
	
}
