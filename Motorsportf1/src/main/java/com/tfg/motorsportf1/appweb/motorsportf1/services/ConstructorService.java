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

	public ConstructorService() {
		super();
	}

	public Object findOne(String name) {
		Object result;
		String encodedName, url;

		try {
			encodedName = this.utilityService.getEncodedText(name);

			url = UtilityService.API_URI_PRE + "/constructor/list/display/" + encodedName;

			result = this.utilityService.stringMapJSON(url);

		} catch (Throwable oops) {
			result = null;

			log.info("ConstructorService::findOne - Error al recuperar el piloto cuyo nombre completo es: " + name);
		}

		return result;
	}

	public Map<String, List<Object>> findAll() {
		return this.findAll(Optional.of(UtilityService.DEFAULT_OFFSET_TO_USER),
				Optional.of(UtilityService.DEFAULT_LIMIT));
	}

	public Map<String, List<Object>> findAll(Optional<Integer> selectedPage, Optional<Integer> limit) {
		Map<String, List<Object>> results;
		String url;

		url = UtilityService.API_URI_PRE + "/constructor/list";

		results = this.getDataPaginationAndObjects(url, selectedPage, limit);

		return results;
	}

	public Map<String, List<Object>> findByCountry(String country, Optional<Integer> selectedPage,
			Optional<Integer> limit) {
		Map<String, List<Object>> results;
		String url, encodedCountry;

		encodedCountry = this.utilityService.getEncodedText(country);

		url = UtilityService.API_URI_PRE + "/constructor/list/country/" + encodedCountry;

		results = this.getDataPaginationAndObjects(url, selectedPage, limit);

		return results;
	}

	public Map<String, List<Object>> findByName(String name, Optional<Integer> selectedPage, Optional<Integer> limit) {
		Map<String, List<Object>> results;
		String url, encodedName;

		encodedName = this.utilityService.getEncodedText(name);

		url = UtilityService.API_URI_PRE + "/constructor/list/name/" + encodedName;

		results = this.getDataPaginationAndObjects(url, selectedPage, limit);

		return results;
	}

	public Map<String, List<Object>> findByParameters(String name, String country, Optional<Integer> selectedPage,
			Optional<Integer> limit) {
		Map<String, List<Object>> results;
		String url, encodedName, encodedCountry;

		encodedName = this.utilityService.getEncodedText(name);
		encodedCountry = this.utilityService.getEncodedText(country);

		url = UtilityService.API_URI_PRE + "/constructor/list/country/" + encodedCountry + "/name/" + encodedName;

		results = this.getDataPaginationAndObjects(url, selectedPage, limit);

		return results;
	}

	private Map<String, List<Object>> getDataPaginationAndObjects(String url, Optional<Integer> selectedPage,
			Optional<Integer> limit) {
		Map<String, List<Object>> results;
		List<LinkedHashMap<String, String>> ls_map_constructors;
		int totalPages, totalElements, valid_limit, valid_selectedPage, targetPage;
		String name, country;
		Map<String, Object> map_json, temp;
		List<Object> constructors;
		List<Object> dataPage;
		Constructor constructor;

		try {
			temp = this.utilityService.mapJSON(url, 0, 2);
			totalElements = (int) this.utilityService.getFromMap(temp, "totalElements");

			// Validamos campos de la paginacion
			valid_limit = this.utilityService.getValidLimit(limit, totalElements);
			valid_selectedPage = this.utilityService.getValidOffset(selectedPage, valid_limit, totalElements);

			targetPage = valid_selectedPage - 1;
			map_json = this.utilityService.mapJSON(url, targetPage, valid_limit);

			results = new HashMap<String, List<Object>>();

			dataPage = new ArrayList<Object>();
			constructors = new ArrayList<Object>();

			if (!map_json.isEmpty()) {
				ls_map_constructors = (List<LinkedHashMap<String, String>>) map_json.get("content");

				if (!ls_map_constructors.isEmpty()) {
					for (LinkedHashMap<String, String> mapConstructor : ls_map_constructors) {
						name = mapConstructor.get("name");
						country = mapConstructor.get("country");

						constructor = (mapConstructor.containsKey("principal"))
								? new Constructor(name, country, mapConstructor.get("principal"))
								: new Constructor(name, country);

						constructors.add(constructor);
					}
				}

				totalPages = (int) this.utilityService.getFromMap(map_json, "totalPages");
				totalElements = (int) this.utilityService.getFromMap(map_json, "totalElements");

				dataPage = this.utilityService.fillDataPage(totalPages, totalElements, valid_limit, valid_selectedPage);
			}

			results.put("constructors", constructors);
			results.put("dataPage", dataPage);

		} catch (Throwable oops) {
			log.info(
					"ConstructorService::getDataPaginationAndObjects - Algo fue mal al recuperar los objetos y datos de la paginacion: "
							+ oops.getMessage());

			results = new HashMap<String, List<Object>>();

			constructors = new ArrayList<Object>();
			dataPage = this.utilityService.fillDataPage(-1, -1, UtilityService.DEFAULT_OFFSET_TO_USER,
					UtilityService.DEFAULT_LIMIT);

			results.put("constructors", constructors);
			results.put("dataPage", dataPage);
		}

		return results;
	}

}
