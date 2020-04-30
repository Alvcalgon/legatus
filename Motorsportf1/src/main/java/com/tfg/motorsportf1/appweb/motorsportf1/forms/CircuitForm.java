package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class CircuitForm extends PaginationForm {

	@NotNull
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String location;
		
	@NotNull
	@Pattern(regexp = "^[^0-9]*$")
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String name;

	
	public CircuitForm() {
		super();
	}

	public CircuitForm(Integer offset) {
		super(offset);
	}

	public CircuitForm(Integer offset, String location, String name) {
		super(offset);
		
		this.location = location;
		this.name = name;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CircuitForm [location=" + location + ", name=" + name + "]";
	}
	
}
