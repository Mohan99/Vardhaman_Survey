package com.gcs.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.LoginDao;
import com.gcs.dbDao.ResourceDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.ChangePasswordRequest;
import com.gcs.requestDao.LoginRequest;
import com.gcs.requestDao.UsersRequest;
import com.gcs.responseDao.Response;

@Controller
//@RequestMapping("/Login")
public class LoginController extends BaseController {
	@Autowired
	   private SessionData sessionobj;
	
	@Autowired
	@Qualifier("loginDao")
	private LoginDao loginDao;
	@Autowired
	@Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	@Qualifier("resourceDao")
	private ResourceDao resourceDao;
	@Autowired
	//@Qualifier("usersDao")
	private UsersDao usersDao;
	
	public EmployeeDao getEmpDao() {
		return empDao;
	}
	public void setEmpDao(EmployeeDao empDao) {
		this.empDao = empDao;
	}
	/*public SessionData getSessionobj() {
		return sessionobj;
	}
	public void setSessionobj(SessionData sessionobj) {
		this.sessionobj = sessionobj;
	}*/
	@RequestMapping(value="dashboard",method = RequestMethod.GET)
	   public String dashBoard(ModelMap model) {
		if(sessionobj!=null && sessionobj.getIsValidLogin()==true) { 
			
			sessionobj.setEmployeeCount(empDao.empCount());
			sessionobj.setResourceCount(resourceDao.resourceCount());
			sessionobj.setProjectsCount(empDao.projectsCount());
			sessionobj.setUsersCount(usersDao.usersCount());
			System.out.println("Dashboard");
	      return "dashboard";
		}
		else return "redirect:/login";
	   }
	@RequestMapping(value="/",method = RequestMethod.GET)
	   public String loginCall(ModelMap model) {
	     // model.addAttribute("message", "Hello Spring MVC Framework!");

		return "redirect:/login";
	   }
	@RequestMapping(value="login",method = RequestMethod.GET)
	   public ModelAndView login(ModelMap model) {
	    //  model.addAttribute("message", "Hello Spring MVC Framework!");
		LoginRequest loginRequest =new LoginRequest();
		Map<String, Object> mapModel = new HashMap<String, Object>();		
		mapModel.put("LoginRequest", loginRequest);
		ModelAndView loginModel = new ModelAndView("login", mapModel);
		return loginModel;
	   }
	
	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public ModelAndView validateLogin(@ModelAttribute LoginRequest loginRequest,UsersRequest usersRequest,  BindingResult result,
			ModelMap model) {	
		ModelAndView loginModel=null;
		Response resp=null;
		if(loginRequest.getEmail()!=null && loginRequest.getEmail().length()!=0 ){	
			resp= loginDao.validateLogin(loginRequest.getEmail(),loginRequest.getPassword());
			System.out.println("vadidatee...........");
			//resp= loginDao.validateeLogin(loginRequest.getEmail(),loginRequest.getPassword(),loginRequest.isIsActive());
		Map<String, Object> mapModel = new HashMap<String, Object>();	
		if(resp.getStatusCode()=="0") {	
			sessionobj.setValidLogin(true);
			sessionobj.setEmployeeCount(empDao.empCount());
			sessionobj.setResourceCount(resourceDao.resourceCount());
			sessionobj.setProjectsCount(empDao.projectsCount());
			sessionobj.setUsersCount(usersDao.usersCount());
		//	sessionobj.setUserObj(usersDao.getUsersData(usersRequest.getUserName()));
			sessionobj.setUserObj(usersDao.getUserData(loginRequest.getEmail()));
			mapModel.put("Response", resp);
			 loginModel = new ModelAndView("dashboard", mapModel);	
			 
		} else  {	
			resp= getResponse(ConstantVariables.SC1,ConstantVariables.TTRMsgInvalidLogin);
			loginModel = getLogoutView(resp);
		}
		}else {
			 resp= getResponse(ConstantVariables.SC1,ConstantVariables.TTRMsgInvalidLogin);			
			 loginModel = getLogoutView(resp);	
		}
		return loginModel;
			
	}
	
	@RequestMapping(value="changePassword",method = RequestMethod.GET)
	   public ModelAndView changePassword(ModelMap model) {
	    //  model.addAttribute("message", "Hello Spring MVC Framework!");
		ChangePasswordRequest changeRequest =new ChangePasswordRequest();
		Map<String, Object> mapModel = new HashMap<String, Object>();		
		mapModel.put("ChangePasswordRequest", changeRequest);
		ModelAndView loginModel = new ModelAndView("changePassword", mapModel);
		return loginModel;
	   }
	
	@RequestMapping(value = "updatePassword", method = RequestMethod.POST)
	public ModelAndView updatePassword(@ModelAttribute ChangePasswordRequest passwordRequest, BindingResult result,
			ModelMap model) {	
		ModelAndView loginModel=null;
		Response resp=null;
		System.out.println("Not yet started updating");
		//ChangePasswordRequest changeRequest =new ChangePasswordRequest();
		if(sessionobj!=null && sessionobj.getIsValidLogin()) {
		
			resp= loginDao.changePassword(sessionobj.getUserObj().getUserId(), passwordRequest.getOldPassword(),passwordRequest.getNewPassword());
			System.out.println("Updating the password");
		Map<String, Object> mapModel = new HashMap<String, Object>();	
		mapModel.put("Response", resp);
		if(resp.getStatusCode()=="0") {								
			 loginModel = new ModelAndView("success", mapModel);				 		
		}else {
			mapModel.put("ChangePasswordRequest", passwordRequest);
			 loginModel = new ModelAndView("changePassword", mapModel);
		}
		
			
	} else  {	
		resp= getResponse(ConstantVariables.SC1,ConstantVariables.TTRMsgInvalidLogin);
		loginModel = getLogoutView(resp);
	}
		return loginModel;
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	   public String logout(ModelMap model) {	     
		sessionobj.setValidLogin(false);
		return "redirect:/login";
	   }
	
	
	   
	public LoginDao getLoginDao() {
		return loginDao;
	}
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
}
