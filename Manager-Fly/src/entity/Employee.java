package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {

	private String exployeeID;
	private String firstName;
	private String lastName;
	private LocalDate startWorkDate;
	private LocalDate endWorkDate;

	// Constructor
	public Employee(String exployeeID, String firstName, String lastName, LocalDate startWorkDate,
			LocalDate endWorkDate) {
		super();
		this.exployeeID = exployeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.startWorkDate = startWorkDate;
		this.endWorkDate = endWorkDate;
	}

	
	



	// Getters & Setters
	public String getExployeeID() {
		return exployeeID;
	}







	public void setExployeeID(String exployeeID) {
		this.exployeeID = exployeeID;
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







	public LocalDate getStartWorkDate() {
		return startWorkDate;
	}







	public void setStartWorkDate(LocalDate startWorkDate) {
		this.startWorkDate = startWorkDate;
	}







	public LocalDate getEndWorkDate() {
		return endWorkDate;
	}







	public void setEndWorkDate(LocalDate endWorkDate) {
		this.endWorkDate = endWorkDate;
	}




	



	@Override
	public int hashCode() {
		return Objects.hash(exployeeID);
	}






	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(exployeeID, other.exployeeID);
	}






	@Override
	public String toString() {
		return "Employee [exployeeID=" + exployeeID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", startWorkDate=" + startWorkDate + ", endWorkDate=" + endWorkDate + "]";
	}






	public boolean updateEmployee() {
		// TODO - implement Employee.updateEmployee
		throw new UnsupportedOperationException();
	}

}