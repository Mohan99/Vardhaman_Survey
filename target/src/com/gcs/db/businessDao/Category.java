package com.gcs.db.businessDao;
// Generated Nov 7, 2017 5:49:38 PM by Hibernate Tools 5.2.3.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Category generated by hbm2java
 */
@SuppressWarnings("serial")
public class Category implements java.io.Serializable {

	@Id
	@GeneratedValue
	private int categoryId;
	private String categoryName;
	private boolean status;
	@SuppressWarnings("rawtypes")
	private Set employees = new HashSet(0);

	public Category() {
	}

	public Category( String categoryName, boolean status) {
		
		this.categoryName = categoryName;
		this.status = status;
	}

	@SuppressWarnings("rawtypes")
	public Category(String categoryName, boolean status, Set employees) {
		this.categoryName = categoryName;
		this.status = status;
		this.employees = employees;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName
				+ ", status=" + status + ", employees=" + employees + "]";
	}

}
