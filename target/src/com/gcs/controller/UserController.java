package com.gcs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;

import com.gcs.db.businessDao.Users;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.UsersDao;
import com.gcs.requestDao.LoginRequest;
import com.gcs.requestDao.UsersRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("/Users")
public class UserController extends BaseController {
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private EmployeeDao empDao;
	@Autowired
	private SessionData sessionobj;

	@RequestMapping(value = "/createUsers", method = RequestMethod.GET)
	public ModelAndView createUsers(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			UsersRequest usersReq = new UsersRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("UsersRequest", usersReq);

			modelObj = new ModelAndView("createUsers", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ModelAndView validateUser(@ModelAttribute UsersRequest usersRequest, BindingResult result,
			LoginRequest loginRequest, ModelMap model) {
		ModelAndView loginModel = null;
		Response resp = null;
		if (usersRequest.getEmail() != null && usersRequest.getEmail().length() != 0) {
			resp = usersDao.validateUsers(usersRequest.getUserName(), usersRequest.getUserId(),
					usersRequest.getLoginName(), usersRequest.getLoginPassword(), usersRequest.getEmail(),
					usersRequest.isIsActive());
			Map<String, Object> mapModel = new HashMap<String, Object>();
			if (resp.getStatusCode() == "0") {
				sessionobj.setValidLogin(true);
				sessionobj.setEmployeeCount(empDao.empCount());
				/*
				 * sessionobj.setManagerCount(empDao.managersCount());
				 * sessionobj.setEmpObj(empDao.getEmployeeData(loginRequest.getEmail()));
				 */
				// sessionobj.setUserName(usersDao.userName());
				sessionobj.setUserObj(usersDao.getUserData(loginRequest.getEmail()));
				// sessionobj.setUserObj(usersDao.getUserData(loginRequest.getEmail()));
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

	@RequestMapping(value = "/createOrUpdateUsers", method = RequestMethod.GET)
	public String createOrUpdateUsers(@ModelAttribute UsersRequest usersRequest, BindingResult result, ModelMap model) {
		// System.out.println(empRequest.getEmployeeName());
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Users userObj = new Users();
			userObj.setUserId(usersRequest.getUserId());
			userObj.setUserName(usersRequest.getUserName());
			userObj.setLoginName(usersRequest.getLoginName());
			userObj.setLoginPassword(usersRequest.getLoginPassword());
			userObj.setIsActive(usersRequest.isIsActive());
			userObj.setEmail(usersRequest.getEmail());
			// empObj.setPassword(empRequest.getPassword()==null?null:);
			// List<Users> usersList = usersDao.getListUsers();
			// GcsManagerList managerData =
			// empDao.getManagerData(empRequest.getManagerId());
			// userObj.setUserName( ((Users) usersList).getUserName());
			usersDao.saveOrUpdate(userObj);

			return "redirect:/Users/searchUsers";
		} else
			return "redirect:/login";
	}

	/*
	 * return "redirect:/Users/searchUsers";
	 * 
	 * }else
	 * 
	 * return "redirect:/login";
	 */

	@RequestMapping(value = "/searchUsers", method = RequestMethod.GET)
	public ModelAndView usersList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Users> usersList = usersDao.getListUsers();
			modelObj = new ModelAndView("searchUsers", "Users", usersList);
			// modelObj = new ModelAndView("searchEmployee", "GcsEmpManager", empList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/editUsers/{userId}", method = RequestMethod.GET)
	public ModelAndView editUsers(@PathVariable Integer userId, ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Users> usersList = usersDao.getListUsers();
			Users usersObj = usersDao.getUsersData(userId);

			UsersRequest usersReq = new UsersRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			mapModel.put("UsersRequest", usersReq);
			mapModel.put("usersList", usersList);
			mapModel.put("Users", usersObj);

			modelObj = new ModelAndView("editUsers", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/deleteUsers/{userId}", method = RequestMethod.GET)
	public String deleteUsers(@PathVariable Integer userId, ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (userId != null) {
				usersDao.delete(userId, true);
			}
			return "redirect:/Users/searchUsers";
		} else
			return "redirect:/login";
	}
	/*
	 * @RequestMapping(value = "/deleteMultiple/{userId}", method =
	 * RequestMethod.GET) public String deleteMultiple(@PathVariable Integer userId,
	 * HttpServletRequest request, ModelMap model) {
	 * 
	 * try { if (request.getParameterValues("userId") != null) { for (String userId
	 * : request.getParameterValues("userId")) { int id = Integer.parseInt(userId);
	 * System.out.println("Id===" + id); this.usersDao.removeUser(id); } } return
	 * "redirect:/Users/searchUsers"; }catch (Exception e) { model.put("error",
	 * e.getMessage()); model.addAttribute("Users", new Users());
	 * model.addAttribute("listUsers", this.usersDao.getListUsers()); return
	 * "redirect:/login";
	 * 
	 * }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * if(sessionobj!=null && sessionobj.getIsValidLogin()) { if (userId != null) {
	 * usersDao.removeUser(userId); } return "redirect:/Users/searchUsers"; }else
	 * return "redirect:/login"; }
	 */

	/*
	 * //try { if (request.getParameterValues("userId") != null) { //for (String
	 * userId : request.getParameterValues("userId")) { //int id =
	 * Integer.parseInt(userId); System.out.println("Id===" + userId);
	 * this.usersDao.removeUser(userId); model.put("usersList",
	 * usersDao.getListUsers()); } //} return "redirect:/Users/searchUsers"; } catch
	 * (Exception e) {
	 * 
	 * model.put("error", e.getMessage()); model.addAttribute("users", new Users());
	 * //model.addAttribute("usersList", this.usersDao.getListUsers()); //Users
	 * usersObj = usersDao.getUsersData(userId); //model.put("UsersRequest",
	 * usersReq); //model.put("usersList", usersDao.getListUsers());
	 * //model.put("Users", usersObj); //return "redirect:/login";
	 * 
	 * 
	 * }
	 */
	@RequestMapping(value = "/deleteMultiple", method = RequestMethod.GET)
	public String deleteMultipleRecords(HttpServletRequest request, ModelMap model) {
		try {
			if (request.getParameterValues("userId") != null) {
				for (String userId : request.getParameterValues("userId")) {
					int id = Integer.parseInt(userId);
					System.out.println("Id===" + id);
					this.usersDao.removeUser(id);
				}
				return "redirect:/Users/searchUsers";
			}

			else {
				return "redirect:/login";
			}
		} catch (Exception e) {
			System.out.println("Id===");
			model.put("error", e.getMessage());
			model.addAttribute("Users", new Users());
			model.addAttribute("listUsers", this.usersDao.getListUsers());
			return "redirect:/Users/searchUsers";

		}

	}
}
