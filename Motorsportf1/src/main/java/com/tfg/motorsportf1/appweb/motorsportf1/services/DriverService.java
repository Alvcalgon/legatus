package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;

@Service
public class DriverService {

	private static final Log log = LogFactory.getLog(DriverService.class); 
	
	@Autowired
	private UtilityService utilityService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public DriverService() {
		super();
	}
	
	public List<Driver> findAll() {
		
		log.info("Pilotos recuperados");
		return this.repositoryfindAll();
	}
	
	// source: https://www.journaldev.com/2552/spring-rest-example-tutorial-spring-restful-web-services
	public Map<String, List<Object>> findAll(int offset, int limit) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, String>> ls_map_drivers;
		int currentPage, totalPages;
		Map<String, Object> map_json;
		List<Object> drivers;
		List<Object> dataPage;
		Driver driver;
		
		try {
			map_json = this.mapJSON(offset, limit);
			
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
				
				dataPage.add(totalPages);
				dataPage.add(currentPage);
				
				results.put("drivers", drivers);
				results.put("dataPage", dataPage);
			}
			
		} catch (Throwable oops) {
			log.info("Algo fue mal: " + oops.getMessage());
			
			results = new HashMap<String, List<Object>>();
		}
		
		return results;
	}
	
	private Map<String, Object> mapJSON(int offset, int limit) {
		Map<String, Object> results;
		String url;
		URI uri;
		
		url = UtilityService.API_URI_PRE + "/driver/list?offset=" + offset +
				"&limit=" + limit;
		
		try {
			uri = new URI(url);
			
			results = this.restTemplate.getForObject(uri, Map.class);
		} catch (URISyntaxException use) {
			log.info("Error en la url de la API: " + use.getMessage());
		
			results = new HashMap<String, Object>();
		}
		
		return results;
	}
	
	private List<Driver> repositoryfindAll() {
		List<Driver> drivers;
		Driver d;
		
		drivers = new ArrayList<Driver>();
		
		d = new Driver();
		d.setFullname("Fernando Alonso");
		d.setCountry("Spain");
		d.setPlaceOfBirth("Turia, Asturias");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Lewis Hamilton");
		d.setCountry("Great Britain");
		d.setPlaceOfBirth("London, England");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Sebastian Vettel");
		d.setCountry("Germany");
		d.setPlaceOfBirth("Berlin");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Niki Lauda");
		d.setCountry("Austry");
		d.setPlaceOfBirth("Viena");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Sergio Perez");
		d.setCountry("Mexico");
		d.setPlaceOfBirth("Cali");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Fullname 1");
		d.setCountry("Country 1");
		d.setPlaceOfBirth("Place 1");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Fullname 2");
		d.setCountry("Country 2");
		d.setPlaceOfBirth("Place 2");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Fullname 3");
		d.setCountry("Country 3");
		d.setPlaceOfBirth("Place 3");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Fullname 4");
		d.setCountry("Country 4");
		d.setPlaceOfBirth("Place 4");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		d = new Driver();
		d.setFullname("Fullname 5");
		d.setCountry("Country 5");
		d.setPlaceOfBirth("Place 5");
		d.setDateOfBirth(new Date());
		drivers.add(d);
		
		return drivers;
	}
	
}
