package com.tfg.motorsportf1.appweb.motorsportf1.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.util.HtmlUtils;

import com.tfg.motorsportf1.appweb.motorsportf1.bean.CircuitJson;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Circuit;
import com.tfg.motorsportf1.appweb.motorsportf1.forms.CircuitForm;
import com.tfg.motorsportf1.appweb.motorsportf1.services.CircuitService;
import com.tfg.motorsportf1.appweb.motorsportf1.services.UtilityService;

@Controller
@RequestMapping("/circuit")
public class CircuitController {

	private static final Log log = LogFactory.getLog(CircuitController.class);

	@Autowired
	private CircuitService circuitService;

	@Autowired
	private UtilityService utilityService;

	
	public CircuitController() {
		super();
	}

	@GetMapping(value = "/list-by-season")
	public ModelAndView listBySeason(Optional<String> season) {
		ModelAndView result;
		List<Circuit> circuits;
		String val_season;
		
		result = new ModelAndView("circuit/listSeason");
		
		if (season.isPresent()) {
			val_season = season.get();
			
			circuits = this.circuitService.findBySeason(val_season.trim());
			result.addObject("season", val_season.trim());
		} else {
			circuits = new ArrayList<>();
			
			result.addObject("season", UtilityService.CADENA_VACIA);
		}
		
		result.addObject("circuits", circuits);
		
		return result;
	}
	
	@PostMapping(value = "/list-by-season", params = "search")
	public ModelAndView listBySeason(HttpServletRequest request) {
		ModelAndView result;
		String season, val_season;
		
		season = String.valueOf(request.getParameter("seasonSearch"));
		val_season = HtmlUtils.htmlEscape(season);
		
		result = this.listBySeason(Optional.ofNullable(val_season));
		
		return result;
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
							 @RequestParam("location") Optional<String> location,
							 @RequestParam("name") Optional<String> name) {
		String val_location, val_name;
		int valid_offset;
		CircuitJson mapa;
		ModelAndView result;
		CircuitForm circuitForm;
		
		val_name = name.orElse(UtilityService.CADENA_VACIA);
		val_location = location.orElse(UtilityService.CADENA_VACIA);
				
		if (!StringUtil.isBlank(val_name) && !StringUtil.isBlank(val_location)) {
			
			mapa = this.circuitService.findByAllParameters(val_location, val_name, selectedPage);
		
		} else if (!StringUtil.isBlank(val_location)) {			
			
			mapa = this.circuitService.findByLocation(val_location, selectedPage);
					
		} else if (!StringUtil.isBlank(val_name)) {			
			
			mapa = this.circuitService.findByName(val_name, selectedPage);
				
		} else {
			
			mapa = this.circuitService.findAll(selectedPage);
		
		}
		
		valid_offset = (int) mapa.getNumber();
		
		circuitForm = new CircuitForm(valid_offset, val_location, val_name);
		
		result = this.getModelAndView(mapa, circuitForm);
		
		return result;
	}
		
	@PostMapping(value = "/list", params = "search")
	public ModelAndView search(@Valid @ModelAttribute CircuitForm circuitForm,
							   BindingResult binding) {
		ModelAndView result;
		String name, location;
		Integer offset;
		
		if (binding.hasErrors()) {
			// Si hay errores de validacion, se envian todos los circuitos
			result = this.getModelAndView(this.circuitService.findAll(),
										  circuitForm);
		} else {
			// Si no hay errores de validacion, se filtran los circuitos según los
			// parametros de busquedas
			location = circuitForm.getLocation().trim();
			name = circuitForm.getName().trim();
			offset = circuitForm.getOffset();
			
			result = this.list(Optional.ofNullable(offset),
							   Optional.ofNullable(location),
							   Optional.ofNullable(name));
		}
		
		return result;
	}
	
	
	@PostMapping(value = "/list", params = "reset")
	public ModelAndView reset(@ModelAttribute CircuitForm circuitForm) {
		CircuitJson mapa;
		ModelAndView result;
		
		mapa = this.circuitService.findAll();
		
		result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(CircuitJson json,
										   CircuitForm circuitForm) {
		int totalPages, totalElements, valid_selectedPage;
		List<Circuit> circuits;
		ModelAndView result;
		List<Integer> pages;

		totalPages = json.getTotalPages();
		pages = this.utilityService.getPages(totalPages);

		totalElements = json.getTotalElements();

		valid_selectedPage = circuitForm.getOffset()+1;
		log.info("Offset: " + valid_selectedPage);

		circuitForm.setOffset(valid_selectedPage);

		circuits = Arrays.asList(json.getContent());

		result = new ModelAndView("circuit/list");

		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("circuits", circuits);
		result.addObject("circuitForm", circuitForm);

		return result;
	}

	protected ModelAndView getModelAndView(CircuitJson json) {
		ModelAndView result;
		CircuitForm circuitForm;

		circuitForm = new CircuitForm();
		result = this.getModelAndView(json, circuitForm);

		return result;
	}

}
