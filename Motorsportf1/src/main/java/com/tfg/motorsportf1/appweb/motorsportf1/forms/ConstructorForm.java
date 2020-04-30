package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class ConstructorForm extends PaginationForm {

	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;
	
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String nationality;

	
	public ConstructorForm() {
		super();
	}

	public ConstructorForm(Integer offset) {
		super(offset);
	}

	public ConstructorForm(int offset, String name, String nationality) {
		super(offset);
		
		this.name = name;
		this.nationality = nationality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Override
	public String toString() {
		return "ConstructorForm [name=" + name + ", nationality=" + nationality + "]";
	}
		
}
