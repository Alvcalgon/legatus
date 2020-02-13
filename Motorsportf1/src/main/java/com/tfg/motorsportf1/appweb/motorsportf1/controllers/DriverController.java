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

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;
import com.tfg.motorsportf1.appweb.motorsportf1.forms.DriverForm;
import com.tfg.motorsportf1.appweb.motorsportf1.forms.PaginationForm;
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
							 @RequestParam("limit") Optional<Integer> limit) {
		ModelAndView result;
		Map<String, List<Object>> mapa;
	
		mapa = this.driverService.findAll(selectedPage, limit);
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	@GetMapping("/display")
	public ModelAndView display(@RequestParam("fullname") String fullname) {
		ModelAndView result;
		Driver driver;
		
		driver = (Driver)this.driverService.findOne(fullname.trim());
		
		result = new ModelAndView("driver/display");
		result.addObject("driver", driver);
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "update")
	public ModelAndView update(@Valid @ModelAttribute PaginationForm paginationForm, BindingResult binding) {
		ModelAndView result;
				
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se muestra la primera pagina
			result = this.list(Optional.of(0), Optional.of(10));
			
		} else {
			result = this.list(Optional.of(paginationForm.getOffset()), 
					  		   Optional.of(paginationForm.getLimit()));
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverForm driverForm, BindingResult binding) {
		ModelAndView result;
		Map<String, List<Object>> mapa;
		String country;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			mapa = this.driverService.findAll();
			result = this.getModelAndView(mapa);
			
		} else {
			//TODO: Si no hay errores de validacion, se filtran los pilotos según los
			// parametros de busquedas
			country = driverForm.getCountry();
			
			if (!StringUtil.isBlank(country)) {
				mapa = this.driverService.findByCountry(country,
														Optional.ofNullable(0),
														Optional.ofNullable(10)); 
			} else {
				mapa = this.driverService.findAll();
			}
			
			result = this.getModelAndView(mapa);
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute DriverForm driverForm) {
		ModelAndView result;
		
		result = new ModelAndView("driver/list");
		result.addObject("drivers", this.driverService.findAll());
		result.addObject("driverForm", new DriverForm());
		//PagedListHolder<Driver> pagedDriver;
		return result;
		
	}
	
	
	public ModelAndView getModelAndView(Map<String, List<Object>> mapa) {
		int totalPages, totalElements, valid_selectedPage, valid_limit;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;
		PaginationForm paginationForm;
		
		dataPage = mapa.get("dataPage");
		
		totalPages = (int) dataPage.get(0);
		pages = this.utilityService.getPages(totalPages);
	
		totalElements = (int) dataPage.get(2);
		valid_limit = (int) dataPage.get(3);
		valid_selectedPage = (int) dataPage.get(4);
	
		paginationForm = new PaginationForm(valid_selectedPage, valid_limit);
		
		result = new ModelAndView("driver/list");
		
		result.addObject("totalElements", totalElements);
		result.addObject("limit", valid_limit);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("drivers", mapa.get("drivers"));
		result.addObject("driverForm", new DriverForm());
		result.addObject("paginationForm", paginationForm);
		
		return result;
	}
		
}
