package com.gcs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.gcs.db.businessDao.User;
import com.gcs.db.businessDao.Vendor;
import com.gcs.db.businessDao.VendorType;
import com.gcs.dbDao.LoginDao;
import com.gcs.dbDao.VendorDao;
import com.gcs.dbDao.VendorTypeDao;
import com.gcs.requestDao.VendorRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Vendor")
public class VendorController extends BaseController {

	@Autowired
	private SessionData sessionobj;
	
	@Autowired
	@Qualifier("vendorDao")
	private VendorDao vendorDao;
	
	@Autowired
	@Qualifier("vendorTypeDao")
	private VendorTypeDao vendorTypeDao;
	
	@Autowired
	@Qualifier("loginDao")
	private LoginDao loginDao;
	
	@RequestMapping(value = "/createVendor", method = RequestMethod.GET)
	public ModelAndView createVendor(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			VendorRequest vendorRequest = new VendorRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			List<VendorType> vendorTypeList=vendorTypeDao.getVendorTypeList();
			model.put("vendorTypeList", vendorTypeList);
			model.put("vendorRequest", vendorRequest);

			modelObj = new ModelAndView("createVendors", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "createOrupdateVendor", method = RequestMethod.GET)
	public String createOrupdateVendor(@ModelAttribute VendorRequest vendorRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Vendor vendor = new Vendor();
			System.out.println("VendorType="+vendorRequest.getVendorType());
			vendor.setName(vendorRequest.getName());
			vendor.setVendorType(vendorRequest.getVendorType());
			vendor.setEmailId(vendorRequest.getEmailId());
			vendorDao.saveOrUpdate(vendor);
			
			Random rnd = new Random();
			int n = 100000 + rnd.nextInt(900000);
			
			User user=new User();
			user.setName(vendorRequest.getName());
			user.setUsername(vendorRequest.getName());
			user.setPassword(String.valueOf(n));
			user.setEmailid(vendorRequest.getEmailId());
			loginDao.saveOrupdateUser(user);
			
			return "redirect:/Vendor/searchVendor";
		} else
			return "redirect:/login";
	}
	
	@RequestMapping(value = "/searchVendor", method = RequestMethod.GET)
	public ModelAndView vendorList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Vendor> vendorList =vendorDao.getVendorsList(); 
			modelObj = new ModelAndView("searchVendors", "vendorList", vendorList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
}
