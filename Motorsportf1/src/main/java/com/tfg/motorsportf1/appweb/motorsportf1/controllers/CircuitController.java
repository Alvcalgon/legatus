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
		ModelAndView result = new ModelAndView("circuit/listSeason");
		
		String valSeason = UtilityService.CADENA_VACIA;
		
		List<Circuit> circuits = new ArrayList<Circuit>();
		
		if (season.isPresent()) {
			valSeason = season.get();
			
			circuits = this.circuitService.findBySeason(valSeason.trim());
		}
		
		result.addObject("season", valSeason.trim());
		result.addObject("circuits", circuits);
		
		return result;
	}
	
	@PostMapping(value = "/list-by-season", params = "search")
	public ModelAndView listBySeason(HttpServletRequest request) {		
		String season = String.valueOf(request.getParameter("seasonSearch"));
		
		String valSeason = HtmlUtils.htmlEscape(season);
		
		ModelAndView result = this.listBySeason(Optional.ofNullable(valSeason));
		
		return result;
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam("offset") Optional<Integer> selectedPage,
							 @RequestParam("location") Optional<String> location,
							 @RequestParam("name") Optional<String> name) {
		CircuitJson mapa;
		
		String valName = name.orElse(UtilityService.CADENA_VACIA);
		String valLocation = location.orElse(UtilityService.CADENA_VACIA);
		
		int validOffset = this.utilityService.getValidOffset(selectedPage);
		
		if (!StringUtil.isBlank(valName) && !StringUtil.isBlank(valLocation)) {
			
			mapa = this.circuitService.findByAllParameters(valLocation, valName, selectedPage);
		} else if (!StringUtil.isBlank(valLocation)) {			
			
			mapa = this.circuitService.findByLocation(valLocation, selectedPage);
		} else if (!StringUtil.isBlank(valName)) {			
			
			mapa = this.circuitService.findByName(valName, selectedPage);
		} else {
			
			mapa = this.circuitService.findAll(selectedPage);
		}
		
		validOffset = this.utilityService.getValidOffset(selectedPage, mapa.getTotalElements());
		
		CircuitForm circuitForm = new CircuitForm(validOffset, valLocation, valName);
		
		ModelAndView result = this.getModelAndView(mapa, circuitForm);
		
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
		CircuitJson mapa = this.circuitService.findAll();
		
		ModelAndView result = this.getModelAndView(mapa);
		
		return result;
	}
	
	protected ModelAndView getModelAndView(CircuitJson json,
										   CircuitForm circuitForm) {
		int totalPages = json.getTotalPages();
		List<Integer> pages = this.utilityService.getPages(totalPages);

		int totalElements = json.getTotalElements();

		int valid_selectedPage = circuitForm.getOffset();
		log.info("Offset: " + valid_selectedPage);

		circuitForm.setOffset(valid_selectedPage);

		List<Circuit> circuits = Arrays.asList(json.getContent());

		ModelAndView result = new ModelAndView("circuit/list");

		result.addObject("totalElements", totalElements);
		result.addObject("selectedPage", valid_selectedPage);
		result.addObject("totalPages", totalPages);
		result.addObject("pages", pages);
		result.addObject("circuits", circuits);
		result.addObject("circuitForm", circuitForm);

		return result;
	}

	protected ModelAndView getModelAndView(CircuitJson json) {
		CircuitForm circuitForm = new CircuitForm();
		
		ModelAndView result = this.getModelAndView(json, circuitForm);

		return result;
	}

}
