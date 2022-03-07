package entity;

import java.time.LocalDateTime;

public class Flight {
		
	
	private String flightNumber;
	private LocalDateTime departureDate;
	private String departureAirportId;
	private LocalDateTime landingDate;
	private String landingAirportId;
	private FlightStatus flightStatus;
	private String tailNumber;
	
	
	public Flight(String flightNumber, LocalDateTime departureDate, String departureAirportId, LocalDateTime landingDate,
			String landingAirportId, FlightStatus flightStatus, String tailNumber) {
		super();
		this.flightNumber = flightNumber;
		this.departureDate = departureDate;
		this.departureAirportId = departureAirportId;
		this.landingDate = landingDate;
		this.landingAirportId = landingAirportId;
		this.flightStatus = flightStatus;
		this.tailNumber = tailNumber;
		
		
	}


	
	public String getFlightNumber() {
		return flightNumber;
	}


	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}


	public LocalDateTime getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}


	public String getDepartureAirportId() {
		return departureAirportId;
	}


	public void setDepartureAirportId(String departureAirportId) {
		this.departureAirportId = departureAirportId;
	}


	public LocalDateTime getLandingDate() {
		return landingDate;
	}


	public void setLandingDate(LocalDateTime landingDate) {
		this.landingDate = landingDate;
	}


	public String getLandingAirportId() {
		return landingAirportId;
	}


	public void setLandingAirportId(String landingAirportId) {
		this.landingAirportId = landingAirportId;
	}


	public FlightStatus getFlightStatus() {
		return flightStatus;
	}


	public void setFlightStatus(FlightStatus flightStatus) {
		this.flightStatus = flightStatus;
	}


	public String getTailNumber() {
		return tailNumber;
	}


	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}



	@Override
	public String toString() {
		return "Flight [flightNumber=" + flightNumber + ", departureDate=" + departureDate + ", departureAirportId="
				+ departureAirportId + ", landingDate=" + landingDate + ", landingAirportId=" + landingAirportId
				+ ", flightStatus=" + flightStatus + ", tailNumber=" + tailNumber + "]";
	}
	

}
