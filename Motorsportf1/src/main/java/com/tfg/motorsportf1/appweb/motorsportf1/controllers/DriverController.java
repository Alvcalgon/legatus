package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tfg.motorsportf1.appweb.motorsportf1.services.DriverService;

@Controller
public class DriverController {

	private static final Log log = LogFactory.getLog(DriverController.class);
	
	@Autowired
	private DriverService driverService;
	
	public DriverController() {
		super();
	}
	
}
