package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightAttendantInFlight {

	private String flightID;
	private String faID;
	
	
	private LocalDate departureDate;
	private LocalTime departureHour;
	private LocalDate landingDate;
	private LocalTime landingHour;
	
	
	public FlightAttendantInFlight(LocalDate departureDate, LocalTime departureHour, LocalDate landingDate,
			LocalTime landingHour) {
		super();
		this.departureDate = departureDate;
		this.departureHour = departureHour;
		this.landingDate = landingDate;
		this.landingHour = landingHour;
	}
	
	public FlightAttendantInFlight(String flightID, String faID) {
		super();
		this.flightID = flightID;
		this.faID = faID;
	}

	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public String getFaID() {
		return faID;
	}

	public void setFaID(String faID) {
		this.faID = faID;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}
	
	public LocalTime getDepartureHour() {
		return departureHour;
	}
	public void setDepartureHour(LocalTime departureHour) {
		this.departureHour = departureHour;
	}

	public LocalDate getLandingDate() {
		return landingDate;
	}
	public void setLandingDate(LocalDate landingDate) {
		this.landingDate = landingDate;
	}

	public LocalTime getLandingHour() {
		return landingHour;
	}
	public void setLandingHour(LocalTime landingHour) {
		this.landingHour = landingHour;
	}


	@Override
	public String toString() {
		return "FlightAttendantInFlight [departureDate=" + departureDate + ", departureHour=" + departureHour
				+ ", landingDate=" + landingDate + ", landingHour=" + landingHour + "]";
	}
	
	
	
	
	
	

}