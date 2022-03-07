package entity;

public class Seat {

	private int rowNumber;
	private int seatNumber;
	private SeatClass seatClass;
	private int seatID;
	private static int idCounter = 1;
	private String planeID;
	
	
	public Seat(int rowNumber, int seatNumber, SeatClass seatClass, String planeID) {
		super();
		this.rowNumber = rowNumber;
		this.seatNumber = seatNumber;
		this.seatClass = seatClass;
		this.planeID = planeID;
		this.seatID = idCounter++;

	}


	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}
	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}

	public int getSeatID() {
		return seatID;
	}
	public void setSeatID(int seatID) {
		this.seatID = seatID;
	}
	public static int getIdCounter() {
		return idCounter;
	}
	public static void setIdCounter(int idCounter) {
		Seat.idCounter = idCounter;
	}
	public String getPlaneID() {
		return planeID;
	}
	public void setPlaneID(String planeID) {
		this.planeID = planeID;
	}


	@Override
	public String toString() {
		return "Seat [rowNumber=" + rowNumber + ", seatNumber=" + seatNumber + ", seatClass=" + seatClass + ", seatID="
				+ seatID + "]";
	}

}