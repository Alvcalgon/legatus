package com.tfg.motorsportf1.appweb.motorsportf1.domain;

public class DriverStanding {

	// Atributos --------------------------
	private String season;
	
	private Double points;
	
	private String position;
	
	private Integer wins;
	
	private Driver driver;
	
	private Constructor constructor;
	
	// Constructores ---------------------
	public DriverStanding() {
		super();
	}
	
	public DriverStanding(String season, Double points, String position,
			              Integer wins, Driver driver, Constructor constructor) {
		super();
		
		this.season = season;
		this.points = points;
		this.position = position;
		this.wins = wins;
		this.driver = driver;
		this.constructor = constructor;
	}
	
	// Getter y setters -------------------	
	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
	public Constructor getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor constructor) {
		this.constructor = constructor;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constructor == null) ? 0 : constructor.hashCode());
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
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
		DriverStanding other = (DriverStanding) obj;
		if (constructor == null) {
			if (other.constructor != null)
				return false;
		} else if (!constructor.equals(other.constructor))
			return false;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DriverStanding [season=" + season + ", points=" + points + ", position=" + position + ", driver="
				+ driver + "]";
	}
	
}
