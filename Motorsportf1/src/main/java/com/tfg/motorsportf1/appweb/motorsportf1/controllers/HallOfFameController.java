package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tfg.motorsportf1.appweb.motorsportf1.services.HallOfFameService;

@Controller
@RequestMapping("/hall-of-fame")
public class HallOfFameController {

	@Autowired
	private HallOfFameService hallOfFameService;
	
	
	public HallOfFameController() {
		super();
	}
	
	
	@GetMapping(value = "/championship/drivers")
	public ModelAndView findDriversTitle() {
		ModelAndView result;
		List<Object> objects;
		
		result = new ModelAndView("hallOfFame/list");
		
		objects = this.hallOfFameService.findDriversTitle();
		result.addObject("results", objects);
		result.addObject("requestURI", "hall-of-fame/championship/drivers");
		
		return result;
	}
	
	@GetMapping(value = "/championship/constructors")
	public ModelAndView findConstructorsTitle() {
		ModelAndView result;
		List<Object> objects;
		
		result = new ModelAndView("hallOfFame/list2");
		
		objects = this.hallOfFameService.findConstructorsTitle();
		result.addObject("results", objects);
		
		
		return result;
	}
	
	@GetMapping(value = "/victories/drivers")
	public ModelAndView findDriversVictories() {
		ModelAndView result;
		List<Object> objects;
		
		result = new ModelAndView("hallOfFame/list");
		
		objects = this.hallOfFameService.findDriversVictories();
		result.addObject("results", objects);
		result.addObject("requestURI", "hall-of-fame/victories/drivers");
		
		return result;
	}
	
}
