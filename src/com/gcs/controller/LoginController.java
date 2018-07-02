package com.gcs.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.CampaignQuestions;
import com.gcs.db.businessDao.Vendor;
import com.gcs.dbDao.CampaignDao;
import com.gcs.dbDao.LoginDao;
import com.gcs.dbDao.VendorDao;
import com.gcs.dbDao.VendorTypeDao;
import com.gcs.requestDao.ChangePasswordRequest;
import com.gcs.requestDao.LoginPublicUserRequest;
import com.gcs.requestDao.LoginRequest;
import com.gcs.responseDao.Response;

@Controller
// @RequestMapping("/Login")
public class LoginController extends BaseController {
	@Autowired
	private SessionData sessionobj;

	@Autowired
	@Qualifier("loginDao")
	private LoginDao loginDao;

	@Autowired
	@Qualifier("campaignDao")
	private CampaignDao campaignDao;

	@Autowired
	@Qualifier("vendorDao")
	private VendorDao vendorDao;
	
	@Autowired
	@Qualifier("vendorTypeDao")
	private VendorTypeDao vendorTypeDao;

	@RequestMapping(value = "dashboard", method = RequestMethod.GET)
	public String dashBoard(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin() == true) {
			sessionobj.setVendorCount(vendorDao.vendorCount());
			sessionobj.setVendorTypeCount(vendorTypeDao.vendorTypeCount());
			sessionobj.setCampaignCount(campaignDao.campaignCount());
			//System.out.println("Dashboard");
			return "dashboard";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginCall(ModelMap model) {
		// model.addAttribute("message", "Hello Spring MVC Framework!");

		return "redirect:/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(ModelMap model) {
		// model.addAttribute("message", "Hello Spring MVC Framework!");
		LoginRequest loginRequest = new LoginRequest();
		Map<String, Object> mapModel = new HashMap<String, Object>();
		mapModel.put("LoginRequest", loginRequest);
		ModelAndView loginModel = new ModelAndView("login", mapModel);
		return loginModel;
	}

	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public ModelAndView validateLogin(@ModelAttribute LoginRequest loginRequest, BindingResult result, ModelMap model) {
		ModelAndView loginModel = null;
		Response resp = null, resp1 = null;
		if (loginRequest.getEmail() != null && loginRequest.getEmail().length() != 0) {
			resp = loginDao.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());
			Map<String, Object> mapModel = new HashMap<String, Object>();
			if (resp.getStatusCode() == "0") {
				sessionobj.setValidLogin(true);
				sessionobj.setUserObj(loginDao.getUserData(loginRequest.getEmail()));
				sessionobj.setVendorCount(vendorDao.vendorCount());
				sessionobj.setVendorTypeCount(vendorTypeDao.vendorTypeCount());
				sessionobj.setCampaignCount(campaignDao.campaignCount());
				mapModel.put("Response", resp);
				loginModel = new ModelAndView("dashboard", mapModel);

			} else {
				resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidLogin);
				loginModel = getLogoutView(resp);
			}
		} else {
			resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidLogin);
			loginModel = getLogoutView(resp);
		}
		return loginModel;

	}

	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword(ModelMap model) {
		// model.addAttribute("message", "Hello Spring MVC Framework!");
		ChangePasswordRequest changeRequest = new ChangePasswordRequest();
		Map<String, Object> mapModel = new HashMap<String, Object>();
		mapModel.put("ChangePasswordRequest", changeRequest);
		ModelAndView loginModel = new ModelAndView("changePassword", mapModel);
		return loginModel;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		sessionobj.setValidLogin(false);
		return "redirect:/login";
	}

	@RequestMapping(value = "/publicUser", method = RequestMethod.GET)
	public ModelAndView publicUserLogin(@RequestParam("emailId") String emailId, @RequestParam("campId") String campId,
			ModelMap model) {
		LoginPublicUserRequest loginRequest = new LoginPublicUserRequest();
		loginRequest.setEmail(emailId);
		loginRequest.setCampId(campId);
		model.addAttribute("email", loginRequest.getEmail());
		model.addAttribute("campId", loginRequest.getCampId());

		loginRequest.setEmail(loginRequest.getEmail());
		loginRequest.setCampId(loginRequest.getCampId());
		Map<String, Object> mapModel = new HashMap<String, Object>();
		mapModel.put("LoginRequest", loginRequest);
		ModelAndView publicLoginModel = new ModelAndView("publicUser", mapModel);
		return publicLoginModel;
	}

	@RequestMapping(value = "validatePublicUser", method = RequestMethod.POST)
	public String validatePublicUser(@ModelAttribute LoginPublicUserRequest loginRequest,
			BindingResult result, ModelMap model,HttpServletRequest req) {
		Response resp = null;
		int campId = 0;
		if (loginRequest.getEmail() != null && loginRequest.getEmail().length() != 0) {
			resp = loginDao.validateLogin(loginRequest.getEmail(), loginRequest.getPassword());
			if (resp.getStatusCode() == "0") {
				sessionobj.setValidLogin(true);
				campId = Integer.parseInt(req.getParameter("campId"));
				System.out.println("campId==" + campId);
				Vendor vendor = vendorDao.getVendorData(loginRequest.getEmail());
				sessionobj.setVendor(vendor);

			}
		}
		return "redirect:/Campaign/getSurveyQuestion/"+campId;
	}

}
