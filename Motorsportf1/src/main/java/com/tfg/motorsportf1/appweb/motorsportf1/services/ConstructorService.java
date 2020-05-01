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

@Service
public class ConstructorService {

	private static final Log log = LogFactory.getLog(ConstructorService.class);

	@Autowired
	private UtilityService utilityService;

	@Autowired
	private ResultService resultService;
	
	public ConstructorService() {
		super();
	}

	
	public Object findOne(String name) {
		Object result;
		String encodedName, url;

		try {
			encodedName = this.utilityService.getEncodedText(name);

			url = UtilityService.API_URI + "/constructor/display/" + encodedName;

			result = this.utilityService.stringMapJSON(url);

		} catch (Throwable oops) {
			result = null;

			log.info("ConstructorService::findOne - Error al recuperar el piloto cuyo nombre completo es: " + name);
		}

		return result;
	}

	public Map<String, List<Object>> findAll() {
		return this.findAll(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER));
	}

	public Map<String, List<Object>> findAll(Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI + "/constructor/list";

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	public Map<String, List<Object>> findByNationality(String nationality, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedNationality;

		encodedNationality = this.utilityService.getEncodedText(nationality);

		url = UtilityService.API_URI + "/constructor/list/nationality/" + encodedNationality;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	public Map<String, List<Object>> findByName(String name, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedName;

		encodedName = this.utilityService.getEncodedText(name);

		url = UtilityService.API_URI + "/constructor/list/name/" + encodedName;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	public Map<String, List<Object>> findByParameters(String name, String nationality, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		String url, encodedName, encodedNationality;

		encodedName = this.utilityService.getEncodedText(name);
		encodedNationality = this.utilityService.getEncodedText(nationality);

		url = UtilityService.API_URI + "/constructor/list/nationality/" + encodedNationality + "/name/" + encodedName;

		results = this.getDataPaginationAndObjects(url, selectedPage);

		return results;
	}

	private Map<String, List<Object>> getDataPaginationAndObjects(String url, Optional<Integer> selectedPage) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, String>> ls_map_constructors;
		int totalPages, totalElements, valid_selectedPage, targetPage;
		String name, nationality, information;
		Map<String, Object> map_json, temp;
		List<Object> constructors;
		List<Object> dataPage;
		Constructor constructor;

		try {
			temp = this.utilityService.mapJSON(url, 0);
			totalElements = (int) this.utilityService.getFromMap(temp, "totalElements");

			// Validamos offset de la paginacion
			valid_selectedPage = this.utilityService.getValidOffset(selectedPage, totalElements);

			targetPage = valid_selectedPage - 1;
			map_json = this.utilityService.mapJSON(url, targetPage);

			results = new HashMap<String, List<Object>>();

			dataPage = new ArrayList<Object>();
			constructors = new ArrayList<Object>();

			if (!map_json.isEmpty()) {
				ls_map_constructors = (List<LinkedHashMap<String, String>>) map_json.get("content");

				if (!ls_map_constructors.isEmpty()) {
					for (LinkedHashMap<String, String> mapConstructor : ls_map_constructors) {
						name = this.utilityService.getStringFromKey(mapConstructor, "name");
						nationality = this.utilityService.getStringFromKey(mapConstructor, "nationality");
						information = this.utilityService.getStringFromKey(mapConstructor, "information");
						
						constructor = new Constructor(name, nationality, information);

						constructors.add(constructor);
					}
				}

				totalPages = (int) this.utilityService.getFromMap(map_json, "totalPages");
				totalElements = (int) this.utilityService.getFromMap(map_json, "totalElements");

				dataPage = this.utilityService.fillDataPage(totalPages, totalElements, valid_selectedPage);
			}

			results.put("constructors", constructors);
			results.put("dataPage", dataPage);

		} catch (Throwable oops) {
			log.info(
					"ConstructorService::getDataPaginationAndObjects -> Algo fue mal al recuperar los objetos y datos de la paginacion: "
							+ oops.getMessage());

			results = new HashMap<String, List<Object>>();

			constructors = new ArrayList<Object>();
			dataPage = this.utilityService.fillDataPage(-1, -1, UtilityService.DEFAULT_OFFSET_TO_USER);

			results.put("constructors", constructors);
			results.put("dataPage", dataPage);
		}

		return results;
	}

}
