package com.gcs.db.businessDao;
// Generated Nov 7, 2017 5:49:38 PM by Hibernate Tools 5.2.3.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * States generated by hbm2java
 */
@SuppressWarnings("serial")
public class States implements java.io.Serializable {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int countryId;
	@SuppressWarnings("rawtypes")
	private Set employees = new HashSet(0);

	public States() {
	}

	public States(String name, int countryId) {
		this.name = name;
		this.countryId = countryId;
	}

	@SuppressWarnings("rawtypes")
	public States(String name, int countryId,  Set employees) {
		this.name = name;
		this.countryId = countryId;
		this.employees = employees;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCountryId() {
		return this.countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	@SuppressWarnings("rawtypes")
	public Set getEmployees() {
		return this.employees;
	}

	@SuppressWarnings("rawtypes")
	public void setEmployees(Set employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "States [id=" + id + ", name=" + name + ", countryId=" + countryId
				+ ", employees=" + employees + "]";
	}

}
