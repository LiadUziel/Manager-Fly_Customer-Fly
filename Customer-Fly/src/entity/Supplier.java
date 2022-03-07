package entity;

public class Supplier {
	
	private String supplierID;
	private String name;
	private String phone;
	private String mail;
	public Supplier(String supplierID, String name, String phone, String mail) {
		super();
		this.supplierID = supplierID;
		this.name = name;
		this.phone = phone;
		this.mail = mail;
	}
	public String getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public String toString() {
		return "Supplier [supplierID=" + supplierID + ", name=" + name + ", phone=" + phone + ", mail=" + mail + "]";
	}
	
	

}
