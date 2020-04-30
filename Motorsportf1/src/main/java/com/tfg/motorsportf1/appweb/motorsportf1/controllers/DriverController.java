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
import com.tfg.motorsportf1.appweb.motorsportf1.services.DriverStandingService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.ResultService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/driver")
public class DriverController {

	//private static final Log log = LogFactory.getLog(DriverController.class);
	
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
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;
		DriverForm driverForm;
		
		String val_fullname = fullname.orElse("");
		String val_nationality = nationality.orElse("");
		
		if (!StringUtil.isBlank(val_fullname) && !StringUtil.isBlank(val_nationality)) {
			mapa = this.driverService.findByParameters(val_fullname,
													   val_nationality,
													   selectedPage);
		} else if (!StringUtil.isBlank(val_fullname)) {
			mapa = this.driverService.findByFullname(val_fullname,
													selectedPage);
		} else if (!StringUtil.isBlank(val_nationality)) {			
			mapa = this.driverService.findByNationality(val_nationality,
													selectedPage);
		} else {
			mapa = this.driverService.findAll(selectedPage);
		}
		
		dataPage = this.utilityService.getFromMap2(mapa, "dataPage");
		
		int valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);
		
		driverForm = new DriverForm(valid_offset,
									val_fullname,
									val_nationality);
		
		result = this.getModelAndView(mapa, driverForm);
		
		return result;
	}
	
	@GetMapping("/display")
	public ModelAndView display(@RequestParam("fullname") String fullname) {			
		ModelAndView result;
		String fullnameTrim;
		Object driver;
		Integer races;
		Integer victories;
		Integer podiums;
		Integer poles;
		Integer titles;
		
		fullnameTrim = fullname.trim();
		
		driver = this.driverService.findOne(fullnameTrim);
		
		try {
			if (driver != null) {
				result = new ModelAndView("driver/display");
								
				races = this.resultService.findCountByDriver(fullnameTrim);
				victories = this.resultService.findCountByPositionAndDriver(fullnameTrim, "1");
				podiums = this.driverService.getPodiums(fullnameTrim);
				poles = this.resultService.findCountByGridAndDriver(fullnameTrim, "1");
				titles = this.driverStandingService.findCountByDriverAndPosition(fullnameTrim,
																				 "1");
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
		}
		
		return result;
	}
			
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverForm driverForm, BindingResult binding) {
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
