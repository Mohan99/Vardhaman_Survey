package com.gcs.bean;

import com.gcs.db.businessDao.Campaign;
import com.gcs.db.businessDao.Vendor;
import com.gcs.db.businessDao.VendorType;

public class SurveyStatusBean {

	private Vendor vendor;
	private VendorType vendorType;
	private Campaign campaign;
	private int totalCount;
	private int attemtedCount;
	private int notAttemptedCount;
	private int inProgressCount;
	private String status;
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public VendorType getVendorType() {
		return vendorType;
	}
	public void setVendorType(VendorType vendorType) {
		this.vendorType = vendorType;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getAttemtedCount() {
		return attemtedCount;
	}
	public void setAttemtedCount(int attemtedCount) {
		this.attemtedCount = attemtedCount;
	}
	public int getNotAttemptedCount() {
		return notAttemptedCount;
	}
	public void setNotAttemptedCount(int notAttemptedCount) {
		this.notAttemptedCount = notAttemptedCount;
	}
	public int getInProgressCount() {
		return inProgressCount;
	}
	public void setInProgressCount(int inProgressCount) {
		this.inProgressCount = inProgressCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
