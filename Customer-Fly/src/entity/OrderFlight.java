package entity;

public class OrderFlight {
	
	private String flightNumber;
	private String orderId;
	private boolean hasCancelled;
	
	public OrderFlight(String flightNumber, String orderId, boolean hasCancelled) {
		super();
		this.flightNumber = flightNumber;
		this.orderId = orderId;
		this.hasCancelled = hasCancelled;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean isHasCancelled() {
		return hasCancelled;
	}

	public void setHasCancelled(boolean hasCancelled) {
		this.hasCancelled = hasCancelled;
	}

	
	@Override
	public String toString() {
		return "OrderFlight [flightNumber=" + flightNumber + ", orderId=" + orderId + ", hasCancelled=" + hasCancelled
				+ "]";
	}
	
	

	
}
