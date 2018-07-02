package com.gcs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

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

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.State;
import com.gcs.dbDao.ProjectDao;
import com.gcs.requestDao.EmployeeRequest;
import com.gcs.requestDao.ProjectRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Project")
public class ProjectController extends BaseController {
	@Autowired
	@Qualifier("projectDao")
	private ProjectDao projectDao;
	@Autowired
	private SessionData sessionobj;

	@RequestMapping(value = "activeProject", method = RequestMethod.GET)
	public ModelAndView activeProjectList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Projects> projectList = projectDao.getActiveProjects();
			modelObj = new ModelAndView("activeProject", "projectList", projectList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	@RequestMapping(value = "closedProject", method = RequestMethod.GET)
	public ModelAndView closedProjectList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Projects> projectList = projectDao.getClosedProjects();
			modelObj = new ModelAndView("closedProjects", "projectList", projectList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "createProject", method = RequestMethod.GET)
	public ModelAndView createProject(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			ProjectRequest projectReq = new ProjectRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("projectRequest", projectReq);

			modelObj = new ModelAndView("createProject", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	
	public String getDateFormat(Date unformattedDate) {
		Date dt = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(unformattedDate);

		System.out.println(strDate);

		return strDate;
	}

	@RequestMapping(value = "/editProject/{projectId}", method = RequestMethod.GET)
	public ModelAndView editProject(@PathVariable String projectId,ModelMap model) {
		System.out.println("edit project..........."+projectId);
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Projects> projectList = projectDao.getProjects();
			Projects project = projectDao.getProjectData(projectId);
			
			if(project != null){
				
					if (project.getStartDate() != null)
						project.setStartDateStr(getDateFormat(project.getStartDate()));
					if (project.getEndDate() != null){
						project.setEndDateStr(getDateFormat(project.getEndDate()));
						
						//Projects projects = projectDao.getProjectEndDate(id, project.getEndDate());
					}
			
			ProjectRequest projectReq = new ProjectRequest();
			
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("projectRequest", projectReq);
			model.put("project", project);
			model.put("projectList", projectList);
			//model.put("projects", projects);
			modelObj = new ModelAndView("editProject", mapModel);
		} 
		}
		else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}
	
	
	
	@RequestMapping(value = "createOrupdateProject", method = RequestMethod.POST)
	public String createOrUpdateEmployee(@ModelAttribute ProjectRequest projectRequest, BindingResult result,
			ModelMap model) {
		System.out.println("ProjectDetails........."+projectRequest);
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Projects project = new Projects();
			if(projectRequest.getProjectId()!= null){
				System.out.println(project.getProjectId());
				project.setProjectId(projectRequest.getProjectId());
			}
			project.setProjectName(projectRequest.getProjectName());
			Resourceallocations re= new Resourceallocations();
			/*project.setStartDate(projectRequest.getStartDate());
			project.setEndDate(projectRequest.getEndDate());
			project.setStatus(projectRequest.isStatus());*/
		    if (projectRequest.getStartDate() != null)
				project.setStartDate(projectRequest.getStartDate());
			if (projectRequest.getEndDate() != null) {
				project.setEndDate(projectRequest.getEndDate());
				Boolean isTrue= projectDao.setProjectEndDate(projectRequest.getProjectId(), projectRequest.getEndDate());
				//re.setProjectTo(projectRequest.getEndDate());
				project.setStatus(true);
				
			} else
				project.setStatus(false);
			/*if(project.getEndDate() != null ) {
				re.setProjectTo(projectRequest.getEndDate());
			}*/
			projectDao.saveOrUpdate(project);
			return "redirect:/Project/activeProject";
		} else
			return "redirect:/login";
	}

}
