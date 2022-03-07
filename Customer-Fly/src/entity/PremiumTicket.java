package entity;

public class PremiumTicket extends FlightTicket{
	
	private double maxWeight;
	private String personalRequirement_1, personalRequirement_2, personalRequirement_3;
	
	
	public PremiumTicket(String ticketNumber, String mealType, String seatClass, String orderId,
			String flightNumber, double maxWeight, String personalRequirement_1, String personalRequirement_2, String personalRequirement_3) {
		super(ticketNumber, mealType, seatClass, orderId, flightNumber);

		this.maxWeight = maxWeight;
		this.personalRequirement_1 = personalRequirement_1;
		this.personalRequirement_2 = personalRequirement_2;
		this.personalRequirement_3 = personalRequirement_3;
	}


	public double getMaxWeight() {
		return maxWeight;
	}


	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}


	public String getPersonalRequirement_1() {
		return personalRequirement_1;
	}


	public void setPersonalRequirement_1(String personalRequirement_1) {
		this.personalRequirement_1 = personalRequirement_1;
	}


	public String getPersonalRequirement_2() {
		return personalRequirement_2;
	}


	public void setPersonalRequirement_2(String personalRequirement_2) {
		this.personalRequirement_2 = personalRequirement_2;
	}


	public String getPersonalRequirement_3() {
		return personalRequirement_3;
	}


	public void setPersonalRequirement_3(String personalRequirement_3) {
		this.personalRequirement_3 = personalRequirement_3;
	}


	@Override
	public String toString() {
		return "PremiumTicket [maxWeight=" + maxWeight + ", personalRequirement_1=" + personalRequirement_1
				+ ", personalRequirement_2=" + personalRequirement_2 + ", personalRequirement_3="
				+ personalRequirement_3 + "]";
	}
	
	
	
	
	

}
