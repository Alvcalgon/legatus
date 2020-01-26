package com.tfg.motorsportf1.appweb.motorsportf1.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

	public String getCurrentMomentString() {
		SimpleDateFormat formatter;
		String result;
		
		formatter = (LocaleContextHolder.getLocale().getLanguage() == "es") ? new SimpleDateFormat("dd/MM/yyyy HH:mm") : new SimpleDateFormat("yyyy/MM/dd HH:mm");
		result = formatter.format(new Date());
		
		return result;
	}
	
}
