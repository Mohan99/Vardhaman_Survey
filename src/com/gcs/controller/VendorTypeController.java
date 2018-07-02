package com.gcs.controller;

import java.util.HashMap;
import java.util.List;
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
import com.gcs.db.businessDao.Campaign;
import com.gcs.db.businessDao.VendorType;
import com.gcs.dbDao.VendorTypeDao;
import com.gcs.requestDao.VendorTypeRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("VendorType")
public class VendorTypeController extends BaseController {

	@Autowired
	private SessionData sessionobj;
	
	@Autowired
	@Qualifier("vendorTypeDao")
	private VendorTypeDao vendorTypeDao;
	
	@RequestMapping(value = "/createVendorType", method = RequestMethod.GET)
	public ModelAndView createType(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			VendorTypeRequest vendorTypeRequest = new VendorTypeRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("vendorTypeRequest", vendorTypeRequest);

			modelObj = new ModelAndView("createVendorTypes", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	@RequestMapping(value = "createOrupdateVendorType", method = RequestMethod.GET)
	public String createOrupdateVendorType(@ModelAttribute VendorTypeRequest vendorTypeRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			VendorType  vendorType = new VendorType();
			vendorType.setName(vendorTypeRequest.getName());
			vendorTypeDao.saveOrUpdate(vendorType);
			return "redirect:/VendorType/searchVendorType";
		} else
			return "redirect:/login";
	}
	
	@RequestMapping(value = "/searchVendorType", method = RequestMethod.GET)
	public ModelAndView vendorTypeList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<VendorType> vendorTypeList =vendorTypeDao.getVendorTypeList(); 
			modelObj = new ModelAndView("searchVendorTypes", "vendorTypeList", vendorTypeList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
}
