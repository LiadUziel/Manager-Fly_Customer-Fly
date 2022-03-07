package entity;

import java.time.LocalDate;

public class Order {
	
	private String orderId;
	private PaymentMethod paymentMethod;
	private LocalDate orderDate;
	
	
	public Order(String orderId, PaymentMethod paymentMethod, LocalDate orderDate) {
		super();
		this.orderId = orderId;
		this.paymentMethod = paymentMethod;
		this.orderDate = orderDate;
	}


	
	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public LocalDate getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}



	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", paymentMethod=" + paymentMethod + ", orderDate=" + orderDate + "]";
	}
	

}
