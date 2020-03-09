package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class ConstructorForm extends PaginationForm {

	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String country;

	
	public ConstructorForm() {
		super();
	}

	public ConstructorForm(Integer offset) {
		super(offset);
	}

	public ConstructorForm(int offset, String name, String country) {
		super(offset);
		
		this.name = name;
		this.country = country;
	}

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

	
	@Override
	public String toString() {
		return "ConstructorForm [name=" + this.name + ", country=" + this.country + "]";
	}
		
}
