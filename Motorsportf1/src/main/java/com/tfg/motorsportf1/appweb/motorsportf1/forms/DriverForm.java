package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class DriverForm extends PaginationForm {
	
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String fullname;
	
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String country;
	
	
	public DriverForm() {
		super();
	}
	
	public DriverForm(int offset) {
		super(offset);
	}
	
	public DriverForm(int offset, String fullname, String country) {
		super(offset);
		
		this.fullname = fullname;
		this.country = country;
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
		return "DriverForm [fullname=" + this.fullname + ", country=" + this.country + "]";
	}
	
}
