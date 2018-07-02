package com.gcs.dbDaoImpl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Campaign;
import com.gcs.db.businessDao.CampaignAnswers;
import com.gcs.db.businessDao.CampaignQuestions;
import com.gcs.dbDao.CampaignDao;

@Transactional
@Repository("campaignDao")
public class CampaignDaoImpl extends HibernateDaoSupport implements CampaignDao {

	@Override
	public void saveOrUpdate(Campaign campaign) {
		getHibernateTemplate().saveOrUpdate(campaign);
		getSession().flush();
	}

	@Override
	public List<Campaign> getCampaignsList() {
		List<Campaign> list = getHibernateTemplate().find("from Campaign");
		return list;
	}

	@Override
	public void saveOrUpdateQuestions(CampaignQuestions questions) {
		getHibernateTemplate().saveOrUpdate(questions);
		getSession().flush();

	}

	@Override
	public List<CampaignQuestions> getRemainingCampaignQuestionsList(int id) {
		/*
		 * List<CampaignQuestions> list = getHibernateTemplate()
		 * .find("from CampaignQuestions where campaign=" + id + "order by id");
		 */

		List<CampaignQuestions> list = getHibernateTemplate().find("from CampaignQuestions a where id not in(select campaignQuestions from CampaignAnswers where campaign="
						+ id + ") and a.campaign=" + id + "order by a.id");
		return list;
	}
	
	@Override
	public List<CampaignQuestions> getCampaignQuestionsList(int id) {
		
		  List<CampaignQuestions> list = getHibernateTemplate()
		  .find("from CampaignQuestions where campaign=" + id + "order by id");

		return list;
	}

	@Override
	public CampaignQuestions getCampaignQuestionsData(int qnsId, int campId) {
		List<CampaignQuestions> questions = getHibernateTemplate()
				.find("from CampaignQuestions where id=" + qnsId + " and campaign=" + campId);
		if (questions.size() != 0)
			return questions.get(0);
		else
			return null;
	}

	@Override
	public void setPrefix(int id, String prefix) {
		getHibernateTemplate().bulkUpdate("update CampaignQuestions set prefix='" + prefix + "' where id=" + id);

	}

	@Override
	public void saveOrUpdateAnswers(CampaignAnswers answers) {
		getHibernateTemplate().saveOrUpdate(answers);

	}

	@Override
	public CampaignQuestions getCampaignQuestionsDataBYQnsId(int qnsId) {
		List<CampaignQuestions> questions = getHibernateTemplate().find("from CampaignQuestions where id=" + qnsId);
		if (questions.size() != 0)
			return questions.get(0);
		else
			return null;
	}

	@Override
	public List<CampaignAnswers> getCampaignAnswersList(int campId) {
		List<CampaignAnswers> list = getHibernateTemplate()
				.find("from CampaignAnswers where campaign=" + campId + "order by vendor,id");
		return list;
	}

	@Override
	public long campaignCount() {
		// TODO Auto-generated method stub
		// System.out.println("Count........."+count);
		long count = ((long) getSession().createQuery("select count(*) from Campaign").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}

}
