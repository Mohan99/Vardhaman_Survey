package com.gcs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gcs.db.businessDao.User;
import com.gcs.db.businessDao.Vendor;

import org.springframework.context.annotation.ScopedProxyMode;

@Component("sessionObj")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	private boolean isValidLogin;
	private String statusCode;
	private String statusMessage;

	private String LoginUserName;
	private String UserName;

	private String emialCallPath;
	
	private User userObj;
	
	private ArrayList<Integer> qsnIds;
	
	private Vendor vendor;
	private int campaignId;

	private long vendorCount;
	private long vendorTypeCount;
	private long campaignCount;
	
	

	public long getVendorCount() {
		return vendorCount;
	}

	public void setVendorCount(long vendorCount) {
		this.vendorCount = vendorCount;
	}

	public long getVendorTypeCount() {
		return vendorTypeCount;
	}

	public void setVendorTypeCount(long vendorTypeCount) {
		this.vendorTypeCount = vendorTypeCount;
	}

	public long getCampaignCount() {
		return campaignCount;
	}

	public void setCampaignCount(long campaignCount) {
		this.campaignCount = campaignCount;
	}

	public int getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public ArrayList<Integer> getQsnIds() {
		return qsnIds;
	}

	public void setQsnIds(ArrayList<Integer> qsnIds) {
		this.qsnIds = qsnIds;
	}

	public User getUserObj() {
		return userObj;
	}

	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public boolean getIsValidLogin() {
		return isValidLogin;
	}

	public void setValidLogin(boolean isValidLogin) {
		this.isValidLogin = isValidLogin;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getLoginUserName() {
		return LoginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		LoginUserName = loginUserName;
	}

	public String getEmialCallPath() {
		return emialCallPath;
	}

	public void setEmialCallPath(String emialCallPath) {
		this.emialCallPath = emialCallPath;
	}

}
