package entity;

public class FlightTicket {
	
	private String ticketNumber;
	private String mealType;
	private String seatClass;
	private double price;
	private String orderId;
	private String flightNumber;
	
	public FlightTicket(String ticketNumber, String mealType, String seatClass, String orderId,
			String flightNumber) {
		super();
		this.ticketNumber = ticketNumber;
		this.mealType = mealType;
		this.seatClass = seatClass;
		this.orderId = orderId;
		this.flightNumber = flightNumber;
	}

	
	
	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}



	@Override
	public String toString() {
		return "FlightTicket [ticketNumber=" + ticketNumber + ", mealType=" + mealType + ", seatClass=" + seatClass
				+ ", price=" + price + ", orderId=" + orderId + ", flightNumber=" + flightNumber + "]";
	}
	


}
