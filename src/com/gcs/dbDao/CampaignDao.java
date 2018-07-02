package com.gcs.dbDao;

import java.util.List;

import com.gcs.db.businessDao.Campaign;
import com.gcs.db.businessDao.CampaignAnswers;
import com.gcs.db.businessDao.CampaignQuestions;
public interface CampaignDao {

	public void saveOrUpdate(Campaign campaign);
	public void saveOrUpdateQuestions(CampaignQuestions questions);
	public void saveOrUpdateAnswers(CampaignAnswers answers);
	public List<Campaign> getCampaignsList();
	public List<CampaignQuestions> getCampaignQuestionsList(int id);
	public List<CampaignQuestions> getRemainingCampaignQuestionsList(int id);
	
	public CampaignQuestions getCampaignQuestionsData(int qnsId,int campId);
	public CampaignQuestions getCampaignQuestionsDataBYQnsId(int qnsId);
	public List<CampaignAnswers> getCampaignAnswersList(int id);
	
	public void setPrefix(int id,String prefix);
	long campaignCount();
}
