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

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Circuit;

@Service
public class CircuitService {

	private static final Log log = LogFactory.getLog(CircuitService.class);

	@Autowired
	private UtilityService utilityService;

	public CircuitService() {
		super();
	}

	public Object findOne(String name) {
		Object result;
		String encodedName, url;

		try {
			encodedName = this.utilityService.getEncodedText(name);

			url = UtilityService.API_URI_PRE + "/circuit/display/" + encodedName;

			result = this.utilityService.stringMapJSON(url);
		} catch (Throwable oops) {
			result = null;

			log.info("CircuitService::findOne - Error al recuperar el piloto cuyo nombre completo es: " + name);
		}

		return result;
	}

	public Map<String, List<Object>> findAll() {
		return this.findAll(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
	}

	public Map<String, List<Object>> findAll(Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/circuit/list";

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	public Map<String, List<Object>> findByType(String type, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedType;

		encodedType = this.utilityService.getEncodedText(type);
		
		url = UtilityService.API_URI_PRE + "/circuit/list/type/" + encodedType;
		
		results = this.getDataPaginationAndObjects(url, selectedPage);
		
		return results;
	}
	
	public Map<String, List<Object>> findByLocation(String location, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedLocation;

		encodedLocation = this.utilityService.getEncodedText(location);
		
		url = UtilityService.API_URI_PRE + "/circuit/list/location/" + encodedLocation;
		
		results = this.getDataPaginationAndObjects(url, selectedPage);
		
		return results;
	}
	
	public List<Object> findBySeason(String season) {
		List<Object> results;
		String url, encodedSeason;

		encodedSeason = this.utilityService.getEncodedText(season);
		
		url = UtilityService.API_URI_PRE + "/circuit/list/season/" + encodedSeason;
		
		results = this.getDataObjects(url);
		
		return results;
	}
	
	public Map<String, List<Object>> findByName(String name, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedName;

		encodedName = this.utilityService.getEncodedText(name);
		
		url = UtilityService.API_URI_PRE + "/circuit/list/name/" + encodedName;
		
		results = this.getDataPaginationAndObjects(url, selectedPage);
		
		return results;
	}
	
	private Map<String, List<Object>> getDataPaginationAndObjects(String url, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, String>> ls_map_circuits;
		String type, lapDistance, location, name;
		int totalPages, totalElements, valid_selectedPage, targetPage;
		Map<String, Object> map_json, temp;
		List<Object> circuits;
		List<Object> dataPage;
		Circuit circuit;

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
			circuits = new ArrayList<Object>();

			if (!map_json.isEmpty()) {
				ls_map_circuits = (List<LinkedHashMap<String, String>>) map_json.get("content");

				if (!ls_map_circuits.isEmpty()) {
					for (LinkedHashMap<String, String> mapCircuit : ls_map_circuits) {
						name = this.utilityService.getStringFromKey(mapCircuit, "name");
						location = this.utilityService.getStringFromKey(mapCircuit, "location");
						type = this.utilityService.getStringFromKey(mapCircuit, "type");
						lapDistance = this.utilityService.getStringFromKey(mapCircuit, "lapDistance");
						
						circuit = new Circuit(name, location, type, lapDistance);

						circuits.add(circuit);
					}
				}

				totalPages = (int) map_json.get("totalPages");
				totalElements = (int) map_json.get("totalElements");

				dataPage = this.utilityService.fillDataPage(totalPages, totalElements, valid_selectedPage);
			}

			results.put("circuits", circuits);
			results.put("dataPage", dataPage);

		} catch (Throwable oops) {
			log.info(
					"CircuitService::getDataPaginationAndObjects - Algo fue mal al recuperar los objetos y datos de la paginacion: "
							+ oops.getMessage());

			results = new HashMap<String, List<Object>>();

			circuits = new ArrayList<Object>();
			dataPage = this.utilityService.fillDataPage(-1, -1, UtilityService.DEFAULT_OFFSET_TO_USER);

			results.put("circuits", circuits);
			results.put("dataPage", dataPage);
		}

		return results;
	}

	private List<Object> getDataObjects(String url) {
		List<LinkedHashMap<String, String>> list_json;
		String type, lapDistance, location, name;
		List<Object> circuits;
		Circuit circuit;

		try {
			list_json = this.utilityService.listJSON(url);

			circuits = new ArrayList<Object>();

			if (!list_json.isEmpty()) {
				for (LinkedHashMap<String, String> mapCircuit : list_json) {
					name = this.utilityService.getStringFromKey(mapCircuit, "name");
					location = this.utilityService.getStringFromKey(mapCircuit, "location");
					type = this.utilityService.getStringFromKey(mapCircuit, "type");
					lapDistance = this.utilityService.getStringFromKey(mapCircuit, "lapDistance");
					
					circuit = new Circuit(name, location, type, lapDistance);

					circuits.add(circuit);
				}
			}
			
		} catch (Throwable oops) {
			log.info(
					"CircuitService::getDataPaginationAndObjects - Algo fue mal al recuperar los objetos y datos de la paginacion: "
							+ oops.getMessage());

			circuits = new ArrayList<Object>();
		}

		return circuits;
	}
		
}
