package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.util.ArrayList;
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
	
//	public Driver findOne(String fullname) {
//		List<LinkedHashMap<String, String>> ls_map_drivers;
//		Driver result;
//		String url;
//		
//		result = null;
//		try {
//			url = UtilityService.API_URI_PRE + "/driver/list/fullname/" + fullname;
//			
//			ls_map_drivers = this.utilityService.mapJSON(url);
//			
//			if (ls_map_drivers.size() > 0) {
//				for (LinkedHashMap<String, String> mapDriver: ls_map_drivers) {
//					result = new Driver(mapDriver.get("fullname"),
//										mapDriver.get("placeOfBirth"),
//										mapDriver.get("country"),
//										this.utilityService.getDateFromString(mapDriver.get("dateOfBirth")));
//					break;
//				}
//			}
//		} catch (Throwable oops) {
//			log.info("Error al recuperar el piloto cuyo nombre completo es: " + fullname);
//		}
//		
//		return result;
//	}
	
	public Object findOne(String fullname) {
		String ls_map_drivers;
		Object result;
		String url;
		
		result = null;
		try {
			url = UtilityService.API_URI_PRE + "/driver/list/fullname/" + fullname;
			
			result = this.utilityService.stringMapJSON(url);
			
		} catch (Throwable oops) {
			log.info("Error al recuperar el piloto cuyo nombre completo es: " + fullname);
		}
		
		return result;
	}
	
	// source: https://www.journaldev.com/2552/spring-rest-example-tutorial-spring-restful-web-services
	public Map<String, List<Object>> findAll(Optional<Integer> selectedPage,
											 Optional<Integer> limit) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, String>> ls_map_drivers;
		int currentPage, totalPages, totalElements, valid_limit,
		    valid_selectedPage, targetPage;
		Map<String, Object> map_json, temp;
		List<Object> drivers;
		List<Object> dataPage;
		Driver driver;
		String url;
		
		try {
			
			url = UtilityService.API_URI_PRE + "/driver/list";
			
			temp = this.utilityService.mapJSON(url, 0, 2);
			totalElements = (int) temp.get("totalElements");
			
			// Validamos campos de la paginacion
			valid_limit = this.utilityService.getValidLimit(limit, 
															totalElements);
			
			valid_selectedPage = this.utilityService.getValidOffset(selectedPage,
																	valid_limit,
																	totalElements);
			
			targetPage = valid_selectedPage - 1;
			map_json = this.utilityService.mapJSON(url, targetPage, valid_limit);
			
			results = new HashMap<String, List<Object>>();
			
			dataPage = new ArrayList<Object>();
			drivers = new ArrayList<Object>();
			
			if (!map_json.isEmpty()) {
				ls_map_drivers = (List<LinkedHashMap<String, String>>) map_json.get("content");
				
				for (LinkedHashMap<String, String> mapDriver: ls_map_drivers) {
					driver = new Driver(mapDriver.get("fullname"),
										mapDriver.get("placeOfBirth"),
										mapDriver.get("country"),
										this.utilityService.getDateFromString(mapDriver.get("dateOfBirth")));
					
					drivers.add(driver);
				}
				
				// Necesito saber el numero total de paginas para paginar la lista
				totalPages = (int) map_json.get("totalPages");
				currentPage = (int) map_json.get("number");
				totalElements = (int) map_json.get("totalElements");
				
				dataPage.add(totalPages);
				dataPage.add(currentPage);
				dataPage.add(totalElements);
				dataPage.add(valid_limit);
				dataPage.add(valid_selectedPage);
				
				results.put("drivers", drivers);
				results.put("dataPage", dataPage);
			}
			
		} catch (Throwable oops) {
			log.info("Algo fue mal: " + oops.getMessage());
			
			results = new HashMap<String, List<Object>>();
			dataPage = new ArrayList<Object>();
			drivers = new ArrayList<Object>();
			
			results.put("drivers", drivers);
			results.putIfAbsent("dataPage", dataPage);
		}
		
		return results;
	}
	

	public Map<String, List<Object>> findAll() {
		return this.findAll(Optional.of(0), Optional.of(10));
	}

	// source: https://www.journaldev.com/2552/spring-rest-example-tutorial-spring-restful-web-services
		public Map<String, List<Object>> findByCountry(String country, Optional<Integer> selectedPage,
												 Optional<Integer> limit) {
			Map<String, List<Object>> results;
			List<LinkedHashMap<String, String>> ls_map_drivers;
			int currentPage, totalPages, totalElements, valid_limit,
			    valid_selectedPage, targetPage;
			Map<String, Object> map_json, temp;
			List<Object> drivers;
			List<Object> dataPage;
			Driver driver;
			String url;
			
			try {
				
				url = UtilityService.API_URI_PRE + "/driver/list/country/" + country;
				
				temp = this.utilityService.mapJSON(url, 0, 2);
				totalElements = (int) temp.get("totalElements");
				
				// Validamos campos de la paginacion
				valid_limit = this.utilityService.getValidLimit(limit, 
																totalElements);
				
				valid_selectedPage = this.utilityService.getValidOffset(selectedPage,
																		valid_limit,
																		totalElements);
				
				targetPage = valid_selectedPage - 1;
				map_json = this.utilityService.mapJSON(url, targetPage, valid_limit);
				
				results = new HashMap<String, List<Object>>();
				
				dataPage = new ArrayList<Object>();
				drivers = new ArrayList<Object>();
				
				if (!map_json.isEmpty()) {
					ls_map_drivers = (List<LinkedHashMap<String, String>>) map_json.get("content");
					
					for (LinkedHashMap<String, String> mapDriver: ls_map_drivers) {
						driver = new Driver(mapDriver.get("fullname"),
											mapDriver.get("placeOfBirth"),
											mapDriver.get("country"),
											this.utilityService.getDateFromString(mapDriver.get("dateOfBirth")));
						
						drivers.add(driver);
					}
					
					// Necesito saber el numero total de paginas para paginar la lista
					totalPages = (int) map_json.get("totalPages");
					currentPage = (int) map_json.get("number");
					totalElements = (int) map_json.get("totalElements");
					
					dataPage.add(totalPages);
					dataPage.add(currentPage);
					dataPage.add(totalElements);
					dataPage.add(valid_limit);
					dataPage.add(valid_selectedPage);
					
					results.put("drivers", drivers);
					results.put("dataPage", dataPage);
				}
				
			} catch (Throwable oops) {
				log.info("Algo fue mal: " + oops.getMessage());
				
				results = new HashMap<String, List<Object>>();
				dataPage = new ArrayList<Object>();
				drivers = new ArrayList<Object>();
				
				results.put("drivers", drivers);
				results.putIfAbsent("dataPage", dataPage);
			}
			
			return results;
		}
	
}
