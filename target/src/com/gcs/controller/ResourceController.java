package com.gcs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.State;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.ProjectDao;
import com.gcs.dbDao.ResourceDao;
import com.gcs.requestDao.ProjectRequest;
import com.gcs.requestDao.ResourceRequest;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("/Resource")
public class ResourceController extends BaseController {
	@Autowired
	@Qualifier("resourceDao")
	private ResourceDao resourceDao;
	@Autowired
	// @Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	@Qualifier("projectDao")
	private ProjectDao projectDao;
	@Autowired
	private SessionData sessionobj;

	List<Employee> empCmpList;
	List<Projects> projectsCmpList;
	List<Category> categoryCmpList;
	List<Cities> cityCmpList;
	List<State> stateCmpList;
	List<Countries> countryCmpList;
	List<Resourceallocations> resourceCmpList;

	@RequestMapping(value = "/searchResource", method = RequestMethod.GET)
	public ModelAndView employeeList(@ModelAttribute ResourceRequest resourceRequest, ModelMap model) {
		ModelAndView modelObj = null;
		List<Resourceallocations> resourceList = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resourceList = resourceDao.getResourcesList();
			System.out.println("searchResource......." + resourceList);
			modelObj = new ModelAndView("searchResource", "resourceList", resourceList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/allocateResource/{empId}", method = RequestMethod.GET)
	public ModelAndView allocateResource(@PathVariable  String empId,String id,Date projectTo, ModelMap model) {
		ModelAndView modelObj = null;
		System.out.println("allocateResource............");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			sessionobj.setEmpId(empId);
			ResourceRequest resourceReq = new ResourceRequest();
			List<Employee> empList = empDao.getEmpList();
			List<Projects> projectList = projectDao.getProjects();
			//List<Projects> projectList = projectDao.getClosedProjects();
			System.out.println("empId......." + empId);
			List<Resourceallocations> resourceList = resourceDao.getResourcesListByEmpId(empId);
			System.out.println("Size......." + resourceList.size());
			Resourceallocations resourceObj = resourceList.get(0);
			System.out.println(".....getProject id..... " +resourceObj.getProjectId() );
			
	//List<Resourceallocations> resList =resourceDao.getProjectEndDate(projectId, projectTo);
		//System.out.println("Size......." + resList.size());
		//Resourceallocations resource =resList.get(0);
		
		//System.out.println(".....getProject id..... " +resource.getProjectId() );
		
			//Resourceallocations resource = resourceDao.getProjectEndDate(id, projectTo);
			//Resourceallocations resource =projectDao.getProjectEndDate( sessionobj.getProjectId(),sessionobj.getDate());
			System.out.println("projectList......." + projectList);
			System.out.println("ResourceByEmployee......." + resourceList);
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("resourceRequest", resourceReq);
			model.put("empList", empList);
			model.put("projectList", projectList);
			model.put("resourceList", resourceList);
			//model.put("resource", resource);
			//model.put("resList", resList);
			//resourceDao.updateRes(projectId, projectTo);
			modelObj = new ModelAndView("allocateResource", mapModel);
			model.put("empName", resourceObj.getEmployeeName());
			model.put("empId", resourceObj.getEmpId());
			model.put("projectId", resourceObj.getProjectId());
			model.put("projectTo", resourceObj.getProjectTo());
			
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

	@RequestMapping(value = "editResource/{projectId}", method = RequestMethod.GET)
	public ModelAndView editEmployee(@PathVariable String projectId,Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Resourceallocations> resourceList = resourceDao.getResourcesListByEmpId(sessionobj.getEmpId());
			//List<Projects> projectList = projectDao.getProjects();
			List<Projects> projectList = projectDao.getProjects();
			Resourceallocations resourceObj = resourceDao.getResourceByProjectId(sessionobj.getEmpId(), projectId);
		
			System.out.println("edit Resource.........");
			if (resourceObj != null) {
				if (resourceObj.getProjectFrom() != null)
					resourceObj.setProjectFromStr(getDateFormat(resourceObj.getProjectFrom()));
				if (resourceObj.getProjectTo() != null) {
					resourceObj.setProjectToStr(getDateFormat(resourceObj.getProjectTo()));
					//System.out.println("project.getEndDate()....."+project.getEndDate());
				}
			
				ResourceRequest resourceReq = new ResourceRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();

				mapModel.put("projectList", projectList);
				mapModel.put("resourceRequest", resourceReq);
				mapModel.put("resourceList", resourceList);
				mapModel.put("resourceObj", resourceObj);
				//mapModel.put("resource", resource);
				modelObj = new ModelAndView("allocateResource", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/createOrUpdateResource", method = RequestMethod.POST)
	public String createOrUpdateResource(@ModelAttribute ResourceRequest resourceRequest, BindingResult result,
			ModelMap model) {
		
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Resourceallocations resourceObj = new Resourceallocations();
			System.out.println("createOrUpdateResource Details..................");
			resourceObj.setResId(resourceRequest.getResId());
			if (sessionobj.getEmpId() != null)
				resourceObj.setEmployeeId(Integer.parseInt(sessionobj.getEmpId()));
			else
				resourceObj.setEmployeeId(resourceRequest.getEmployeeId());
			resourceObj.setProjectId(resourceRequest.getProjectId());
			resourceObj.setAllocation(resourceRequest.getAllocation());
			
			Projects project=new Projects();
			System.out.println("Projects Details.................."+project);
			if (resourceRequest.getProjectFrom() != null)
				resourceObj.setProjectFrom(resourceRequest.getProjectFrom());
			if (resourceRequest.getProjectTo() != null) {
				resourceObj.setProjectTo(resourceRequest.getProjectTo());
				
				resourceObj.setProjectCompleted("Y");
			} 
			//resourceObj.setProjectCompleted("N");
			else
			{
				resourceObj.setProjectTo(project.getEndDate());
				System.out.println("project.getEndDate()...."+project.getEndDate());
				resourceObj.setProjectCompleted("N");
			}
			//resourceDao.updateRes(projectId, projectTo);
			resourceDao.saveOrUpdate(resourceObj);
			System.out.println("Resource saved or Updated");
			return "redirect:/Resource/allocateResource/" + sessionobj.getEmpId();
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createNewResource", method = RequestMethod.POST)
	public String createNewResource(@ModelAttribute ResourceRequest resourceRequest, BindingResult result,
			ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Resourceallocations resource = new Resourceallocations();
			resource.setResId(resourceRequest.getResId());

			resource.setEmployeeId(resourceRequest.getEmployeeId());
			resource.setProjectId(resourceRequest.getProjectId());
			if (resourceRequest.getProjectFrom() != null)
				resource.setProjectFrom(resourceRequest.getProjectFrom());

			System.out.println(resourceRequest.getProjectFrom());
			if (resourceRequest.getProjectTo() != null) {
				resource.setProjectTo(resourceRequest.getProjectTo());
				resource.setProjectCompleted("Y");
			} else
				resource.setProjectCompleted("N");
			resourceDao.saveOrUpdate(resource);

			System.out.println("Resource saved or Updated");
			return "redirect:/Resource/searchResource";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/deleteResource/{resId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String resId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (resId != null) {
				resourceDao.delete(resId, true);
			}
			return "redirect:/Resource/allocateResource/" + sessionobj.getEmpId();
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "/allocateNewResource", method = RequestMethod.GET)
	public ModelAndView allocateNewResource(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			ResourceRequest resourceReq = new ResourceRequest();
			List<Employee> empList = empDao.getEmpList();
			List<Projects> projectList = projectDao.getProjects();
			// System.out.println("ResourceByEmployee......." + resourceList);
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("resourceRequest", resourceReq);
			model.put("empList", empList);
			model.put("projectList", projectList);

			modelObj = new ModelAndView("allocateNewResource", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "resourceBulkUpload", method = RequestMethod.GET)
	public String resourceBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "resourceBulkUpload";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "resourceAction", method = RequestMethod.GET)
	public String resourceActionBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "resourceActionUpload";
		} else
			return "redirect:/login";
	}

	public int getEmployeeId(List<Employee> emp, String empid) {
		int employeeId = 0;
		System.out.println("emp>>>>" + emp);
		for (Employee employee : emp) {
			if (empid.contains(employee.getEmployeeId())) {
				System.out.println(">>>>>>>>" + employee.getEmpId());
				employeeId = employee.getEmpId();
			}
		}
		return employeeId;

	}

	public int getProjectId(List<Projects> projects, String projectName) {
		int projectId = 0;
		System.out.println("projects>>>>" + projects);
		for (Projects proj : projects) {
			if (proj.getProjectName().equals(projectName))
				projectId = proj.getProjectId();
		}
		return projectId;
	}

	public Resourceallocations getResource(List<Resourceallocations> resources, int empId, int projId) {
		Resourceallocations allocation = null;
		System.out.println("empId" + empId + ">>>>>>projId" + projId);
		for (Resourceallocations res : resources) {
			if (res.getEmployeeId() == empId && res.getProjectId() == projId) {
				int resId = res.getResId();
				System.out.println("resId...." + resId);
				allocation = new Resourceallocations();
				allocation.setResId(res.getResId());
				allocation.setEmployeeId(res.getEmployeeId());
				allocation.setProjectId(res.getProjectId());
				allocation.setProjectFrom(res.getProjectFrom());
				allocation.setProjectTo(res.getProjectTo());
				allocation.setAllocation(res.getAllocation());
				allocation.setProjectCompleted(res.getProjectCompleted());
			}
		}
		return allocation;
	}

	public Category getCategory(List<Category> catgList, String categoryName) {
		Category ctg = null;
		for (Category cat : catgList) {
			if (cat.getCategoryName().equalsIgnoreCase(categoryName)) {
				ctg = new Category();
				ctg.setCategoryId(cat.getCategoryId());
				ctg.setCategoryName(cat.getCategoryName());
			}
		}
		return ctg;
	}

	public Cities getcity(List<Cities> cityList, String cityName) {
		Cities city = null;
		System.out.println("in getcity......");
		for (Cities ct : cityList) {
			System.out.println("in getcity In For");
			if (ct.getName().equalsIgnoreCase(cityName)) {
				System.out.println("ct>>>>>>" + ct);
				city = new Cities();
				city.setId(ct.getId());
				city.setName(ct.getName());
				city.setStateId(ct.getStateId());
				city.setCountryId(ct.getCountryId());
				break;
			}
		}
		return city;
	}

	public State getState(List<State> stateList, int cityStId) {
		State state = null;
		for (State st : stateList) {
			if (st.getId() == cityStId) {
				state = new State();
				state.setId(st.getId());
				state.setName(st.getName());
				state.setCountryId(st.getCountryId());
			}
		}
		return state;
	}

	public Countries getCountry(List<Countries> cntList, int stCntId) {
		Countries country = null;
		for (Countries cnt : cntList) {
			if (cnt.getId() == stCntId) {
				country = new Countries();
				country.setId(cnt.getId());
				country.setName(cnt.getName());
				country.setPhonecode(cnt.getPhonecode());
				country.setSortname(cnt.getSortname());
			}
		}
		return country;
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String fileUpload(@RequestParam CommonsMultipartFile file, HttpSession session) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {

				ResourceController controller = new ResourceController();
				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				String fileName = file.getOriginalFilename();

				FileInputStream fileStream = new FileInputStream(file1);

				// Create Workbook instance holding reference to .xlsx file
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

				// Get first/desired sheet from the workbook
				XSSFSheet ws = workbook.getSheetAt(0);

				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int id = -1, name = -1, designation = -1, serviceCategory = -1, primaryAssignment = -1,
						otherAssignments = -1, specialization = -1, mobileNo = -1, location = -1, date = -1;

				// Read the headers first. Locate the ones you need
				XSSFRow rowHeader = ws.getRow(0);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("ID".equalsIgnoreCase(cellValue)) {
						id = j;
					} else if ("Name".equalsIgnoreCase(cellValue)) {
						name = j;
					} else if ("Designation".equalsIgnoreCase(cellValue)) {
						designation = j;
					} else if ("Service_Category".equalsIgnoreCase(cellValue)
							|| "Service Category".equalsIgnoreCase(cellValue)) {
						serviceCategory = j;
					} else if ("Primary Assignment".equalsIgnoreCase(cellValue)
							|| "Primary_Assignment".equalsIgnoreCase(cellValue)) {
						primaryAssignment = j;
					} else if ("Other Assignments".equalsIgnoreCase(cellValue)
							|| "Other_Assignments".equalsIgnoreCase(cellValue)) {
						otherAssignments = j;
					} else if ("Specialization".equalsIgnoreCase(cellValue)) {
						specialization = j;
					} else if ("Mobile No".equalsIgnoreCase(cellValue) || "Mobile_No".equalsIgnoreCase(cellValue)) {
						mobileNo = j;

					} else if ("Location".equalsIgnoreCase(cellValue)) {
						location = j;
					} else if ("Date".equalsIgnoreCase(cellValue)) {
						date = j;
					}

				}

				if (id == -1 || name == -1 || designation == -1 || serviceCategory == -1 || primaryAssignment == -1
						|| otherAssignments == -1 || specialization == -1 || mobileNo == -1 || location == -1
						|| date == -1) {
					try {
						throw new Exception(
								"Could not find header indexes\nemployeeId : " + id + " | EmployeeName : " + name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				List<Employee> empList = new ArrayList<Employee>();
				List<Resourceallocations> resourceList = new ArrayList<>();
				List<Projects> projectsList = new ArrayList<>();

				Employee employee = null;
				Resourceallocations resource = null;
				Projects project = null;
				for (int i = 1; i < rowNum; i++) {
					XSSFRow row = ws.getRow(i);
					resource = new Resourceallocations();

					if (empCmpList == null) {
						empCmpList = empDao.getEmpList();
					}
					// System.out.println("empCmpList>>>>" + empCmpList);
					DataFormatter dataFormatter = new DataFormatter();
					String emplId = dataFormatter.formatCellValue(row.getCell(id));
					// System.out.println("emplId...." + emplId);
					String categoryName = cellToString(row.getCell(serviceCategory));
					// System.out.println("categoryName...." + categoryName);
					String cityName = cellToString(row.getCell(location));

					Category category = null;
					Cities city = null;
					State state = null;
					Countries country = null;
					boolean empFlag = false;
					boolean prjectFlag = false;
					boolean secPorjFlag = false;
					for (Employee emp : empCmpList) {
						if (emp.getEmployeeId().equals(emplId)) {
							empFlag = true;
							break;
						}
					}
					if (empFlag == false) {
						employee = new Employee();
						System.out.println("inserting Emloyeeeee");
						employee.setEmployeeId(emplId);
						employee.setEmployeeName(cellToString(row.getCell(name)));
						employee.setEmployeeDesg(cellToString(row.getCell(designation)));
						employee.setEmployeeSpecialization(cellToString(row.getCell(specialization)));
						if (categoryCmpList == null) {
							categoryCmpList = empDao.getCategoryList();
						}
						category = controller.getCategory(categoryCmpList, categoryName);
						employee.setEmployeeCategory(category);

						/*
						 * if (cityCmpList == null) { cityCmpList = empDao.getCities(); }
						 */
						// System.out.println("cityCmpList........."+cityCmpList);
						// city = controller.getcity(cityCmpList, cityName);
						city = empDao.getCityByName(cityName);

						employee.setEmployeeCity(city);

						if (stateCmpList == null) {
							stateCmpList = empDao.getStates();
						}

						state = controller.getState(stateCmpList, city.getStateId());
						employee.setEmployeeState(state);

						if (countryCmpList == null) {
							countryCmpList = empDao.getCountryList();
						}
						country = controller.getCountry(countryCmpList, state.getCountryId());
						employee.setEmployeeCountry(country);

						employee.setEmployeeMobilenumber(cellToString(row.getCell(mobileNo)));
						employee.setStatus(true);
						empDao.saveOrUpdate(employee);
						empCmpList = empDao.getEmpList();
					}

					int empId = controller.getEmployeeId(empCmpList, emplId);
					// System.out.println("empId...." + empId);
					resource.setEmployeeId(empId);
					Date date1 = null;
					if (projectsCmpList == null) {
						projectsCmpList = projectDao.getProjects();
					}
					String projName = cellToString(row.getCell(primaryAssignment));
					// System.out.println("projName..." + projName);
					for (Projects proj : projectsCmpList) {
						if (proj.getProjectName().equals(projName)) {
							prjectFlag = true;
							resource.setProjectFrom(proj.getStartDate());
							break;
						}
					}
					String projDate = dataFormatter.formatCellValue(row.getCell(date));

					if (prjectFlag == false) {
						project = new Projects();
						project.setProjectName(projName);
						try {

							date1 = new SimpleDateFormat("dd/MM/yyyy").parse(projDate);

							project.setStartDate(date1);
							resource.setProjectFrom(date1);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						project.setStatus(false);
						projectDao.saveOrUpdate(project);
						projectsCmpList = projectDao.getProjects();
					}

					int projectId = controller.getProjectId(projectsCmpList, projName);
					System.out.println(
							"projectId before inserting primary projecct into resource allocatioon table " + projectId);
					resource.setProjectId(projectId);

					resource.setProjectCompleted("N");
					resource.setAllocation("p");
					if (prjectFlag == true) {

					}
					System.out.println("Inserting Resource with PrimaryProject..." + projName);
					resourceDao.insert(resource);

					String otherProjects = cellToString(row.getCell(otherAssignments));

					if (otherProjects != null) {
						String secProjName[] = otherProjects.split("/");
						for (int j = 0; j < secProjName.length; j++) {
							resource = new Resourceallocations();
							resource.setEmployeeId(empId);

							System.out.println("secProjName....." + secProjName[j]);
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equals(secProjName[j])) {
									secPorjFlag = true;

									break;
								}
							}
							if (secPorjFlag == false) {
								project = new Projects();
								System.out.println("inserting SecondaryProject");
								project.setProjectName(secProjName[j]);
								project.setStartDate(new Date());
								project.setStatus(false);
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
							}
							int scndProjectId = controller.getProjectId(projectsCmpList, secProjName[j]);
							resource.setProjectId(scndProjectId);
							resource.setProjectFrom(new Date());
							resource.setProjectCompleted("N");
							resource.setAllocation("s");
							resourceDao.insert(resource);
							System.out.println("Inserting Resource with SecondaryProject..." + secProjName[j]);
						}
					}

					/*
					 * resourceList.add(resource); empList.add(employee);
					 */
				}

				/*
				 * if (resourceList.size() > 0) { for (Resourceallocations res : resourceList) {
				 * System.out.println("Inserting Resource"); // resourceDao.insert(res); } }
				 */
				fileStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return "redirect:/Resource/searchResource";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "addOrRemoveResource", method = RequestMethod.POST)
	public String addOrRemoveResource(@RequestParam CommonsMultipartFile file, HttpSession session)
			throws ParseException {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {

				ResourceController controller = new ResourceController();
				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				String fileName = file.getOriginalFilename();

				FileInputStream fileStream = new FileInputStream(file1);

				// Create Workbook instance holding reference to .xlsx file
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

				// Get first/desired sheet from the workbook
				XSSFSheet ws = workbook.getSheetAt(0);

				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int empId = -1, primaryProject = -1, secondaryProject = -1, startDate = -1, endDate = -1, action = -1;

				// Read the headers first. Locate the ones you need
				XSSFRow rowHeader = ws.getRow(0);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("Employee Id".equalsIgnoreCase(cellValue) || "Employee_Id".equalsIgnoreCase(cellValue)) {
						empId = j;

					} else if ("Primary Project".equalsIgnoreCase(cellValue)
							|| "Primary_Project".equalsIgnoreCase(cellValue)) {
						primaryProject = j;
					} else if ("Secondary project".equalsIgnoreCase(cellValue)
							|| "Secondary_project".equalsIgnoreCase(cellValue)) {
						secondaryProject = j;
					} else if ("Start Date".equalsIgnoreCase(cellValue) || "Start_Date".equalsIgnoreCase(cellValue)) {
						startDate = j;
					} else if ("End Date".equalsIgnoreCase(cellValue) || "End_Date".equalsIgnoreCase(cellValue)) {
						endDate = j;

					} else if ("Action".equalsIgnoreCase(cellValue)) {
						action = j;
					}
				}

				if (empId == -1 || primaryProject == -1 || secondaryProject == -1 || startDate == -1 || endDate == -1
						|| action == -1) {
					try {
						throw new Exception("Could not find header indexes\nemployeeId : " + empId);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Resourceallocations resource = null;
				Projects project = null;
				for (int i = 1; i < rowNum; i++) {
					XSSFRow row = ws.getRow(i);
					resource = new Resourceallocations();

					if (empCmpList == null) {
						empCmpList = empDao.getEmpList();
					}
					DataFormatter df = new DataFormatter();
					String employeeId = df.formatCellValue(row.getCell(empId));

					int id = controller.getEmployeeId(empCmpList, employeeId);
					System.out.println("id...." + id);
					if (id != 0) {

						DataFormatter dataFormatter = new DataFormatter();
						String projStartDate = dataFormatter.formatCellValue(row.getCell(startDate));
						String projEndDate = dataFormatter.formatCellValue(row.getCell(endDate));
						System.out.println("projStartDate..." + projStartDate + ".....projEndDate...." + projEndDate);
						Date dateStart = null;
						Date dateEnd = null;
						if (projStartDate != "") {
							dateStart = new SimpleDateFormat("dd/MM/yyyy").parse(projStartDate);
						} else
							dateStart = new Date();

						if (projEndDate != "") {
							dateEnd = new SimpleDateFormat("dd/MM/yyyy").parse(projEndDate);
						} else
							dateEnd = new Date();

						if (projectsCmpList == null) {
							projectsCmpList = projectDao.getProjects();
						}

						int projectId = 0;
						String primaryProjName = cellToString(row.getCell(primaryProject));
						if (primaryProjName != null) {
							boolean prjectFlag = false;
							boolean secPorjFlag = false;
							System.out.println("primaryProjName..." + primaryProjName);
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equalsIgnoreCase(primaryProjName)) {
									prjectFlag = true;
									break;
								}
							}

							if (prjectFlag == false) {
								System.out.println("Inserting primary project");
								project = new Projects();
								project.setProjectName(primaryProjName);
								project.setStartDate(dateStart);
								project.setStatus(false);
								/*
								 * if (dateEnd != null) { project.setEndDate(dateEnd); project.setStatus(true);
								 * }
								 */
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
							}
							resource.setAllocation("p");
							projectId = controller.getProjectId(projectsCmpList, primaryProjName);

						}

						String secondaryProjName = cellToString(row.getCell(secondaryProject));
						if (secondaryProjName != null) {
							boolean secPorjFlag = false;
							System.out.println("secondaryProjName..." + secondaryProjName);
							for (Projects proj : projectsCmpList) {
								if (proj.getProjectName().equalsIgnoreCase(secondaryProjName)) {
									secPorjFlag = true;
									break;
								}
							}

							if (secPorjFlag == false) {
								System.out.println("Inserting Secondary project");
								project = new Projects();
								project.setProjectName(secondaryProjName);
								project.setStartDate(dateStart);
								project.setStatus(false);
								/*
								 * if (dateEnd != null) { project.setEndDate(dateEnd); project.setStatus(true);
								 * }
								 */
								projectDao.saveOrUpdate(project);
								projectsCmpList = projectDao.getProjects();
							}
							resource.setAllocation("s");
							projectId = controller.getProjectId(projectsCmpList, secondaryProjName);

						}

						String projAction = cellToString(row.getCell(action));
						if (resourceCmpList == null) {
							resourceCmpList = resourceDao.getResources();
						}
						Resourceallocations res = controller.getResource(resourceCmpList, id, projectId);
						System.out.println("res...." + res);
						boolean dateFlag = false;
						if (projAction.equalsIgnoreCase("add")) {
							if (res != null) {
								resource.setResId(res.getResId());
								resource.setEmployeeId(id);
								resource.setProjectId(projectId);
								resource.setProjectFrom(res.getProjectFrom());
								resource.setProjectTo(res.getProjectFrom());
								resource.setAllocation(res.getAllocation());
								resource.setProjectCompleted("Y");
								System.out.println("formDateDay..=" + res.getProjectFrom().getDate() + "..formDateMonth"
										+ res.getProjectFrom().getMonth() + "....dateStartDay=" + dateStart.getDate()
										+ "....dateStartMonth=" + dateStart.getMonth());
								if (res.getProjectFrom().getDate() != dateStart.getDate()
										&& res.getProjectFrom().getMonth() != dateStart.getMonth()
										&& res.getProjectFrom().getYear() != dateStart.getYear()) {
									resourceDao.saveOrUpdate(resource);
									dateFlag = true;
								}
								resourceCmpList = resourceDao.getResources();
							}
							if (dateFlag == true) {
								Resourceallocations resource1 = new Resourceallocations();
								resource1.setEmployeeId(id);
								resource1.setProjectId(projectId);
								resource1.setProjectFrom(dateStart);
								if (primaryProjName != null)
									resource1.setAllocation("p");
								else if (secondaryProjName != null)
									resource1.setAllocation("s");
								resource1.setProjectCompleted("N");
								/*
								 * if (dateEnd != null) { resource.setProjectTo(dateEnd);
								 * resource.setProjectCompleted("Y"); }
								 */
								System.out.println("Inserting Resource with PrimaryProject In Add..." + projectId);
								resourceDao.insert(resource1);
								resourceCmpList = resourceDao.getResources();
							}
						} else if (projAction.equalsIgnoreCase("remove")) {
							if (res != null && projStartDate == "") {
								resourceDao.updateResource(res.getResId(), dateEnd);
								resourceCmpList = resourceDao.getResources();
							} /*
								 * else if (res == null && projStartDate == "") { Resourceallocations resource2
								 * = new Resourceallocations(); resource2.setEmployeeId(id);
								 * resource2.setProjectId(projectId); resource2.setProjectFrom(dateEnd);
								 * resource2.setProjectTo(dateEnd); resource2.setProjectCompleted("Y"); if
								 * (primaryProjName != null) resource2.setAllocation("p"); else if
								 * (secondaryProjName != null) resource2.setAllocation("s");
								 * System.out.println("Inserting Resource with PrimaryProject In Remove..." +
								 * projectId); resourceDao.insert(resource2); }
								 */
						}

					}
				}

				fileStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return "redirect:/Resource/searchResource";
		} else
			return "redirect:/login";
	}

	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;
		if (cell == null) {
			return null;
		} else {
			type = cell.getCellType();
			switch (type) {

			case XSSFCell.CELL_TYPE_NUMERIC:
				result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();

				break;
			case XSSFCell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				result = "";
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
			}
			return result.toString();
		}
	}

}
