package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class DriverForm extends PaginationForm {
	
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String fullname;
	
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String nationality;
	
	
	public DriverForm() {
		super();
	}
	
	public DriverForm(int offset) {
		super(offset);
	}
	
	public DriverForm(int offset, String fullname, String nationality) {
		super(offset);
		
		this.fullname = fullname;
		this.nationality = nationality;
	}
	
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	@Override
	public String toString() {
		return "DriverForm [fullname=" + this.fullname + ", nationality=" + this.nationality + "]";
	}
	
}
