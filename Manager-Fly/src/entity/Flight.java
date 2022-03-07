package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

public class Flight {

	private String flightID;
	private Airport departureAirport;
	private Airport landingAirport;
	private LocalDate departureDate;
	private LocalTime departureHour;
	private LocalDate landingDate;
	private LocalTime landingHour;
	private FlightStatus flightStatus;
	
	String depAirport, landAirport, status;
	private Pilot chiefPilot, coPilot;
	private Plane plane;
	private HashSet<FlightAttendant> flightAttendants;

	
	
	
	public Flight(String flightID, String depAirport, String landAirport, String status, LocalDate departureDate, LocalTime departureHour, LocalDate landingDate,
			LocalTime landingHour) {
		super();
		this.flightID = flightID;
		this.departureDate = departureDate;
		this.departureHour = departureHour;
		this.landingDate = landingDate;
		this.landingHour = landingHour;
		this.depAirport = depAirport;
		this.landAirport = landAirport;
		this.status = status;
	}
	
	





	public String getDepAirport() {
		return depAirport;
	}







	public void setDepAirport(String depAirport) {
		this.depAirport = depAirport;
	}







	public String getLandAirport() {
		return landAirport;
	}







	public void setLandAirport(String landAirport) {
		this.landAirport = landAirport;
	}







	public String getStatus() {
		return status;
	}







	public void setStatus(String status) {
		this.status = status;
	}







	public Flight(String flightID, Airport departureAirport, Airport landingAirport, Plane plane, LocalDate departureDate,
			LocalTime departureHour, LocalDate landingDate, LocalTime landingHour, FlightStatus flightStatus) {
		super();
		this.flightID = flightID;
		this.departureAirport = departureAirport;
		this.landingAirport = landingAirport;
		this.departureDate = departureDate;
		this.departureHour = departureHour;
		this.plane = plane;
		this.landingDate = landingDate;
		this.landingHour = landingHour;
		this.flightStatus = flightStatus;
		
		flightAttendants = new HashSet<>();
	}

	
	


	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	public Airport getLandingAirport() {
		return landingAirport;
	}

	public void setLandingAirport(Airport landingAirport) {
		this.landingAirport = landingAirport;
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

	public FlightStatus getFlightStatus() {
		return flightStatus;
	}
	public void setFlightStatus(FlightStatus flightStatus) {
		this.flightStatus = flightStatus;
	}
	

	public Pilot getChiefPilot() {
		return chiefPilot;
	}
	public void setChiefPilot(Pilot chiefPilot) {
		this.chiefPilot = chiefPilot;
	}

	public Pilot getCoPilot() {
		return coPilot;
	}
	public void setCoPilot(Pilot coPilot) {
		this.coPilot = coPilot;
	}

	public Plane getPlane() {
		return plane;
	}
	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public HashSet<FlightAttendant> getFlightAttendants() {
		return flightAttendants;
	}
	public void setFlightAttendants(HashSet<FlightAttendant> flightAttendants) {
		this.flightAttendants = flightAttendants;
	}





	public String getCountryOfLandingAirport() {
		// TODO - implement Flight.getCountryOfLandingAirport
		throw new UnsupportedOperationException();
	}

}