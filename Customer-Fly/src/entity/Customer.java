package entity;

import java.time.LocalDate;

public class Customer {
	
	private String passportNumber;
	private String firstName;
	private String lastName;
	private String email;
	private String primaryCitizenship;
	private LocalDate dateOfBirth;
	
	
	public Customer(String passportNumber, String firstName, String lastName, String email, String primaryCitizenship,
			LocalDate dateOfBirth) {
		super();
		this.passportNumber = passportNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.primaryCitizenship = primaryCitizenship;
		this.dateOfBirth = dateOfBirth;
	}


	public String getPassportNumber() {
		return passportNumber;
	}


	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPrimaryCitizenship() {
		return primaryCitizenship;
	}


	public void setPrimaryCitizenship(String primaryCitizenship) {
		this.primaryCitizenship = primaryCitizenship;
	}


	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	@Override
	public String toString() {
		return "Customer [passportNumber=" + passportNumber + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", primaryCitizenship=" + primaryCitizenship + ", dateOfBirth=" + dateOfBirth
				+ "]";
	}
	
	
	
	
	
	

}
