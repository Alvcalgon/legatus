package com.tfg.motorsportf1.appweb.motorsportf1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Constructor {

	// Atributos --------------------------------------------
	private String name;
	
	private String country;
	
	private String principal;
	
	// Constructores -----------------------------------------
	public Constructor() {
		super();
	}
	
	// Getters y setters ---------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	

	@Override
	public String toString() {
		return "Constructor [name=" + this.name + "]";
	}
	
}
