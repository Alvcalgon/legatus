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

import com.tfg.motorsportf1.appweb.motorsportf1.forms.DriverForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.DriverService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/driver")
public class DriverController {

	//private static final Log log = LogFactory.getLog(DriverController.class);
	
	@Autowired
	private DriverService driverService;
	
	@Autowired
	private UtilityService utilityService;
	
	
	public DriverController() {
		super();
	}
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
							 @RequestParam("fullname") Optional<String> fullname,
							 @RequestParam("country") Optional<String> country) {
		String val_country, val_fullname;
		int valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;
		DriverForm driverForm;
		
		val_fullname = fullname.orElse("");
		val_country = country.orElse("");
		
		if (!StringUtil.isBlank(val_fullname) && !StringUtil.isBlank(val_country)) {
			mapa = this.driverService.findByParameters(val_fullname,
													   val_country,
													   selectedPage);
		} else if (!StringUtil.isBlank(val_fullname)) {
			mapa = this.driverService.findByFullname(val_fullname,
													selectedPage);
		} else if (!StringUtil.isBlank(val_country)) {			
			mapa = this.driverService.findByCountry(val_country,
													selectedPage);
		} else {
			mapa = this.driverService.findAll(selectedPage);
		}
		
		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");
		
		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);
		
		driverForm = new DriverForm(valid_offset,
									val_fullname.trim(),
									val_country.trim());
		
		result = this.getModelAndView(mapa, driverForm);
		
		return result;
	}
	
	@GetMapping("/display")
	public ModelAndView display(@RequestParam("fullname") String fullname) {			
		ModelAndView result;
		Object driver;
		
		driver = this.driverService.findOne(fullname.trim());
		
		if (driver != null) {
			result = new ModelAndView("driver/display");
			result.addObject("driver", driver);
		} else {
			result = this.list(Optional.empty(),
							   Optional.empty(),
							   Optional.empty());
		}
		
		return result;
	}
			
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverForm driverForm, BindingResult binding) {
		ModelAndView result;
		String country, fullname;
		Integer offset;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			result = this.list(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER),
					   		   Optional.empty(),
					   		   Optional.empty());
		} else {
			// Si no hay errores de validacion, se filtran los pilotos según los
			// parametros de busquedas
			country = driverForm.getCountry().trim();
			fullname = driverForm.getFullname().trim();
			offset = driverForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(fullname),
							   Optional.ofNullable(country));
		}
		
		return result;
	}
	
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute DriverForm driverForm) {
		Map<String, List<Object>> mapa;
		ModelAndView result;
		
		mapa = this.driverService.findAll();
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa, 
										DriverForm driverForm) {
		int totalPages, totalElements, valid_selectedPage;
		List<Object> drivers;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;
		
		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");
		
		totalPages = (int) dataPage.get(UtilityService.POS_TOTAL_PAGES);
		pages = this.utilityService.getPages(totalPages);
	
		totalElements = (int) dataPage.get(UtilityService.POS_TOTAL_ELEMENTS);
				
		valid_selectedPage = (int) dataPage.get(UtilityService.POS_OFFSET);
	
		driverForm.setOffset(valid_selectedPage);
		
		drivers = this.utilityService.getFromMap2(mapa, "drivers");
		
		result = new ModelAndView("driver/list");
	
		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("drivers", drivers);
		result.addObject("driverForm", driverForm);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(Map<String, List<Object>> mapa) {
		ModelAndView result;
		DriverForm driverForm;
		
		driverForm = new DriverForm();
		result = this.getModelAndView(mapa, driverForm);
		
		return result;
	}
		
}
