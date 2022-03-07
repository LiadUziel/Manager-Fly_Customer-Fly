package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

public class Shift {

	private String shiftID;
	private LocalDate startDate;
	private LocalTime startHour;
	private LocalDate endDate;
	private LocalTime endHour;
	
	
	
	public Shift(String shiftID, LocalDate startDate, LocalTime startHour, LocalDate endDate, LocalTime endHour) {
		super();
		this.shiftID = shiftID;
		this.startDate = startDate;
		this.startHour = startHour;
		this.endDate = endDate;
		this.endHour = endHour;
	}

	
	


	public LocalDate getStartDate() {
		return startDate;
	}



	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}



	public LocalTime getStartHour() {
		return startHour;
	}



	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}



	public LocalDate getEndDate() {
		return endDate;
	}



	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}



	public LocalTime getEndHour() {
		return endHour;
	}



	public void setEndHour(LocalTime endHour) {
		this.endHour = endHour;
	}





	//-- Maybe change to connection table
	private HashSet<GroundAttendant> groundAttendants;
	
	

	public Shift(String shiftID) {
		super();
		this.shiftID = shiftID;
		
		groundAttendants = new HashSet<GroundAttendant>();
	}



	public String getShiftID() {
		return shiftID;
	}
	public void setShiftID(String shiftID) {
		this.shiftID = shiftID;
	}
	
	public HashSet<GroundAttendant> getGroundAttendants() {
		return groundAttendants;
	}
	public void setGroundAttendants(HashSet<GroundAttendant> groundAttendants) {
		this.groundAttendants = groundAttendants;
	}



	@Override
	public String toString() {
		return "Shift [shiftID=" + shiftID + "]";
	}



	public double calcDuration() {
		// TODO - implement Shift.calcDuration
		throw new UnsupportedOperationException();
	}

}