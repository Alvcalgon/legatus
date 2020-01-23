package com.tfg.motorsportf1.appweb.motorsportf1.domain;

import java.util.Date;




public class Driver {
	
	// Atributos --------------------------
	private String fullname;
	
	private String placeOfBirth;
	
	private String country;
	
	private Date dateOfBirth;	
	
	
	// Constructores -------------------------
	public Driver() {
		super();
	}
	
	
	// Getters y setters ----------------------	
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	@Override
	public String toString() {
		return "Driver [fullname=" + this.fullname + ", country=" + this.country + "]";
	}
	
}
