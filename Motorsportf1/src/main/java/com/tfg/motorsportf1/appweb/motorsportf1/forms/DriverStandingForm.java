package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class DriverStandingForm extends PaginationForm {

	@Pattern(regexp = "^[0-9]{0,4}$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String season;
	
	@Pattern(regexp = "^[0-9]{0,2}$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String position;
	
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String driver;

	
	public DriverStandingForm() {
		super();
	}
	
	public DriverStandingForm(int offset) {
		super(offset);
	}
	
	public DriverStandingForm(int offset, String season, String position, String driver) {
		super(offset);
		
		this.season = season;
		this.position = position;
		this.driver = driver;
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

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
	@Override
	public String toString() {
		return "DriverForm [season=" + this.season + ", position=" + this.position + ", driver=" + this.driver + "]";
	}
	
	
}
