package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class ConstructorStandingForm extends PaginationForm {

	@Pattern(regexp = "^[0-9]{0,4}$")
	//@Range(min = 1958, max = 2018)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String season;
	
	@Pattern(regexp = "^[0-9]{0,2}$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String position;
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String constructor;
	
	public ConstructorStandingForm() {
		super();
	}
	
	public ConstructorStandingForm(int offset) {
		super(offset);
	}
	
	public ConstructorStandingForm(int offset, String season, String position, String constructor) {
		super(offset);
		
		this.season = season;
		this.position = position;
		this.constructor = constructor;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getConstructor() {
		return constructor;
	}

	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}
	
	@Override
	public String toString() {
		return "ConstructorStandingForm [season=" + this.season + ", position=" + this.position + ", constructor=" + this.constructor + "]";
	}
	
}
