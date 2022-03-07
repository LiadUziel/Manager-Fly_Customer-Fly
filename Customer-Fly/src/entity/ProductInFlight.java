package entity;

public class ProductInFlight {
	
	private String productID, supplierID, feedback;

	public ProductInFlight(String productID, String supplierID, String feedback) {
		super();
		this.productID = productID;
		this.supplierID = supplierID;
		this.feedback = feedback;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "ProductInFlight [productID=" + productID + ", supplierID=" + supplierID + ", feedback=" + feedback
				+ "]";
	}
	
	

}
