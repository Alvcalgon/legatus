package com.tfg.motorsportf1.appweb.motorsportf1.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

public class RaceForm extends PaginationForm {

	@NotNull
	@Pattern(regexp = "^[0-9]{0,4}$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String season;
	
	@NotNull
	@Pattern(regexp = "^[^0-9]*$")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String event;

	public RaceForm() {
		super();
	}
	
	public RaceForm(Integer offset) {
		super(offset);
	}

	public RaceForm(Integer offset, String season, String event) {
		super(offset);
		
		this.season = season;
		this.event = event;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	
	@Override
	public String toString() {
		return "RaceForm [season=" + season + ", event=" + event + "]";
	}
	
}
