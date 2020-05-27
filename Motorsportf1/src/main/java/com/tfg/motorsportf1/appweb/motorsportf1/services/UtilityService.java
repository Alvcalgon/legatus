package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class UtilityService {
	
	private static final Log log = LogFactory.getLog(UtilityService.class);
	
	//public static final String API_URI = "https://fone-api.herokuapp.com";
	public static final String API_URI = "http://localhost:8080";
	
	public static final int DEFAULT_LIMIT = 10;
	public static final int DEFAULT_OFFSET_TO_USER = 1;
	public static final int DEFAULT_OFFSET_TO_API = 0;
	
	public static final int POS_TOTAL_PAGES = 0;
	public static final int POS_TOTAL_ELEMENTS = 1;
	public static final int POS_OFFSET = 2;
	public static final String CADENA_VACIA = "";
	
	public static final String LAST_SEASON = "2019";
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public <T> T getObjectFromJSON(String url, Class<T> classOutput) {
		T result;
		
		String strJSON = getStringOfJSON(url);
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		
		result = gson.fromJson(strJSON, classOutput);
		
		return result;
	}
	
	public String getURI(String url, Optional<Integer> selectedPage, int totalElements) {
		// Validamos offset
		int valid_selectedPage = this.getValidOffset(selectedPage, totalElements);
		int targetPage = valid_selectedPage - 1;
		
		String result = url + "?offset=" + targetPage; 
		
		return result;
	}
	
	public String getCurrentMomentString() {
		SimpleDateFormat formatter;
		String result;
		
		formatter = (LocaleContextHolder.getLocale().getLanguage() == "es")
						? new SimpleDateFormat("dd/MM/yyyy HH:mm")
						: new SimpleDateFormat("yyyy/MM/dd HH:mm");
						
		result = formatter.format(new Date());
		
		return result;
	}
	
	public List<LinkedHashMap<String, Object>> listJSON(String url) {
		List<LinkedHashMap<String, Object>> results;
		URI uri;
		
		try {
			uri = new URI(url);
			
			results = this.restTemplate.getForObject(uri, List.class);
		} catch (URISyntaxException use) {
			log.info("Error en la url de la API: " + use.getMessage());
		
			results = new ArrayList<LinkedHashMap<String,Object>>();
		} catch (Throwable oops) {
			log.info("Error al parsear los objetos del json: " + oops.getMessage());
			
			results = new ArrayList<LinkedHashMap<String,Object>>();
		}
		
		return results;
	}
	
	public Integer countJSON(String url) {
		Integer result;
		URI uri;
		
		try {
			uri = new URI(url);
			
			result = this.restTemplate.getForObject(uri, Integer.class);
		} catch (URISyntaxException use) {
			log.info("Error en la url de la API: " + use.getMessage());
		
			result = null;
		} catch (Throwable oops) {
			log.info("Error al parsear los objetos del json: " + oops.getMessage());
			
			result = null;
		}
		
		return result;
	}
	
	public String getStringOfJSON(String url) {
		String result;
		URI uri;
		
		try {
			uri = new URI(url);
			
			result = this.restTemplate.getForObject(uri, String.class);
		} catch (URISyntaxException use) {
			log.debug("Error al recuperar el json");
			
			result = "";
		}
		
		return result;
	}
	
	public String getStringOfJSON(String url, int offset) {		
		String uri = url + "?offset=" + offset + "&limit=" + DEFAULT_LIMIT;
		
		String result = this.getStringOfJSON(uri);
		
		return result;
	}
	
	public List<Integer> getPages(int totalPages) {
		List<Integer> results;
		
		results = new ArrayList<Integer>();
		
		if (totalPages > 0) {
			results = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
		}
		
		return results;
	}
	
	public int getValidOffset(Optional<Integer> selectedPage, int totalElements) {
		int result;
		int totalPages;
		
		if (selectedPage != null && totalElements > 0) {
			totalPages = (totalElements%DEFAULT_LIMIT == 0) 
					? totalElements/DEFAULT_LIMIT
					: (totalElements/DEFAULT_LIMIT)+1; 
			
			result = (selectedPage.isPresent()) ? selectedPage.get() : 1;
			if (result < 1) { result = 1; }
			if (result > totalPages) { result = totalPages; }
		} else {
			result = 1;
		}
		
		return result;
	}
	
	protected String getEncodedText(String text) {
		String result;
		
		result = UriUtils.encode(text, "UTF-8");
		
		return result;
	}
	
	
	public String getStringFromKey(LinkedHashMap<String, Object> map, String key) {
		String result;
		
		result = (map.containsKey(key)) ? (String) map.get(key) : UtilityService.CADENA_VACIA;
		
		return result;
	}
		
}
