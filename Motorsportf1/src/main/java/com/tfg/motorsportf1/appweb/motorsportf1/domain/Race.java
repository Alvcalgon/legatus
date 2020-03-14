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
	
	
	
	public Race(String season, Date raceDate, String event, Circuit circuit) {
		super();
		
		this.season = season;
		this.raceDate = raceDate;
		this.event = event;
		this.circuit = circuit;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((circuit == null) ? 0 : circuit.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((raceDate == null) ? 0 : raceDate.hashCode());
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Race other = (Race) obj;
		if (circuit == null) {
			if (other.circuit != null)
				return false;
		} else if (!circuit.equals(other.circuit))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (raceDate == null) {
			if (other.raceDate != null)
				return false;
		} else if (!raceDate.equals(other.raceDate))
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		return true;
	}
	
}