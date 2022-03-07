package entity;

import java.time.LocalDate;

public class Parameters {

	private LocalDate updateDate;
	private double constDuration, constMeal, constClass;
	
	
	
	public Parameters(LocalDate updateDate, double constDuration, double constMeal, double constClass) {
		super();
		this.updateDate = updateDate;
		this.constDuration = 100;
		this.constMeal = 70;
		this.constClass = 300;
	}
	public LocalDate getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	public double getConstDuration() {
		return constDuration;
	}
	public void setConstDuration(double constDuration) {
		this.constDuration = constDuration;
	}
	public double getConstMeal() {
		return constMeal;
	}
	public void setConstMeal(double constMeal) {
		this.constMeal = constMeal;
	}
	public double getConstClass() {
		return constClass;
	}
	public void setConstClass(double constClass) {
		this.constClass = constClass;
	}
	
	
}
