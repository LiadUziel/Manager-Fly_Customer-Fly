package entity;

public class EntertainmentProduct {
	
	private String productID;
	private String type;
	private String Name;
	private String description;
	public EntertainmentProduct(String productID, String name, String description, String type) {
		super();
		this.productID = productID;
		this.type = type;
		Name = name;
		this.description = description;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "EntertainmentProduct [productID=" + productID + ", type=" + type + ", Name=" + Name + ", description="
				+ description + "]";
	}
	
	

}
