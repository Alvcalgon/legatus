package com.tfg.motorsportf1.appweb.motorsportf1.domain;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Race {

	// Atributos -----------------------	
	private String season;
	
	private Date raceDate;
	
	private String event;
	
	private Circuit circuit;
	
	Set<Result> results;
	
	
	// Constructores -----------------------
	public Race() {
		super();
		
		this.results = new HashSet<Result>();
	}	
	
	// Getters y setters ----------------
	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Date getRaceDate() {
		return raceDate;
	}

	public void setRaceDate(Date raceDate) {
		this.raceDate = raceDate;
	}
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Circuit getCircuit() {
		return circuit;
	}

	public void setCircuit(Circuit circuit) {
		this.circuit = circuit;
	}

	public Set<Result> getResults() {
		return results;
	}

	public void setResults(Set<Result> results) {
		this.results = results;
	}

	
	@Override
	public String toString() {
		return "[Race [season=" + this.season + ", event=" + this.event + "]";
	}
	
	
}