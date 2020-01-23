package com.tfg.motorsportf1.appweb.motorsportf1.controllers.misc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController implements ApplicationContextAware {

	private static final Log log = LogFactory.getLog(HomeController.class);
	
//	@Autowired
//	private MessageSource messageSource;
	
	// Internal state --------------------------------
	private ConfigurableApplicationContext context;
	
	
	
	// ApplicationContextAware interface -------------
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		assert context != null;
		
		this.context = (ConfigurableApplicationContext) context;
	}

	// Welcome ---------------------------------------
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
		SimpleDateFormat formatter;
		String moment;
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		
		result = new ModelAndView("home/index");
		result.addObject("moment", moment);
	
		log.info("HomeController::welcome");
		
		return result;
	}
	
	// Footer -----------------------------
	@GetMapping("/home/terms")
	public ModelAndView terms() {
		ModelAndView result;
		//String metaTitle;

		//metaTitle = this.messageSource.getMessage("master.footer.terms", null, LocaleContextHolder.getLocale());
		
		result = new ModelAndView("master-page/terms");
		//result.addObject("metaTitle", metaTitle);
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
	
}
