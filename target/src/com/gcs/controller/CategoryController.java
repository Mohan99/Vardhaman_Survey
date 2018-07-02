package com.gcs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Projects;
import com.gcs.dbDao.CategoryDao;
import com.gcs.requestDao.CategoryRequest;
import com.gcs.requestDao.ProjectRequest;
import com.gcs.responseDao.Response;


@Controller
@RequestMapping("Category")
public class CategoryController extends BaseController {
	
	
	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;
	
	@Autowired
	private SessionData sessionobj;

	
	
	@RequestMapping(value = "/createCategory", method = RequestMethod.GET)
	public ModelAndView createProject(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			CategoryRequest categoryReq = new CategoryRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("categoryRequest", categoryReq);

			modelObj = new ModelAndView("createCategory", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	
	@RequestMapping(value = "/searchCategory", method = RequestMethod.GET)
	public ModelAndView employeeList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = categoryDao.getCategory();
			modelObj = new ModelAndView("searchCategory", "categoryList", categoryList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
    @RequestMapping(value = "/editCategory/{categoryId}", method = RequestMethod.GET)
	public ModelAndView editCategory(@PathVariable String categoryId,ModelMap model) {
		System.out.println("edit category..........."+categoryId);
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = categoryDao.getCategory();
			Category category = categoryDao.getCategory(categoryId);
			
			if(category != null){
				
					/*if (project.getStartDate() != null)
						project.setStartDateStr(getDateFormat(project.getStartDate()));
					if (project.getEndDate() != null)
						project.setEndDateStr(getDateFormat(project.getEndDate()));*/
			
			CategoryRequest categoryReq = new CategoryRequest();
			
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("categoryRequest", categoryReq);
			model.put("category", category);
			model.put("categoryList", categoryList);
			
			modelObj = new ModelAndView("editCategory", mapModel);
		} 
		}
		else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	/*@RequestMapping(value = "/editCategory/{categoryId}", method = RequestMethod.GET)
	public String editCategory(@PathVariable String categoryId,ModelMap model) {
		System.out.println("edit category..........."+categoryId);
		//ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (categoryId != null) {
				categoryDao.editCategory(categoryId);
			}
			return "redirect:/Category/searchCategory";
		} else
			return "redirect:/login";
	}*/
	@RequestMapping(value = "createOrupdateCategory", method = RequestMethod.GET)
	public String createOrupdateCategory(@ModelAttribute CategoryRequest categoryRequest, BindingResult result,
			ModelMap model) {
		System.out.println("CategoryDetails........."+categoryRequest);
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Category category = new Category();
			if(categoryRequest.getCategoryId()!= null){
				System.out.println(category.getCategoryId());
				category.setCategoryId(categoryRequest.getCategoryId());
			}
			category.setCategoryName(categoryRequest.getCategoryName());
			
			
			category.setStatus(categoryRequest.isStatus());
			
			categoryDao.saveOrUpdate(category);
			return "redirect:/Category/searchCategory";
		} else
			return "redirect:/login";
	}

}

