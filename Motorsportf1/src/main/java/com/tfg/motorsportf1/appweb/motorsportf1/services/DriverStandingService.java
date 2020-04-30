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

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Constructor;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;
import com.tfg.motorsportf1.appweb.motorsportf1.domain.DriverStanding;

@Service
public class DriverStandingService {

	private static final Log log = LogFactory.getLog(DriverStandingService.class);
	
	@Autowired
	private UtilityService utilityService;
	
	public DriverStandingService() {
		super();
	}
	
	
	public Map<String, List<Object>> findBySeason(String season,
												  Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/driver-standing/list/season/" + season;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findByPosition(String position,
													Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/driver-standing/list/position/" + position;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Map<String, List<Object>> findByDriver(String driver,
												  Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;
		String encoded_driver;
		
		encoded_driver = this.utilityService.getEncodedText(driver);

		url = UtilityService.API_URI_PRE + "/driver-standing/list/driver/" + encoded_driver;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}
	
	public Integer findCountByDriver(String driver) {
		Integer result;
		String url;
		String encoded_driver;
		
		encoded_driver = this.utilityService.getEncodedText(driver);
		
		url = UtilityService.API_URI_PRE + "/driver-standing/count/driver/" + encoded_driver;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	public Integer findCountByDriverAndPosition(String driver, String position) {
		Integer result;
		String url;
		String encoded_driver;
		
		encoded_driver = this.utilityService.getEncodedText(driver);
		
		url = UtilityService.API_URI_PRE + "/driver-standing/count/driver/" + encoded_driver +
				"/position/" + position;
		
		result = this.utilityService.countJSON(url);
		
		return result;
	}
	
	private Map<String, List<Object>> getDataPaginationAndObjects(String url,
			Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, Object>> ls_map_driverssStanding;
		int totalPages, totalElements, valid_selectedPage, targetPage;
		String driverFullname, driverInfo, driverNationality, driverDateBirth;
		String constructorName, constructorNationality, constructorInfo;
		String season, position;
		Double points;
		Integer wins;
		Constructor constructor;
		Driver driver;
		Map<String, Object> map_json, temp;
		List<Object> driversStanding;
		List<Object> dataPage;
		DriverStanding ds;
		LinkedHashMap<String, Object> o;

		try {
			temp = this.utilityService.mapJSON(url, 0);
			totalElements = (int) temp.get("totalElements");

			// Validamos offset de la paginacion
			valid_selectedPage = this.utilityService.getValidOffset(selectedPage,
																	totalElements);

			targetPage = valid_selectedPage - 1;
			map_json = this.utilityService.mapJSON(url, targetPage);

			results = new HashMap<String, List<Object>>();

			dataPage = new ArrayList<Object>();
			driversStanding = new ArrayList<Object>();

			if (!map_json.isEmpty()) {
				ls_map_driverssStanding = (List<LinkedHashMap<String, Object>>) map_json.get("content");

				if (!ls_map_driverssStanding.isEmpty()) {
					for (LinkedHashMap<String, Object> mapDriverStanding : ls_map_driverssStanding) {
						// Atributos de driverStanding
						season = (String) mapDriverStanding.get("season");
						position = (String) mapDriverStanding.get("position");
						points = (Double) mapDriverStanding.get("points");
						wins = (Integer) mapDriverStanding.get("wins");
						
						// Atributos de DriverStanding::constructor
						o = (LinkedHashMap<String, Object>) mapDriverStanding.get("constructor");
						constructorName = (String) o.get("name");
						constructorNationality = (String) o.get("nationality");
						constructorInfo = (String) o.get("information");
						
						constructor = new Constructor(constructorName,
													  constructorNationality,
													  constructorInfo);
						
						// Atributos de DriverStanding::driver
						o = (LinkedHashMap<String, Object>) mapDriverStanding.get("driver");
						driverFullname = (String) o.get("fullname");
						driverNationality = (String) o.get("nacionality");
						driverInfo = (String) o.get("information");
						driverDateBirth = (String) o.get("dateOfBirth");
												
						driver = new Driver(driverFullname,
											driverNationality,
											driverDateBirth,
											driverInfo);
											
						ds = new DriverStanding(season, points, position, wins, driver, constructor);

						driversStanding.add(ds);
					}
				}

				totalPages = (int) map_json.get("totalPages");
				totalElements = (int) map_json.get("totalElements");
				
				dataPage = this.utilityService.fillDataPage(totalPages,
															totalElements,
															valid_selectedPage);
			}

			results.put("driversStanding", driversStanding);
			results.put("dataPage", dataPage);

		} catch (Throwable oops) {
			log.info("DriverStandingService::getDataPaginationAndObjects - Algo fue mal al recuperar los objetos y datos de la paginacion: " + oops.getMessage());

			results = new HashMap<String, List<Object>>();

			driversStanding = new ArrayList<Object>();
			dataPage = this.utilityService.fillDataPage(-1, -1, UtilityService.DEFAULT_OFFSET_TO_USER);

			results.put("driversStanding", driversStanding);
			results.put("dataPage", dataPage);
		}

		return results;
	}
	
}
