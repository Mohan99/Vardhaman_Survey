package com.gcs.db.businessDao;
// Generated Mar 17, 2018 12:55:27 PM by Hibernate Tools 5.2.6.Final

import java.util.Date;

import javax.persistence.GeneratedValue;

/**
 * CampaignAnswers generated by hbm2java
 */
public class CampaignAnswers implements java.io.Serializable {

	@GeneratedValue
	private int id;
	private Campaign campaign;
	private CampaignQuestions campaignQuestions;
	private Vendor vendor;
	private String answer;
	private Date createdDate;
	private String remarks;

	public CampaignAnswers() {
	}

	public CampaignAnswers(Campaign campaign, CampaignQuestions campaignQuestions, Vendor vendor) {
		this.campaign = campaign;
		this.campaignQuestions = campaignQuestions;
		this.vendor = vendor;
	}

	public CampaignAnswers(Campaign campaign, CampaignQuestions campaignQuestions, Vendor vendor, String answer,
			Date createdDate) {
		this.campaign = campaign;
		this.campaignQuestions = campaignQuestions;
		this.vendor = vendor;
		this.answer = answer;
		this.createdDate = createdDate;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Campaign getCampaign() {
		return this.campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public CampaignQuestions getCampaignQuestions() {
		return this.campaignQuestions;
	}

	public void setCampaignQuestions(CampaignQuestions campaignQuestions) {
		this.campaignQuestions = campaignQuestions;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
