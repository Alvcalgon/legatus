package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

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
		DriverJson mapa;
		ModelAndView result;
		
		String val_fullname = fullname.orElse(UtilityService.CADENA_VACIA);
		String val_nationality = nationality.orElse(UtilityService.CADENA_VACIA);
		
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
		
		int valid_offset = mapa.getNumber();
		
		DriverForm driverForm = new DriverForm(valid_offset,
									val_fullname,
									val_nationality);
		
		result = this.getModelAndView(mapa, driverForm);
		
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
				Integer victories = this.resultService.findCountByPositionAndDriver(fullnameTrim, "1");
				Integer podiums = this.driverService.getPodiums(fullnameTrim);
				Integer poles = this.resultService.findCountByGridAndDriver(fullnameTrim, "1");
				Integer titles = this.driverStandingService.findCountByDriverAndPosition(fullnameTrim,
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
		DriverJson json = this.driverService.findAll();
		
		ModelAndView result = this.getModelAndView(json);
		
		return result;
	}
	
	
	protected ModelAndView getModelAndView(DriverJson json, 
										DriverForm driverForm) {
		List<Driver> drivers;
		List<Integer> pages;
		
		int totalPages = json.getTotalPages();
		pages = this.utilityService.getPages(totalPages);
	
		int totalElements = json.getTotalElements();
				
		int valid_selectedPage = driverForm.getOffset() + 1;
	
		driverForm.setOffset(valid_selectedPage);
		
		drivers = Arrays.asList(json.getContent());
		
		ModelAndView result = new ModelAndView("driver/list");
	
		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
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
