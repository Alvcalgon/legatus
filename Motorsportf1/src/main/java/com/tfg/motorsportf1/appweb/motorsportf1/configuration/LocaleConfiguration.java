/*
 * LocaleConfiguration.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package com.tfg.motorsportf1.appweb.motorsportf1.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

	// WebMvcConfigurer interface ---------------------------------------------

	// In order to take effect, this bean needs to be added to the
	// application's interceptor registry
	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor;

		interceptor = this.localeChangeInterceptor();
		registry.addInterceptor(interceptor);
	}

	// Beans ------------------------------------------------------------------

	// The Locale Resolver interface has implementations that determine
	// the current locale based on the session, cookies, the Accept-
	// Language header or a fixed value.
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver result = new SessionLocaleResolver();

		result.setDefaultLocale(Locale.ENGLISH);

		return result;
	}

	// We need to add an interceptor bean that will switch to a new
	// locale based on the value of the language parameter appended
	// to a request
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor result;

		result = new LocaleChangeInterceptor();
		result.setParamName("language");

		return result;
	}

}
