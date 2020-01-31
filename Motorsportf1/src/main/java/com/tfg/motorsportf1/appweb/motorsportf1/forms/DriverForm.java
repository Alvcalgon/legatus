package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class DriverForm {
	
	@NotBlank
	@Pattern(regexp = "^[^0-9]+$")
	private String fullname;
	
	@NotNull(message = "It cannot be null")
	@Pattern(regexp = "^[^0-9]*$")
	private String country;
	
	public DriverForm() {
		super();
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	@Override
	public String toString() {
		return "DriverSearch [fullname=" + this.fullname + ", country=" + this.country + "]";
	}
	
}
