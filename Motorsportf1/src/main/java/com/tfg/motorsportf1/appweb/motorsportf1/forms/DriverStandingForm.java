package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class DriverStandingForm extends PaginationForm {

	@NotNull
	@Pattern(regexp = "^[0-9]{0,4}$")
	//TODO:@Range(min = 1950, max = 2018)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String season;
	
	@NotNull
	@Pattern(regexp = "^[0-9]{0,2}$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String position;
	
	@NotNull
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String driver;

	
	public DriverStandingForm() {
		super();
	}
	
	public DriverStandingForm(int offset, int limit) {
		super(offset, limit);
	}
	
	public DriverStandingForm(int offset, int limit, String season, String position, String driver) {
		super(offset, limit);
		
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
