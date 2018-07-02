package com.gcs.requestDao;

import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.State;

public class EmployeeRequest {

	private Integer empId;
	private Category category;
	private Cities cities;
	private Countries countries;
	private State states;
	private String employeeId;
	private String employeeName;
	private String employeeDesg;
	private String employeeSpecialization;
	private String employeeMobilenumber;
	private boolean status;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Cities getCities() {
		return cities;
	}

	public void setCities(Cities cities) {
		this.cities = cities;
	}

	public Countries getCountries() {
		return countries;
	}

	public void setCountries(Countries countries) {
		this.countries = countries;
	}

	public State getStates() {
		return states;
	}

	public void setStates(State states) {
		this.states = states;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeDesg() {
		return employeeDesg;
	}

	public void setEmployeeDesg(String employeeDesg) {
		this.employeeDesg = employeeDesg;
	}

	public String getEmployeeSpecialization() {
		return employeeSpecialization;
	}

	public void setEmployeeSpecialization(String employeeSpecialization) {
		this.employeeSpecialization = employeeSpecialization;
	}

	public String getEmployeeMobilenumber() {
		return employeeMobilenumber;
	}

	public void setEmployeeMobilenumber(String employeeMobilenumber) {
		this.employeeMobilenumber = employeeMobilenumber;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "EmployeeRequest [empId=" + empId + ", category=" + category + ", cities=" + cities + ", countries="
				+ countries + ", states=" + states + ", employeeId=" + employeeId + ", employeeName=" + employeeName
				+ ", employeeDesg=" + employeeDesg + ", employeeSpecialization=" + employeeSpecialization
				+ ", employeeMobilenumber=" + employeeMobilenumber + ", status=" + status + "]";
	}

}
