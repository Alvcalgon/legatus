package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;
import com.tfg.motorsportf1.appweb.motorsportf1.forms.DriverForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.DriverService;

@Controller
@RequestMapping("/home/driver")
public class DriverController {

	private static final Log log = LogFactory.getLog(DriverController.class);
	
	@Autowired
	private DriverService driverService;
	
	public DriverController() {
		super();
	}
	
	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		DriverForm driverForm;
		
		
		driverForm = new DriverForm();
		
		result = new ModelAndView("driver/list");
		result.addObject("drivers", this.findAll());
		result.addObject("driverForm", driverForm);
		
		return result;
	}
	
	@PostMapping(value = "/search", params = "search")
	public ModelAndView search(@Valid @ModelAttribute DriverForm driverForm, BindingResult binding) {
		ModelAndView result;
		
		result = new ModelAndView("driver/list");
		result.addObject("driverForm", driverForm);
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los pilotos
			result.addObject("drivers", this.findAll());
			
		} else {
			//TODO: Si no hay errores de validacion, se filtran los pilotos según los
			// parametros de busquedas
			result.addObject("drivers", this.findAll());
			
			result.addObject("nombreCompleto", driverForm.getFullname());
			result.addObject("pais", driverForm.getCountry());
		}
		
		return result;
		
	}
	
	@PostMapping(value = "/search", params = "reset")
	public ModelAndView reset(@ModelAttribute DriverForm driverForm) {
		ModelAndView result;
		
		result = new ModelAndView("driver/list");
		result.addObject("drivers", this.findAll());
		result.addObject("driverForm", new DriverForm());
		
		return result;
		
	}
	
	private List<Driver> findAll() {
		List<Driver> drivers;
		Driver d;
		
		drivers = new ArrayList<Driver>();
		
		d = new Driver();
		d.setFullname("Fernando Alonso");
		d.setCountry("Spain");
		d.setPlaceOfBirth("Turia, Asturias");
		d.setDateOfBirth(new Date());
		
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Lewis Hamilton");
		d.setCountry("Great Britain");
		d.setPlaceOfBirth("London, England");
		d.setDateOfBirth(new Date());
		
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Sebastian Vettel");
		d.setCountry("Germany");
		d.setPlaceOfBirth("Berlin");
		d.setDateOfBirth(new Date());
		
		drivers.add(d);
		
		return drivers;
	}
	
}
