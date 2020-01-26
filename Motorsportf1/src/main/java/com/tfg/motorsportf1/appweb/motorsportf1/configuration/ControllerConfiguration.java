/*
 * ControllerConfiguration.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package com.tfg.motorsportf1.appweb.motorsportf1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@ControllerAdvice
public class ControllerConfiguration {

	@Autowired
	private UtilityService utilityService;
	
	// Handlers ---------------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView handleException(final Throwable oops) {
		ModelAndView result;
		
		result = new ModelAndView("home/panic");
		result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		
		result.addObject("moment", this.utilityService.getCurrentMomentString());
		result.addObject("oops", oops);
		
		return result;
	}
	
}
