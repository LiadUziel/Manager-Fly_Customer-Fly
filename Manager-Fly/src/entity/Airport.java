package entity;

import java.time.LocalTime;
import java.util.Objects;

public class Airport {

	private String airportID;
	private String country;
	private String city;
	private double timeZone_GMT;

	// Constructor
	public Airport(String airportID, String country, String city, double timeZone_GMT) {
		super();
		this.airportID = airportID;
		this.country = country;
		this.city = city;
		this.timeZone_GMT = timeZone_GMT;
	}

	

	// Getters & Setters
	public String getAirportID() {
		return airportID;
	}



	public void setAirportID(String airportID) {
		this.airportID = airportID;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public double getTimeZone_GMT() {
		return timeZone_GMT;
	}



	public void setTimeZone_GMT(double timeZone_GMT) {
		this.timeZone_GMT = timeZone_GMT;
	}


	

	@Override
	public int hashCode() {
		return Objects.hash(airportID);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		return Objects.equals(airportID, other.airportID);
	}



	@Override
	public String toString() {
		return "Airport [airportID=" + airportID + ", country=" + country + ", city=" + city + ", timeZone_GMT="
				+ timeZone_GMT + "]";
	}



	/**
	 * 
	 * @param gmtValue
	 */
	public LocalTime findCurrentTime(double gmtValue) {
		// TODO - implement Airport.findCurrentTime
		throw new UnsupportedOperationException();
	}

}