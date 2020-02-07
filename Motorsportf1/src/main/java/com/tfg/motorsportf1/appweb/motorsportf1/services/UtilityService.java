package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {
	
	private static final Log log = LogFactory.getLog(UtilityService.class);
	
	public static final String API_URI_DEPLOY = "https://fone-api.herokuapp.com/fone";
	public static final String API_URI_PRE = "http://localhost:8080/fone";
	
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
		}
		
		return result;
	}
	
}
