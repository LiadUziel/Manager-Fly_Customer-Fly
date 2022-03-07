package entity;


public class Plane {

	private String taleID;
	private int requiredFlightAttendants;
	

	
	public Plane(String taleID, int requiredFlightAttendants, int seatsInEconomy) {
		super();
		this.taleID = taleID;
		this.requiredFlightAttendants = requiredFlightAttendants;
		
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
	
	


	@Override
	public String toString() {
		return "Plane [taleID=" + taleID + ", requiredFlightAttendants=" + requiredFlightAttendants + "]";
	}




}