package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

@Controller
@RequestMapping("/driver")
public class DriverController {

	private static final Log log = LogFactory.getLog(DriverController.class);
	
	@Autowired
	private DriverService driverService;
	
	public DriverController() {
		super();
	}
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> requestedPage,
							 @RequestParam("limit") Optional<Integer> limit) {
		int targetPage, sizePage, totalPages, currentPage;
		ModelAndView result;
		DriverForm driverForm;
		List<Integer> pages;
		Map<String, List<Object>> mapa;
	
		result = new ModelAndView("driver/list");
		result.addObject("requestedPage", requestedPage);
		
		targetPage = (requestedPage.isPresent()) ? requestedPage.get()-1 : 0;
		sizePage = limit.orElse(10);
		
		driverForm = new DriverForm();
		
		mapa = this.driverService.findAll(targetPage, sizePage);
		
		totalPages = (int) mapa.get("dataPage").get(0);
		currentPage = (int) mapa.get("dataPage").get(1);
		
		if (totalPages > 0) {
			pages = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
		} else {
			pages = new ArrayList<Integer>();
		}
		
		result.addObject("drivers", mapa.get("drivers"));
		result.addObject("limit", sizePage);
		result.addObject("currentPage", currentPage);
		result.addObject("pages", pages);
		result.addObject("driverForm", driverForm);
		
		return result;
	}
	
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverForm driverForm, BindingResult binding) {
		ModelAndView result;
		
		result = new ModelAndView("driver/list");
		result.addObject("driverForm", driverForm);
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			result.addObject("drivers", this.driverService.findAll());
			
		} else {
			//TODO: Si no hay errores de validacion, se filtran los pilotos según los
			// parametros de busquedas
			result.addObject("drivers", this.driverService.findAll());
			
			result.addObject("nombreCompleto", driverForm.getFullname());
			result.addObject("pais", driverForm.getCountry());
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
		
}
