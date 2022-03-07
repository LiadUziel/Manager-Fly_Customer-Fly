package entity;

import java.util.HashSet;

public class Plane {

	private String taleID;
	private int requiredFlightAttendants;
	
	private int seatsInEconomy; //--Implement

	//-- Maybe change to connection table
	private HashSet<Seat> seats;

	
	public Plane(String taleID, int requiredFlightAttendants, int seatsInEconomy) {
		super();
		this.taleID = taleID;
		this.requiredFlightAttendants = requiredFlightAttendants;
		this.seatsInEconomy = -1;
		seats = new HashSet<>();
	}


	public String getTaleID() {
		return taleID;
	}
	public void setTaleID(String taleID) {
		this.taleID = taleID;
	}

	public int getRequiredFlightAttendants() {
		return requiredFlightAttendants;
	}
	public void setRequiredFlightAttendants(int requiredFlightAttendants) {
		this.requiredFlightAttendants = requiredFlightAttendants;
	}
	public int getSeatsInEconomy() {
		return seatsInEconomy;
	}
	public void setSeatsInEconomy(int seatsInEconomy) {
		this.seatsInEconomy = seatsInEconomy;
	}
	public HashSet<Seat> getSeats() {
		return seats;
	}
	public void setSeats(HashSet<Seat> seats) {
		this.seats = seats;
	}
	


	@Override
	public String toString() {
		return "Plane [taleID=" + taleID + ", requiredFlightAttendants=" + requiredFlightAttendants + "]";
	}




}