package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class CircuitForm extends PaginationForm {

	@NotNull
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String type;
	
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

	public CircuitForm(Integer offset, String type, String location, String name) {
		super(offset);
		
		this.type = type;
		this.location = location;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return "CircuitForm [type=" + type + ", location=" + location + ", name=" + name + "]";
	}
	
}
