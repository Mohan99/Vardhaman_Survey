package com.gcs.db.businessDao;
// Generated Mar 17, 2018 12:55:27 PM by Hibernate Tools 5.2.6.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;

/**
 * VendorType generated by hbm2java
 */
public class VendorType implements java.io.Serializable {

	@GeneratedValue
	private int id;
	private String name;

	public VendorType() {
	}

	public VendorType(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "VendorType [id=" + id + ", name=" + name + "]";
	}


}