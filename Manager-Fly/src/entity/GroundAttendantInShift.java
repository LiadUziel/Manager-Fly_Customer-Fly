package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class GroundAttendantInShift {
	
	private String shiftID;
	private String gaID;
	private String role;
	
	

	public GroundAttendantInShift(String shiftID, String gaID, String role) {
		super();
		this.shiftID = shiftID;
		this.gaID = gaID;
		this.role = role;
	}
	
	
	public String getShiftID() {
		return shiftID;
	}


	public void setShiftID(String shiftID) {
		this.shiftID = shiftID;
	}


	public String getGaID() {
		return gaID;
	}


	public void setGaID(String gaID) {
		this.gaID = gaID;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	private Role attendantRole;
	private LocalDate startDate;
	private LocalTime startHour;
	private LocalDate endDate;
	private LocalTime endHour;
	
	
	public GroundAttendantInShift(Role attendantRole, LocalDate startDate, LocalTime startHour, LocalDate endDate,
			LocalTime endHour) {
		super();
		this.attendantRole = attendantRole;
		this.startDate = startDate;
		this.startHour = startHour;
		this.endDate = endDate;
		this.endHour = endHour;
	}


	public Role getAttendantRole() {
		return attendantRole;
	}
	public void setAttendantRole(Role attendantRole) {
		this.attendantRole = attendantRole;
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

}