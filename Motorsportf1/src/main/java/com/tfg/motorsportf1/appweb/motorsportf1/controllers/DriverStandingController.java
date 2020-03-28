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
		String val_season, val_position, val_driver;
		DriverStandingForm driverStandingForm;
		int valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;

		val_season = season.orElse("");
		val_position = position.orElse("");
		val_driver = driver.orElse("");

		if (!StringUtil.isBlank(val_season)) {
			mapa = this.driverStandingService.findBySeason(val_season,
																selectedPage);
		} else if (!StringUtil.isBlank(val_position)) {
			mapa = this.driverStandingService.findByPosition(val_position, 
																  selectedPage);
		} else if (!StringUtil.isBlank(val_driver)) {
			mapa = this.driverStandingService.findByDriver(val_driver, 
														   selectedPage);
		} else {
			mapa = this.driverStandingService.findBySeason(UtilityService.LAST_SEASON, 
														   selectedPage);
		}

		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");

		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);

		driverStandingForm = new DriverStandingForm(valid_offset,
													val_season,
													val_position,
													val_driver);

		result = this.getModelAndView(mapa, driverStandingForm);

		return result;
	}
	

	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverStandingForm driverStandingForm, BindingResult binding) {
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
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(season),
							   Optional.ofNullable(position),
							   Optional.ofNullable(driver));
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute DriverStandingForm driverStandingForm) {
		Map<String, List<Object>> mapa;
		ModelAndView result;
		Integer offset;
		
		offset = driverStandingForm.getOffset();
		
		mapa = this.driverStandingService.findBySeason(UtilityService.LAST_SEASON,
				                                       Optional.ofNullable(offset));
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa,
			                               DriverStandingForm driverStandingForm) {
		int totalPages, totalElements, valid_selectedPage;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;
		List<Object> driversStanding;

		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");
		
		totalPages = (int) dataPage.get(UtilityService.POS_TOTAL_PAGES);
		pages = this.utilityService.getPages(totalPages);
		
		totalElements = (int) dataPage.get(UtilityService.POS_TOTAL_ELEMENTS);
		
		valid_selectedPage = (int) dataPage.get(UtilityService.POS_OFFSET);
		
		driverStandingForm.setOffset(valid_selectedPage);
		
		driversStanding = this.utilityService.getFromMap2(mapa, "driversStanding");
		
		result = new ModelAndView("driverStanding/list");
			
		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("driversStanding", driversStanding);
		result.addObject("driverStandingForm", driverStandingForm);

		return result;
	}

	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa) {
		ModelAndView result;
		DriverStandingForm driverStandingForm;

		driverStandingForm = new DriverStandingForm();
		result = this.getModelAndView(mapa, driverStandingForm);

		return result;
	}
	
}
