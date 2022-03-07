package entity;

import java.time.LocalDate;

public class Pilot extends Employee {

	private String licenceNumber;
	private LocalDate licenceIssueDate;
	
	public Pilot(String exployeeID, String firstName, String lastName, LocalDate startWorkDate, LocalDate endWorkDate,
			String licenceNumber, LocalDate licenceIssueDate) {
		super(exployeeID, firstName, lastName, startWorkDate, endWorkDate);
		this.licenceNumber = licenceNumber;
		this.licenceIssueDate = licenceIssueDate;
	}

	
	public String getLicenceNumber() {
		return licenceNumber;
	}
	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public LocalDate getLicenceIssueDate() {
		return licenceIssueDate;
	}
	public void setLicenceIssueDate(LocalDate licenceIssueDate) {
		this.licenceIssueDate = licenceIssueDate;
	}


	@Override
	public String toString() {
		return "Pilot [licenceNumber=" + licenceNumber + ", licenceIssueDate=" + licenceIssueDate + "]";
	}

}