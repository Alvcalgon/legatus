package com.tfg.motorsportf1.appweb.motorsportf1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConstructorStanding {

	// Attributos --------------------------
	private String season;
	
	private String position;
	
	private Integer points;
		
	private Constructor constructor;
	
	
	// Constructores ----------------------
	public ConstructorStanding() {
		super();
	}
	
	public ConstructorStanding(String season, 
							   String position, 
							   Integer points, 
							   Constructor constructor) {
		super();
		
		this.season = season;
		this.position = position;
		this.points = points;
		this.constructor = constructor;
	}
	
	// Getters y setters	
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

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Constructor getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}
	
	
	@Override
	public String toString() {
		return "ConstructorStanding [season=" + this.season + ", constructor=" + this.getConstructor() + ", position=" + this.position + ", points" + this.points + "]"; 
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constructor == null) ? 0 : constructor.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		ConstructorStanding other = (ConstructorStanding) obj;
		if (constructor == null) {
			if (other.constructor != null)
				return false;
		} else if (!constructor.equals(other.constructor))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		return true;
	}

}

