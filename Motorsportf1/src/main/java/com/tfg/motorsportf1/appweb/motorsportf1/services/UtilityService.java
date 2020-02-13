package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tfg.motorsportf1.appweb.motorsportf1.domain.Driver;

@Service
public class UtilityService {
	
	private static final Log log = LogFactory.getLog(UtilityService.class);
	
	public static final String API_URI_DEPLOY = "https://fone-api.herokuapp.com/fone";
	public static final String API_URI_PRE = "http://localhost:8080/fone";
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public String getCurrentMomentString() {
		SimpleDateFormat formatter;
		String result;
		
		formatter = (LocaleContextHolder.getLocale().getLanguage() == "es") ? new SimpleDateFormat("dd/MM/yyyy HH:mm") : new SimpleDateFormat("yyyy/MM/dd HH:mm");
		result = formatter.format(new Date());
		
		return result;
	}
	
	public Date getDateFromString(String str_date) {
		Date result;
		SimpleDateFormat format;
		
		try {
			format = new SimpleDateFormat("yyyy-MM-dd");
			result = format.parse(str_date);
		} catch (ParseException e) {
			result = null;
			log.info("Error al parsear la fecha");
		}
		
		return result;
	}

	public Map<String, Object> mapJSON(String url, int offset, int limit) {
		Map<String, Object> results;
		URI uri;
		
		try {
			uri = new URI(url + "?offset=" + offset + "&limit=" + limit);
			
			results = this.restTemplate.getForObject(uri, Map.class);
		} catch (URISyntaxException use) {
			log.info("Error en la url de la API: " + use.getMessage());
		
			results = new HashMap<String, Object>();
		} catch (Throwable oops) {
			log.info("Error al parsear los objetos del json");
			
			results = new HashMap<String, Object>();
		}
		
		return results;
	}
	
	public List<LinkedHashMap<String, String>> mapJSON(String url) {
		List<LinkedHashMap<String, String>> results;
		URI uri;
		
		try {
			uri = new URI(url);
			
			results = this.restTemplate.getForObject(uri, List.class);
		} catch (URISyntaxException use) {
			log.info("Error en la url de la API: " + use.getMessage());
			
			results = new ArrayList<LinkedHashMap<String,String>>();
		} catch (Throwable oops) {
			log.info("Error al parsear los objetos del json");
			results = new ArrayList<LinkedHashMap<String,String>>();
		}
		
		return results;
	}
	
	public Object stringMapJSON(String url) {
		Object result;
		URI uri;
		
		try {
			uri = new URI(url);
			
			result = this.restTemplate.getForObject(uri, Object.class);
		} catch (URISyntaxException use) {
			log.info("Error en la url de la API: " + use.getMessage());
			
			result = new Driver();
		}
		
		return result;
	}
	
	public List<Integer> getPages(int totalPages) {
		List<Integer> results;
		
		if (totalPages > 0) {
			results = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
		} else {
			results = new ArrayList<Integer>();
		}
		
		return results;
	}
	
	public int getValidLimit(Optional<Integer>limit, int totalElements) {
		int result;
		
		if (limit != null) {
			result = (limit.isPresent()) ? limit.get() : 10;
			if (result < 1 || result > totalElements) { result = 10; }
		} else {
			result = 10;
		}
		
		
		return result;
	}
	
	public int getValidOffset(Optional<Integer> selectedPage, int limit, int totalElements) {
		int result;
		int totalPages;
		
		if (selectedPage != null) {
			totalPages = (totalElements%limit == 0) ? totalElements/limit : (totalElements/limit)+1; 
			
			result = (selectedPage.isPresent()) ? selectedPage.get() : 1;
			if (result < 1) { result = 1; }
			if (result > totalPages) { result = totalPages; }
		} else {
			result = 1;
		}
		
		return result;
	}
	
}
