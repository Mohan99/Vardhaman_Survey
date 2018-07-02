package com.gcs.requestDao;

import java.util.Date;

import com.gcs.db.businessDao.Campaign;
import com.gcs.db.businessDao.CampaignQuestions;
import com.gcs.db.businessDao.Vendor;

public class CampaignAnswersRequest {

	private int id;
	private Campaign campaign;
	private CampaignQuestions campaignQuestions;
	private Vendor vendor;
	private String answer;
	private Date createdDate;
	private String remarks;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Campaign getCampaign() {
		return campaign;
	}
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	public CampaignQuestions getCampaignQuestions() {
		return campaignQuestions;
	}
	public void setCampaignQuestions(CampaignQuestions campaignQuestions) {
		this.campaignQuestions = campaignQuestions;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Date getCreatedDate() {
		return createdDate;
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
