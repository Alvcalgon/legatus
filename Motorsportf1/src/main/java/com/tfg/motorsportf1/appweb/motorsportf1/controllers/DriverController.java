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
							 @RequestParam("limit") Optional<Integer> limit,
							 @RequestParam("fullname") Optional<String> fullname,
							 @RequestParam("country") Optional<String> country) {
		String val_country, val_fullname;
		int valid_limit, valid_offset;
		Map<String, List<Object>> mapa;
		ModelAndView result;
		List<Object> dataPage;
		DriverForm driverForm;
		
		val_fullname = fullname.orElse(null);
		val_country = country.orElse(null);
		
		if (!StringUtil.isBlank(val_fullname)) {
			mapa = this.driverService.findByFullname(val_fullname, selectedPage, limit);
		} else if (!StringUtil.isBlank(val_country)) {			
			mapa = this.driverService.findByCountry(val_country, selectedPage, limit);
		} else {
			mapa = this.driverService.findAll(selectedPage, limit);
		}
		
		dataPage = mapa.get("dataPage");
		
		valid_limit = (int) dataPage.get(UtilityService.POS_LIMIT);
		valid_offset = (int) dataPage.get(UtilityService.POS_OFFSET);
		
		driverForm = new DriverForm(valid_offset, valid_limit, val_fullname, val_country);
		
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
							   Optional.empty(),
							   Optional.empty());
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "update")
	public ModelAndView update(@Valid @ModelAttribute DriverForm driverForm, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se muestra la primera pagina
			result = this.list(Optional.of(0),
							   Optional.of(10),
							   Optional.empty(),
							   Optional.empty());
			
		} else {
			result = this.list(Optional.ofNullable(driverForm.getOffset()), 
					  		   Optional.ofNullable(driverForm.getLimit()),
					  		   Optional.ofNullable(driverForm.getFullname()),
					  		   Optional.ofNullable(driverForm.getCountry()));
		}
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverForm driverForm, BindingResult binding) {
		ModelAndView result;
		Map<String, List<Object>> mapa;
		String country, fullname;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			mapa = this.driverService.findAll();
			
		} else {
			//TODO: Si no hay errores de validacion, se filtran los pilotos según los
			// parametros de busquedas
			country = driverForm.getCountry().trim();
			fullname = driverForm.getFullname().trim();
			
			if (!StringUtil.isBlank(country)) {
				mapa = this.driverService.findByCountry(country,
								Optional.ofNullable(driverForm.getOffset()),
							    Optional.ofNullable(driverForm.getLimit()));
			
			} else if (!StringUtil.isBlank(fullname)) {
				mapa = this.driverService.findByFullname(fullname,
								Optional.ofNullable(driverForm.getOffset()),
								Optional.ofNullable(driverForm.getLimit()));
			} else {
				mapa = this.driverService.findAll(
						Optional.ofNullable(driverForm.getOffset()),
						Optional.ofNullable(driverForm.getLimit()));
			}
			
		}
		
		result = this.getModelAndView(mapa, driverForm);
		
		return result;
	}
	
//	@PostMapping(value = "/list", params = "search")
//	public ModelAndView search(@Valid @ModelAttribute DriverForm driverForm, BindingResult binding) {
//		ModelAndView result;
//		Map<String, List<Object>> mapa, mapa_fullname, mapa_country;
//		List<Object> objects, objects_fullname, objects_country;
//		String country, fullname;
//		
//		if (binding.hasErrors()) {
//			// Si hay errores de validacion, se envian todos los pilotos
//			mapa = this.driverService.findAll();
//			
//		} else {
//			//TODO: Si no hay errores de validacion, se filtran los pilotos según los
//			// parametros de busquedas
//			country = driverForm.getCountry().trim();
//			fullname = driverForm.getFullname().trim();
//			
//			mapa = this.driverService.findAll(
//					Optional.ofNullable(driverForm.getOffset()),
//					Optional.ofNullable(driverForm.getLimit()));
//			
//			objects = mapa.get("drivers");
//			
//			if (!StringUtil.isBlank(country)) {
//				mapa_country = this.driverService.findByCountry(country,
//								Optional.ofNullable(driverForm.getOffset()),
//							    Optional.ofNullable(driverForm.getLimit()));
//				
//				objects_country = mapa_country.get("drivers");
//				
//				objects.retainAll(objects_country);
//			} else if (!StringUtil.isBlank(fullname)) {
//				mapa_fullname = this.driverService.findByFullname(fullname,
//								Optional.ofNullable(driverForm.getOffset()),
//								Optional.ofNullable(driverForm.getLimit()));
//				
//				objects_fullname = mapa_fullname.get("drivers");
//				
//				objects.retainAll(objects_fullname);
//			}
//			
//			mapa.put("drivers", objects);
//		}
//		
//		result = this.getModelAndView(mapa, driverForm);
//		
//		return result;
//	}
	
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
		int totalPages, totalElements, valid_selectedPage, valid_limit;
		ModelAndView result;
		List<Integer> pages;
		List<Object> dataPage;
		
		dataPage = mapa.get("dataPage");
		
		totalPages = (int) dataPage.get(UtilityService.POS_TOTAL_PAGES);
		pages = this.utilityService.getPages(totalPages);
	
		totalElements = (int) dataPage.get(UtilityService.POS_TOTAL_ELEMENTS);
		valid_limit = (int) dataPage.get(UtilityService.POS_LIMIT);
		valid_selectedPage = (int) dataPage.get(UtilityService.POS_OFFSET);
	
		result = new ModelAndView("driver/list");
	
		result.addObject("totalElements", totalElements);
		result.addObject("limit", valid_limit);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("drivers", mapa.get("drivers"));
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
