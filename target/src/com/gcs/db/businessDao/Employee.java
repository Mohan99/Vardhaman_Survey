package com.gcs.db.businessDao;
// Generated Nov 7, 2017 5:49:38 PM by Hibernate Tools 5.2.3.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Employee generated by hbm2java
 */
@SuppressWarnings("serial")
public class Employee implements java.io.Serializable {

	@Id
	@GeneratedValue
	private int empId;
	private Category employeeCategory;
	private Cities employeeCity;
	private Countries employeeCountry;
	private State employeeState;
	private String employeeId;
	private String employeeName;
	private String employeeDesg;
	private String employeeSpecialization;
	private String employeeMobilenumber;
	private boolean status;
	@SuppressWarnings("rawtypes")
	private Set resourceallocationses = new HashSet(0);

	public Employee() {
	}

	public Employee(Category category, Cities cities, Countries countries, State states, String employeeId,
			String employeeName, String employeeDesg, String employeeSpecialization, String employeeMobilenumber,
			boolean status) {
		this.employeeCategory = category;
		this.employeeCity = cities;
		this.employeeCountry = countries;
		this.employeeState = states;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeDesg = employeeDesg;
		this.employeeSpecialization = employeeSpecialization;
		this.employeeMobilenumber = employeeMobilenumber;
		this.status = status;
	}

	@SuppressWarnings("rawtypes")
	public Employee(Category category, Cities cities, Countries countries, State states, String employeeId,
			String employeeName, String employeeDesg, String employeeSpecialization, String employeeMobilenumber,
			boolean status, Set resourceallocationses) {
		this.employeeCategory = category;
		this.employeeCity = cities;
		this.employeeCountry = countries;
		this.employeeState = states;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeDesg = employeeDesg;
		this.employeeSpecialization = employeeSpecialization;
		this.employeeMobilenumber = employeeMobilenumber;
		this.status = status;
		this.resourceallocationses = resourceallocationses;
	}

	
	
	
	public Integer getEmpId() {
		return this.empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Category getEmployeeCategory() {
		return this.employeeCategory;
	}

	public void setEmployeeCategory(Category category) {
		this.employeeCategory = category;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeDesg() {
		return this.employeeDesg;
	}

	public void setEmployeeDesg(String employeeDesg) {
		this.employeeDesg = employeeDesg;
	}

	public String getEmployeeSpecialization() {
		return this.employeeSpecialization;
	}

	public void setEmployeeSpecialization(String employeeSpecialization) {
		this.employeeSpecialization = employeeSpecialization;
	}

	public String getEmployeeMobilenumber() {
		return this.employeeMobilenumber;
	}

	public void setEmployeeMobilenumber(String employeeMobilenumber) {
		this.employeeMobilenumber = employeeMobilenumber;
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@SuppressWarnings("rawtypes")
	public Set getResourceallocationses() {
		return this.resourceallocationses;
	}

	@SuppressWarnings("rawtypes")
	public void setResourceallocationses(Set resourceallocationses) {
		this.resourceallocationses = resourceallocationses;
	}

	public Cities getEmployeeCity() {
		return employeeCity;
	}

	public void setEmployeeCity(Cities employeeCity) {
		this.employeeCity = employeeCity;
	}

	public Countries getEmployeeCountry() {
		return employeeCountry;
	}

	public void setEmployeeCountry(Countries employeeCountry) {
		this.employeeCountry = employeeCountry;
	}

	public State getEmployeeState() {
		return employeeState;
	}

	public void setEmployeeState(State employeeState) {
		this.employeeState = employeeState;
	}


	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", category=" + employeeCategory + ", employeeCity=" + employeeCity
				+ ", employeeCountry=" + employeeCountry + ", employeeState=" + employeeState + ", employeeId="
				+ employeeId + ", employeeName=" + employeeName + ", employeeDesg=" + employeeDesg
				+ ", employeeSpecialization=" + employeeSpecialization + ", employeeMobilenumber="
				+ employeeMobilenumber + ", status=" + status + ", resourceallocationses=" + resourceallocationses
				+ "]";
	}

}
