package com.tfg.motorsportf1.appweb.motorsportf1.controllers.misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
public class PanicController implements ErrorController {

	@Autowired
	private UtilityService utilityService;
	
	// ErrorController interface ----------------------------------------------
	@GetMapping("/panic")
	public ModelAndView panic(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result;
		
		result = new ModelAndView("home/panic");		
		result.addObject("moment", this.utilityService.getCurrentMomentString());
				
		return result;
	}
	
	@Override
	public String getErrorPath() {
		return "/home/panic";
	}

}
