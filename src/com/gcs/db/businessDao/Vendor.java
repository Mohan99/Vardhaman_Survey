package com.gcs.db.businessDao;
// Generated Mar 17, 2018 12:55:27 PM by Hibernate Tools 5.2.6.Final

import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;

/**
 * Vendor generated by hbm2java
 */
public class Vendor implements java.io.Serializable {

	@GeneratedValue
	private int id;
	private VendorType vendorType;
	private String name;
	private String emailId;

	public Vendor() {
	}

	public Vendor(VendorType vendorType) {
		this.vendorType = vendorType;
	}

	public Vendor(VendorType vendorType, String name, String emailId) {
		this.vendorType = vendorType;
		this.name = name;
		this.emailId = emailId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public VendorType getVendorType() {
		return this.vendorType;
	}

	public void setVendorType(VendorType vendorType) {
		this.vendorType = vendorType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Vendor [id=" + id + ", vendorType=" + vendorType + ", name=" + name + ", emailId=" + emailId + "]";
	}


}