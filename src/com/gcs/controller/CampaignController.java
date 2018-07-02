package com.gcs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.bean.SurveyStatusBean;
import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Campaign;
import com.gcs.db.businessDao.CampaignAnswers;
import com.gcs.db.businessDao.CampaignQuestions;
import com.gcs.db.businessDao.User;
import com.gcs.db.businessDao.Vendor;
import com.gcs.db.businessDao.VendorType;
import com.gcs.dbDao.CampaignDao;
import com.gcs.dbDao.LoginDao;
import com.gcs.dbDao.VendorDao;
import com.gcs.dbDao.VendorTypeDao;
import com.gcs.requestDao.CampaignQuestionsRequest;
import com.gcs.requestDao.CampaignRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Campaign")
public class CampaignController extends BaseController {

	@Autowired
	private SessionData sessionobj;

	@Autowired
	@Qualifier("campaignDao")
	private CampaignDao campaignDao;

	@Autowired
	@Qualifier("vendorTypeDao")
	private VendorTypeDao vendorTypeDao;

	@Autowired
	@Qualifier("loginDao")
	private LoginDao loginDao;

	@Autowired
	@Qualifier("vendorDao")
	private VendorDao vendorDao;

	@RequestMapping(value = "/createCampaign", method = RequestMethod.GET)
	public ModelAndView createCampaign(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignRequest campaignRequest = new CampaignRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			List<VendorType> vendorTypeList = vendorTypeDao.getVendorTypeList();
			model.put("vendorTypeList", vendorTypeList);
			model.put("campaignRequest", campaignRequest);

			modelObj = new ModelAndView("createCampaigns", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "createOrupdateCampaign", method = RequestMethod.GET)
	public String createOrupdateCampaign(@ModelAttribute CampaignRequest campaignRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Campaign campaign = new Campaign();
			campaign.setName(campaignRequest.getName());
			campaign.setVendorType(campaignRequest.getVendorType());
			campaignDao.saveOrUpdate(campaign);
			return "redirect:/Campaign/searchCampaign";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/searchCampaign", method = RequestMethod.GET)
	public ModelAndView campaignList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Campaign> campaignList = campaignDao.getCampaignsList();
			modelObj = new ModelAndView("searchCampaigns", "campaignList", campaignList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/createCampaignQuestion", method = RequestMethod.GET)
	public ModelAndView createCampaignQuestion(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestionsRequest questionRequest = new CampaignQuestionsRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			List<Campaign> campaignList = campaignDao.getCampaignsList();
			model.put("campaignList", campaignList);
			model.put("questionRequest", questionRequest);

			modelObj = new ModelAndView("createCampaignQuestions", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/createQuestion/{id}", method = RequestMethod.GET)
	public ModelAndView createQuestion(@PathVariable int id, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestionsRequest questionRequest = new CampaignQuestionsRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			List<Campaign> campaignList = campaignDao.getCampaignsList();
			model.put("campaignId", id);
			model.put("campaignList", campaignList);
			model.put("questionRequest", questionRequest);

			modelObj = new ModelAndView("createQuestions", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "createOrupdateQuestion", method = RequestMethod.POST)
	public String createOrupdateQuestion(@ModelAttribute CampaignQuestionsRequest campaignQuestionsRequest,
			BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestions questions = new CampaignQuestions();
			int campaignId = campaignQuestionsRequest.getCampaign().getId();
			questions.setCampaign(campaignQuestionsRequest.getCampaign());
			questions.setQuestion(campaignQuestionsRequest.getQuestion());
			questions.setOption1(campaignQuestionsRequest.getOption1());
			questions.setOption2(campaignQuestionsRequest.getOption2());
			questions.setOption3(campaignQuestionsRequest.getOption3());
			questions.setOption4(campaignQuestionsRequest.getOption4());
			questions.setRemarks(campaignQuestionsRequest.isRemarks());
			campaignDao.saveOrUpdateQuestions(questions);
			return "redirect:/Campaign/createQuestion/" + campaignId;
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createOrupdatePrefix", method = RequestMethod.POST)
	public String createOrupdatePrefix(@ModelAttribute CampaignQuestionsRequest campQuestionRequest,
			BindingResult result, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestions questions = new CampaignQuestions();
			int campaignId = sessionobj.getCampaignId();
			int questionId = campQuestionRequest.getId();
			String prefix = campQuestionRequest.getPrefix();
			// System.out.println("Prefix==" + prefix);
			campaignDao.setPrefix(questionId, prefix);
			return "redirect:/Campaign/searchQuestions/" + campaignId;
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/searchCampaignQuestions", method = RequestMethod.GET)
	public ModelAndView searchCampaignQuestions(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestionsRequest questionRequest = new CampaignQuestionsRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			List<Campaign> campaignList = campaignDao.getCampaignsList();
			model.put("campaignList", campaignList);
			model.put("questionRequest", questionRequest);
			modelObj = new ModelAndView("showCampaignQuestions", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/searchQuestions/{id}", method = RequestMethod.GET)
	public ModelAndView searchQuestions(@PathVariable int id, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<CampaignQuestions> qeustionsList = campaignDao.getCampaignQuestionsList(id);
			sessionobj.setCampaignId(id);
			modelObj = new ModelAndView("showQuestions", "qeustionsList", qeustionsList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/setPrefix/{id}", method = RequestMethod.GET)
	public ModelAndView setPrefix(@PathVariable int id, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestions questions = campaignDao.getCampaignQuestionsDataBYQnsId(id);

			CampaignQuestionsRequest campQuestionRequest = new CampaignQuestionsRequest();

			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("campQuestionRequest", campQuestionRequest);
			model.put("questions", questions);
			modelObj = new ModelAndView("setQuestionPrefix", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/getSurveyQuestion/{campId}", method = RequestMethod.GET)
	public ModelAndView getSurveyQuestion(@PathVariable int campId, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestionsRequest questionRequest = new CampaignQuestionsRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			List<CampaignQuestions> camQuestionsList = campaignDao.getRemainingCampaignQuestionsList(campId);
			sessionobj.setCampaignId(campId);
			if (camQuestionsList.size() != 0)
				model.put("camQuestions", camQuestionsList.get(0));
			else
				model.put("camQuestions", null);
			model.put("questionRequest", questionRequest);

			model.put("qsnId", 1);
			modelObj = new ModelAndView("getSurveyQuestions", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/upadateAnswer", method = RequestMethod.GET)
	public String upadateAnswer(@ModelAttribute CampaignQuestionsRequest questionRequest, BindingResult result,
			ModelMap model, HttpServletRequest req) {
		ModelAndView modelObj = null;
		int qnsNo = 0;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {

			ArrayList<Integer> list = new ArrayList<>();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			String ans = req.getParameter("ans");
			String remarks = req.getParameter("remarks");
			ans = questionRequest.getId() + ans;
			qnsNo = Integer.parseInt(req.getParameter("qsnId"));
			// System.out.println("ans==" + ans);
			CampaignAnswers answers = new CampaignAnswers();
			answers.setCampaign(questionRequest.getCampaign());
			CampaignQuestions qns = new CampaignQuestions();
			/*
			 * Vendor vendor = new Vendor(); vendor.setId(1);
			 */
			qns.setId(questionRequest.getId());
			answers.setCampaignQuestions(qns);
			answers.setVendor(sessionobj.getVendor());
			answers.setAnswer(ans);
			answers.setCreatedDate(new Date());
			answers.setRemarks(questionRequest.getRemarksStr());
			campaignDao.saveOrUpdateAnswers(answers);

			int qnsId = 0;
			List<CampaignQuestions> camQuestionsList = campaignDao
					.getCampaignQuestionsList(questionRequest.getCampaign().getId());
			for (CampaignQuestions questions : camQuestionsList) {
				if (questions.getPrefix() != null && questions.getPrefix().equalsIgnoreCase(ans)) {
					qnsId = questions.getId();
				}
			}

			if (qnsId != 0) {
				List checkList = sessionobj.getQsnIds();
				if (checkList == null || checkList.size() == 0) {
					list.add(0, questionRequest.getId());
					sessionobj.setQsnIds(list);
				} else if (checkList != null && checkList.size() != 0) {

					for (int i = 0; i < checkList.size(); i++) {

						if (checkList.get(i) == null) {

							list.add(i, questionRequest.getId());
							sessionobj.setQsnIds(list);
						}
					}
				}

			} else {
				if (sessionobj.getQsnIds() != null && sessionobj.getQsnIds().get(0) != 0) {
					qnsId = sessionobj.getQsnIds().get(0) + 1;
					sessionobj.setQsnIds(null);
				} else {
					// System.out.println("else");
					qnsId = questionRequest.getId() + 1;
					for (CampaignQuestions qns1 : camQuestionsList) {
						if (qns1.getId() == qnsId && qns1.getPrefix() == null) {
						} else if (qns1.getId() == qnsId && qns1.getPrefix() != null) {
							qnsId = qnsId + 1;

						}
					}
				}
			}
			return "redirect:/Campaign/nextQuestions/" + qnsId + "/" + qnsNo;
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/nextQuestions/{qnsId}/{qnsNo}", method = RequestMethod.GET)
	public ModelAndView nextQuestions(@PathVariable int qnsId, @PathVariable int qnsNo, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestionsRequest questionRequest = new CampaignQuestionsRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			int campId = sessionobj.getCampaignId();
			System.out.println("campId=" + campId);
			CampaignQuestions qns = campaignDao.getCampaignQuestionsData(qnsId, campId);
			model.put("camQuestions", qns);
			model.put("questionRequest", questionRequest);
			model.put("qsnId", qnsNo + 1);
			modelObj = new ModelAndView("getSurveyQuestions", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/submitSurvey", method = RequestMethod.GET)
	public ModelAndView submitSurvey(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Map<String, Object> mapModel = new HashMap<String, Object>();
			modelObj = new ModelAndView("surveySubmission", mapModel);
		} else {
			/*
			 * Response resp = getResponse(ConstantVariables.SC1,
			 * ConstantVariables.TTRMsgInvalidsession); modelObj = getLogoutView(resp);
			 */
		}
		return modelObj;
	}

	@RequestMapping(value = "/sendSurveyMail/{campaignId}/{vendorTypeId}", method = RequestMethod.GET)
	public String sendSurveyMail(@PathVariable int campaignId, @PathVariable int vendorTypeId, ModelMap model) {
		StringBuffer mailids = new StringBuffer();
		String emailId = null;
		String userName = null;
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {

			// vendor type id use it and get the vendor email ids
			List<Vendor> vendor = vendorTypeDao.getVendorList(vendorTypeId);

			List<Campaign> campaignList = campaignDao.getCampaignsList();

			StringBuffer sb = null;
			String pwd = null;
			for (Vendor myVendor : vendor) {
				emailId = myVendor.getEmailId();
				System.out.println("emailId==" + emailId);
				sb = new StringBuffer("http://localhost:2525/Vardhaman_Survey/publicUser?emailId=" + emailId
						+ "&campId=" + campaignId);
				User user = loginDao.getUserData(emailId);
				pwd = user.getPassword();
				userName = user.getName();
				SendMail.send(emailId, sb.toString(), pwd, userName);
			}

			// modelObj = new ModelAndView("searchCampaigns", "campaignList", campaignList);
			return "redirect:/Campaign/sentSurvey";
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
			return "redirect:/login";
		}
	}

	@RequestMapping(value = "/sentSurvey", method = RequestMethod.GET)
	public ModelAndView sentSurvey(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Map<String, Object> mapModel = new HashMap<String, Object>();
			modelObj = new ModelAndView("surveySended", mapModel);
		} else {
			/*
			 * Response resp = getResponse(ConstantVariables.SC1,
			 * ConstantVariables.TTRMsgInvalidsession); modelObj = getLogoutView(resp);
			 */
		}
		return modelObj;
	}

	@RequestMapping(value = "/campaignSurveyStatus", method = RequestMethod.GET)
	public ModelAndView campaignSurveyStatus(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CampaignQuestionsRequest questionRequest = new CampaignQuestionsRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			List<Campaign> campaignList = campaignDao.getCampaignsList();
			model.put("campaignList", campaignList);
			model.put("questionRequest", questionRequest);
			modelObj = new ModelAndView("surveyStatus", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/getSurveyStatus/{campaignId}/{vendorTypeId}", method = RequestMethod.GET)
	public ModelAndView getSurveyStatus(@PathVariable int campaignId, @PathVariable int vendorTypeId, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			sessionobj.setCampaignId(campaignId);
			int totalCount = 0;
			int noPrefixCount = 0;
			String status = null;

			List<CampaignQuestions> camQuestionsList = campaignDao.getCampaignQuestionsList(campaignId);
			totalCount = camQuestionsList.size();
			for (CampaignQuestions campaignQuestions : camQuestionsList) {
				if (campaignQuestions.getPrefix() != null)
					noPrefixCount++;
			}

			List<Vendor> vendorList = vendorDao.getVendorsList();
			List<CampaignAnswers> answersList = campaignDao.getCampaignAnswersList(campaignId);

			int i = 0;
			SurveyStatusBean bean = null;
			List<SurveyStatusBean> listBean = new ArrayList<>();
			for (Vendor vendor2 : vendorList) {
				boolean vendFlag = false, vendTypeFlag = false;
				int answCount = 0;
				for (CampaignAnswers campaignAnswers : answersList) {
					if (vendor2.getId() == campaignAnswers.getVendor().getId()) {
						i++;
						answCount++;
						vendFlag = true;
					}
					if (vendor2.getVendorType().getId() == campaignAnswers.getCampaign().getVendorType().getId()) {
						vendTypeFlag = true;
					}
				}
				if (vendFlag && (i < answersList.size() || i == answersList.size())) {
					int tempCount = noPrefixCount + answCount;
					if (totalCount <= tempCount)
						status = "Completed";
					else if (totalCount > tempCount)
						status = "In Progress";
					else
						status = "Not Attempted";

					bean = new SurveyStatusBean();
					bean.setVendor(answersList.get(i - 1).getVendor());
					bean.setTotalCount(totalCount);
					bean.setStatus(status);
					listBean.add(bean);
				} else if (vendTypeFlag) {
					bean = new SurveyStatusBean();
					bean.setVendor(vendor2);
					bean.setTotalCount(totalCount);
					bean.setStatus("Not Attempted");
					listBean.add(bean);
				}

			}

			modelObj = new ModelAndView("showSurveyStatus", "listBean", listBean);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

}
