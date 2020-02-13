package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class DriverForm {
	
	@NotNull
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String fullname;
	
	@NotNull
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String country;
	
//	@Valid
//	@NotNull
//	private PaginationForm paginationForm;
	
	public DriverForm() {
		super();
	}
	
//	public DriverForm(PaginationForm paginationForm) {
//		super();
//		
//		this.paginationForm = paginationForm;
//	}

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
	
//	public PaginationForm getPaginationForm() {
//		return paginationForm;
//	}
//
//	public void setPaginationForm(PaginationForm paginationForm) {
//		this.paginationForm = paginationForm;
//	}

	@Override
	public String toString() {
		return "DriverSearch [fullname=" + this.fullname + ", country=" + this.country + "]";
	}
	
}
