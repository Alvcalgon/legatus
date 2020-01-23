package com.tfg.motorsportf1.appweb.motorsportf1.controllers.misc;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

@Controller
public class PanicController implements ErrorController {

	// ErrorController interface ----------------------------------------------

	@Override
	public String getErrorPath() {
		return "/home/panic";
	}

}
