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
			 @RequestParam("limit") Optional<Integer> limit,
			 @RequestParam("season") Optional<String> season,
			 @RequestParam("position") Optional<String> position,
			 @RequestParam("driver") Optional<String> driver) {
		String val_season, val_position, val_driver;
		DriverStandingForm driverStandingForm;
		int valid_limit, valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;

		val_season = season.orElse(null);
		val_position = position.orElse(null);
		val_driver = driver.orElse(null);

		if (!StringUtil.isBlank(val_season)) {
			mapa = this.driverStandingService.findBySeason(val_season,
																selectedPage,
																limit);
		} else if (!StringUtil.isBlank(val_position)) {
			mapa = this.driverStandingService.findByPosition(val_position, 
																  selectedPage,
																  limit);
		} else if (!StringUtil.isBlank(val_driver)) {
			mapa = this.driverStandingService.findByDriver(val_driver, 
														   selectedPage,
														   limit);
		} else {
			mapa = this.driverStandingService.findBySeason("2018", 
														   selectedPage,
														   limit);
		}

		dataPage = mapa.get("dataPage");

		valid_limit = (int) dataPage.get(UtilityService.POS_LIMIT);
		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);

		driverStandingForm = new DriverStandingForm(valid_offset, 
													valid_limit,
													val_season,
													val_position,
													val_driver);

		result = this.getModelAndView(mapa, driverStandingForm);

		return result;
	}
	
	@PostMapping(value = "/list", params = "update")
	public ModelAndView update(@Valid @ModelAttribute DriverStandingForm driverStandingForm, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se muestra la primera pagina
			result = this.list(Optional.of(UtilityService.DEFAULT_OFFSET_TO_API),
							   Optional.of(UtilityService.DEFAULT_LIMIT),
							   Optional.empty(),
							   Optional.empty(),
							   Optional.empty());
		} else {
			result = this.list(Optional.ofNullable(driverStandingForm.getOffset()), 
					  		   Optional.ofNullable(driverStandingForm.getLimit()),
					  		   Optional.ofNullable(driverStandingForm.getSeason()),
					  		   Optional.ofNullable(driverStandingForm.getPosition()),
					  		   Optional.ofNullable(driverStandingForm.getDriver()));
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverStandingForm driverStandingForm, BindingResult binding) {
		ModelAndView result;
		Map<String, List<Object>> mapa;
		String season, position, driver;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			mapa = new HashMap<String, List<Object>>();
			
		} else {
			//TODO: Si no hay errores de validacion, se filtran los constructor
			// standing según los parametros de busquedas
			season = driverStandingForm.getSeason().trim();
			position = driverStandingForm.getPosition().trim();
			driver = driverStandingForm.getDriver().trim();
			
			if (!StringUtil.isBlank(season)) {
				mapa = this.driverStandingService.findBySeason(season,
						Optional.ofNullable(driverStandingForm.getOffset()),
						Optional.ofNullable(driverStandingForm.getLimit()));
			
			} else if (!StringUtil.isBlank(position)) {
				mapa = this.driverStandingService.findByPosition(position,
								Optional.ofNullable(driverStandingForm.getOffset()),
								Optional.ofNullable(driverStandingForm.getLimit()));
			} else if (!StringUtil.isBlank(driver)) {
				mapa = this.driverStandingService.findByDriver(driver, 
						Optional.ofNullable(driverStandingForm.getOffset()),
						Optional.ofNullable(driverStandingForm.getLimit()));
			} else {
				//TODO: vacio no puede ir
				mapa = new HashMap<String, List<Object>>();
			}
			
		}
		
		result = this.getModelAndView(mapa, driverStandingForm);
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute DriverStandingForm driverStandingForm) {
		Map<String, List<Object>> mapa;
		ModelAndView result;
		
		mapa = this.driverStandingService.findBySeason("2018",
									Optional.ofNullable(driverStandingForm.getOffset()),
									Optional.ofNullable(driverStandingForm.getLimit()));
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa,
			                               DriverStandingForm driverStandingForm) {
		int totalPages, totalElements, valid_selectedPage, valid_limit, limit;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;
		List<Object> driversStanding;

		result = new ModelAndView("driverStanding/list");
		
		if (!mapa.isEmpty()) {
			dataPage = mapa.get("dataPage");

			totalPages = (int) dataPage.get(UtilityService.POS_TOTAL_PAGES);
			pages = this.utilityService.getPages(totalPages);

			totalElements = (int) dataPage.get(UtilityService.POS_TOTAL_ELEMENTS);
			
			limit = (int) dataPage.get(UtilityService.POS_LIMIT);
			valid_limit = (limit > totalElements) ? totalElements : limit;
			
			valid_selectedPage = (int) dataPage.get(UtilityService.POS_OFFSET);
		
			driverStandingForm.setLimit(valid_limit);
			driverStandingForm.setOffset(valid_selectedPage);
			
			driversStanding = mapa.get("driversStanding");
			
			result.addObject("totalElements", totalElements);
			result.addObject("limit", valid_limit);
			result.addObject("selectedPage", valid_selectedPage);
			result.addObject("totalPages", totalPages);
			result.addObject("pages", pages);
			
		} else {
			driversStanding = new ArrayList<Object>();
		}

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
