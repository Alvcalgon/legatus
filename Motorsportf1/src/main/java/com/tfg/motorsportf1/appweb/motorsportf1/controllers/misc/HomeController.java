package com.tfg.motorsportf1.appweb.motorsportf1.controllers.misc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
public class HomeController {

	private static final Log log = LogFactory.getLog(HomeController.class);
	
	@Autowired
	private UtilityService utilityService;
	
	// Internal state --------------------------------
	private ConfigurableApplicationContext context;
	
	
	// ApplicationContextAware interface -------------
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		assert context != null;
//		
//		this.context = (ConfigurableApplicationContext) context;
//	}

	// Welcome ----------------------------------------------
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView result;
		
		result = new ModelAndView("redirect:/home/welcome");
		log.info("HomeController::index");
		
		return result;
	}
	
	@GetMapping("/home/welcome")
	public ModelAndView welcome() {
		ModelAndView result;
		String swaggerURL;
		
		swaggerURL = UtilityService.API_URI + "/swagger-ui.html";
		
		try {
			result = new ModelAndView("home/index");
			result.addObject("swaggerURL", swaggerURL);
			
			log.info("HomeController::welcome");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/error");
			log.info("Redireccion HomeController::error");
		}
		
		return result;
	}

	// API REST
	@GetMapping("/api")
	public ModelAndView infoAPI() {
		ModelAndView result;
		String driverURL;
		String constructorURL;
		String circuitURL;
		String raceURL;
		String driverStdURL;
		String constructorStdURL;
		String swaggerURL;
		
		driverURL = UtilityService.API_URI + "/driver/list";
		constructorURL = UtilityService.API_URI + "/constructor/list";
		circuitURL = UtilityService.API_URI + "/circuit/list";
		raceURL = UtilityService.API_URI + "/race/list/season/2019";
		driverStdURL = UtilityService.API_URI + "/driver-standing/list/season/2019";
		constructorStdURL = UtilityService.API_URI + "/constructor-standing/list/season/2019";
		swaggerURL = UtilityService.API_URI + "/swagger-ui.html";
		
		result = new ModelAndView("home/api");
		result.addObject("driverURL", driverURL);
		result.addObject("constructorURL", constructorURL);
		result.addObject("circuitURL", circuitURL);
		result.addObject("raceURL", raceURL);
		result.addObject("driverStdURL", driverStdURL);
		result.addObject("constructorStdURL", constructorStdURL);
		result.addObject("swaggerURL", swaggerURL);
		
		log.info("HomeController::infoAPI");
		
		return result;
	}
	
	// Footer ----------------------------------------------
	@GetMapping("/home/terms")
	public ModelAndView terms() {
		ModelAndView result;
		
		result = new ModelAndView("master-page/terms");
		log.info("HomeController::terms");
		
		return result;
	}
	
	@GetMapping("/home/cookies")
	public ModelAndView cookiesPolicy() {
		ModelAndView result;

		result = new ModelAndView("master-page/cookiesPolicy");
		log.info("HomeController::cookiesPolicy");
		
		return result;
	}
	
	
	// Panic ----------------------------------------------	
	@GetMapping("/home/oops")
	public ModelAndView oops() {
		throw new RuntimeException("Test exception");
	}
	
	@GetMapping("/error")
	public ModelAndView error() {
		ModelAndView result;

		result = new ModelAndView("home/error");

		return result;
	}
	
}
