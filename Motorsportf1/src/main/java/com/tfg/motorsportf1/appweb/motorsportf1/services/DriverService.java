package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;

@Service
public class DriverService {

	private static final Log log = LogFactory.getLog(DriverService.class);

	@Autowired
	private UtilityService utilityService;

	
	public DriverService() {
		super();
	}


	public Object findOne(String fullname) {
		Object result;
		String encodedFullname, url;
		
		try {			
			encodedFullname = this.utilityService.getEncodedText(fullname);
			
			url = UtilityService.API_URI_PRE + "/driver/display/" + encodedFullname;
			
			result = this.utilityService.stringMapJSON(url);

		} catch (Throwable oops) {
			result = null;
			
			log.info("DriverService::findOne - Error al recuperar el piloto cuyo nombre completo es: " + fullname);
		}

		return result;
	}

	public Map<String, List<Object>> findAll() {
		return this.findAll(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
	}
	
	// source:
	// https://www.journaldev.com/2552/spring-rest-example-tutorial-spring-restful-web-services
	public Map<String, List<Object>> findAll(Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/driver/list";

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	// source:
	// https://www.journaldev.com/2552/spring-rest-example-tutorial-spring-restful-web-services
	public Map<String, List<Object>> findByCountry(String country, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedCountry;

		encodedCountry = this.utilityService.getEncodedText(country);
		
		url = UtilityService.API_URI_PRE + "/driver/list/country/" + encodedCountry;
		
		results = this.getDataPaginationAndObjects(url, selectedPage);
		
		return results;
	}

	public Map<String, List<Object>> findByFullname(String fullname, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedFullname;

		encodedFullname = this.utilityService.getEncodedText(fullname);
		
		url = UtilityService.API_URI_PRE + "/driver/list/fullname/" + encodedFullname;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findByParameters(String fullname, String country,
			Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedFullname, encodedCountry;

		encodedFullname = this.utilityService.getEncodedText(fullname);
		encodedCountry = this.utilityService.getEncodedText(country);
		
		url = UtilityService.API_URI_PRE + "/driver/list/country/" + encodedCountry + "/fullname/" + encodedFullname;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	private Map<String, List<Object>> getDataPaginationAndObjects(String url,
				Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, String>> ls_map_drivers;
		String fullname, country, str_dateOfBirth, placeOfBirth;
		int totalPages, totalElements, valid_selectedPage, targetPage;
		Map<String, Object> map_json, temp;
		List<Object> drivers;
		List<Object> dataPage;
		Driver driver;
		Date dateOfBirth;
		
		try {
			temp = this.utilityService.mapJSON(url, 0);
			totalElements = (int) temp.get("totalElements");
			
			// Validamos offset de la paginacion
			valid_selectedPage = this.utilityService.getValidOffset(selectedPage, totalElements);

			targetPage = valid_selectedPage - 1;
			map_json = this.utilityService.mapJSON(url, targetPage);
			
			results = new HashMap<String, List<Object>>();
			
			dataPage = new ArrayList<Object>();
			drivers = new ArrayList<Object>();
			
			if (!map_json.isEmpty()) {
				ls_map_drivers = (List<LinkedHashMap<String, String>>) map_json.get("content");

				if (!ls_map_drivers.isEmpty()) {
					for (LinkedHashMap<String, String> mapDriver : ls_map_drivers) {
						fullname = this.utilityService.getStringFromKey(mapDriver, "fullname");
						country = this.utilityService.getStringFromKey(mapDriver, "country");
						placeOfBirth = this.utilityService.getStringFromKey(mapDriver, "placeOfBirth");
						str_dateOfBirth = this.utilityService.getStringFromKey(mapDriver, "dateOfBirth");
						dateOfBirth = this.utilityService.getDateFromString(str_dateOfBirth);
						
						driver = new Driver(fullname, placeOfBirth, country, dateOfBirth);

						drivers.add(driver);
					}
				}
				
				totalPages = (int) map_json.get("totalPages");
				totalElements = (int) map_json.get("totalElements");
				
				dataPage = this.utilityService.fillDataPage(totalPages,
															totalElements,
															valid_selectedPage);
			}
			
			results.put("drivers", drivers);
			results.put("dataPage", dataPage);
			
		} catch (Throwable oops) {
			log.info("DriverService::getDataPaginationAndObjects ->"
					+ " Algo fue mal al recuperar los objetos y datos de la paginacion: "
						+ oops.getMessage());

			results = new HashMap<String, List<Object>>();
			
			drivers = new ArrayList<Object>();
			dataPage = this.utilityService.fillDataPage(-1, -1, UtilityService.DEFAULT_OFFSET_TO_USER);
			
			results.put("drivers", drivers);
			results.put("dataPage", dataPage);
		}
		
		return results;
	}
		
}
