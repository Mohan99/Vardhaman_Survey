package com.gcs.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;

import com.gcs.db.businessDao.State;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.requestDao.EmployeeRequest;
import com.gcs.requestDao.SendMailRequest;
import com.gcs.responseDao.EmployeeResponseReport;
import com.gcs.responseDao.Response;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("Employee")
public class EmployeeController extends BaseController {
	@Autowired
	@Qualifier("employeeDao")
	private EmployeeDao empDao;
	@Autowired
	private SessionData sessionobj;

	String filePath = null;
	Part part = null;

	private List<Category> categoryCmpList;
	private List<Countries> countryCmpList;
	private List<State> stateCmpList;
	private List<Cities> cityCmpList;

	public EmployeeDao getEmpDao() {
		return empDao;
	}

	public void setEmpDao(EmployeeDao empDao) {
		this.empDao = empDao;
	}

	/*
	 * @RequestMapping(value = "searchEmployee", method = RequestMethod.GET) public
	 * ModelAndView employeeList(ModelMap model) { ModelAndView modelObj = null; if
	 * (sessionobj != null && sessionobj.getIsValidLogin()) { List<Employee> empList
	 * = empDao.getEmpList(); modelObj = new ModelAndView("searchEmployee",
	 * "empList", empList); } else { Response resp =
	 * getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
	 * modelObj = getLogoutView(resp); } return modelObj; }
	 */

	@RequestMapping(value = "deleteAllEmployees", method = RequestMethod.GET)
	public ModelAndView deleteAllEmployees(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			empDao.deleteAllEmployees();
			List<Employee> empList = empDao.getActiveEmployeeList();
			modelObj = new ModelAndView("activeEmployee", "empList", empList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "email", method = RequestMethod.GET)
	public ModelAndView email(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			SendMailRequest sendMailRequest = new SendMailRequest();

			sendMailRequest.setToEmail(sessionobj.getToEmail());
			sendMailRequest.setSubject(sessionobj.getMailSubject());
			sendMailRequest.setMessage(sessionobj.getMailMessage());

			modelObj = new ModelAndView("sendMail", "SendMailRequest", sendMailRequest);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "sendEmail", method = RequestMethod.POST)
	public String sendEmail(@ModelAttribute SendMailRequest sendMailRequest, BindingResult result, ModelMap model) {
		// System.out.println(empRequest.getEmployeeName());
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			HSSFWorkbook workbook = null;
			List<EmployeeResponseReport> reportObj = sessionobj.getResponseReport();
			String fileName = "EmpDataReport.xls";
			try {
				switch (sessionobj.getReportFrom()) {
				case "swipe":
					workbook = getHssfSwipeWorkBook(reportObj, fileName);
					break;
				case "monthly":
					workbook = getMonthlyHoursWorkBook(reportObj, fileName);
					break;
				default:
					workbook = getWeeklyHoursWorkBook(reportObj, fileName);
					break;
				}

				com.gcs.resourceMethods.EmailUtility.sendEmailCall(sendMailRequest.getToEmail(), fileName, workbook,
						sendMailRequest.getCcEmails(), sendMailRequest.getSubject(), sendMailRequest.getMessage());

				sessionobj.setStatusMessage("Email sent Successfully");

			} catch (Exception ex) {
				System.out.println(ex);
			}
			return "redirect:/Reports/" + sessionobj.getEmialCallPath();
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createEmployee", method = RequestMethod.GET)
	public ModelAndView createEmployee(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			List<Countries> countryList = empDao.getCountryList();
			EmployeeRequest empReq = new EmployeeRequest();
			Map<String, Object> mapModel = new HashMap<String, Object>();
			model.put("EmployeeRequest", empReq);
			model.put("categoryList", categoryList);
			model.put("countryList", countryList);

			modelObj = new ModelAndView("createEmployee", mapModel);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@ResponseBody
	@RequestMapping(value = "getStatesList")
	public String getStatesList(@RequestParam Integer country_Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		System.out.println("CountryId" + country_Id);
		String States = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<State> stateList = empDao.getSatesList(country_Id != null ? Integer.valueOf(country_Id) : 1);
			System.out.println("States...." + stateList);
			if (stateList != null && stateList.size() > 0) {
				Iterator<State> itr = stateList.iterator();
				StringBuffer sbrObj = new StringBuffer();
				model.put("stateList", stateList);
				while (itr.hasNext()) {
					State state = itr.next();
					sbrObj.append("<option value='").append(state.getId()).append("'>").append(state.getName())
							.append("</option>");
				}
				if (sbrObj != null)
					States = sbrObj.toString();
			}
		}
		return States;
	}

	@ResponseBody
	@RequestMapping(value = "getCitiesList")
	public String getCitiesList(@RequestParam Integer state_Id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		System.out.println("StateId" + state_Id);
		String Cities = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Cities> cityList = empDao.getCitiesList(state_Id != null ? Integer.valueOf(state_Id) : 1);
			System.out.println("Cities...." + cityList);
			if (cityList != null && cityList.size() > 0) {
				model.put("cityList", cityList);
				Iterator<Cities> itr = cityList.iterator();
				StringBuffer sbrObj = new StringBuffer();
				while (itr.hasNext()) {
					Cities city = itr.next();
					sbrObj.append("<option value='").append(city.getId()).append("'>").append(city.getName())
							.append("</option>");
				}
				if (sbrObj != null)
					Cities = sbrObj.toString();
			}
			/*
			 * json = new Gson().toJson(cityList); response.reset();
			 * response.getWriter().write(json); } else { response.reset();
			 * response.getWriter().write("[]"); }
			 */
		}
		return Cities;
	}

	@RequestMapping(value = "/editEmployee/{empId}", method = RequestMethod.GET)
	public ModelAndView editEmployee(@PathVariable String empId, Model model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> categoryList = empDao.getCategoryList();
			Employee empObj = empDao.getEmployeeData(empId);

			if (empObj != null) {
				EmployeeRequest empReq = new EmployeeRequest();
				Map<String, Object> mapModel = new HashMap<String, Object>();
				List<Countries> countryList = empDao.getCountryList();
				List<State> stateList = empDao.getSatesList(empObj.getEmployeeCountry().getId());
				System.out.println("States.....with " + empObj.getEmployeeCountry().getId() + "  " + stateList);
				List<Cities> cityList = empDao.getCitiesList(empObj.getEmployeeState().getId());
				System.out.println("Cities.......with " + empObj.getEmployeeState().getId() + "  " + cityList);
				mapModel.put("countryList", countryList);
				mapModel.put("stateList", stateList);
				mapModel.put("cityList", cityList);
				mapModel.put("categoryList", categoryList);
				mapModel.put("EmployeeRequest", empReq);
				mapModel.put("empObj", empObj);

				modelObj = new ModelAndView("editEmployee", mapModel);
			}
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/deleteEmployee/{empId}", method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String empId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (empId != null) {
				empDao.delete(empId, true);
			}
			return "redirect:/Employee/activeEmployee";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "createOrUpdateEmployee", method = RequestMethod.POST)
	public String createOrUpdateEmployee(@ModelAttribute EmployeeRequest empRequest, BindingResult result,
			ModelMap model) {
		// System.out.println(empRequest.getEmployeeName());
		System.out.println("EmployeeData...." + empRequest.getEmpId());
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Employee empObj = new Employee();
			if (empRequest.getEmpId() != null) {
				empObj.setEmpId(empRequest.getEmpId());
				Boolean isTrue= empDao.setEndDateByEmpId(empRequest.getEmpId(),getCurrentDate());
			}
			empObj.setEmployeeId(empRequest.getEmployeeId());
			empObj.setEmployeeName(empRequest.getEmployeeName());
			empObj.setEmployeeCategory(empRequest.getCategory());
			empObj.setEmployeeCountry(empRequest.getCountries());
			empObj.setEmployeeState(empRequest.getStates());
			empObj.setEmployeeCity(empRequest.getCities());
			empObj.setEmployeeDesg(empRequest.getEmployeeDesg());
			empObj.setEmployeeSpecialization(empRequest.getEmployeeSpecialization());
			empObj.setEmployeeMobilenumber(empRequest.getEmployeeMobilenumber());
			empObj.setStatus(empRequest.isStatus());
			empDao.saveOrUpdate(empObj);
			return "redirect:/Employee/activeEmployee";
		} else
			return "redirect:/login";
	}

	private static java.sql.Date getCurrentDate() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Date(today.getTime());
	}
	
	@RequestMapping(value = "employeeBulkUpload", method = RequestMethod.GET)
	public String employeeBulkUploadData(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			return "employeeBulkUpload";
		} else
			return "redirect:/login";
	}

	@RequestMapping
	public Category compareCategory(List<Category> listCategory, String categoryName) {
		Category category = null;
		System.out.println("categoryName..." + categoryName);
		for (Category cat : listCategory) {
			if (cat.getCategoryName().equalsIgnoreCase(categoryName)) {
				category = new Category();
				System.out.println(categoryName + "...." + cat.getCategoryName());
				category.setCategoryId(cat.getCategoryId());
				category.setCategoryName(cat.getCategoryName());
			}
		}

		return category;
	}

	@RequestMapping
	public Countries compareCountry(List<Countries> listCountry, String countryName) {
		Countries country = null;

		for (Countries cun : listCountry) {
			if (cun.getName().equalsIgnoreCase(countryName)) {
				country = new Countries();
				country.setId(cun.getId());
				country.setName(cun.getName());
				country.setPhonecode(cun.getPhonecode());
			}
		}
		return country;
	}

	@RequestMapping
	public State compareState(List<State> listStates, String stateName) {
		State state = null;

		for (State st : listStates) {
			if (st.getName().equalsIgnoreCase(stateName)) {
				state = new State();
				state.setId(st.getId());
				state.setName(st.getName());
				state.setCountryId(st.getCountryId());
			}
		}
		return state;
	}

	@RequestMapping
	public Cities compareCity(List<Cities> listCities, String cityName) {
		Cities city = null;

		for (Cities ct : listCities) {
			if (ct.getName().equalsIgnoreCase(cityName)) {
				city = new Cities();
				city.setId(ct.getId());
				city.setName(ct.getName());
				city.setStateId(ct.getStateId());
				city.setCountryId(ct.getCountryId());
			}
		}
		return city;

	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String fileUpload(@RequestParam CommonsMultipartFile file, HttpSession session) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			try {

				EmployeeController empController = new EmployeeController();

				File file1 = new File(file.getOriginalFilename());
				file.transferTo(file1);
				System.out.println("Entering into upload method in controller........");
				String fileName = file.getOriginalFilename();
				System.out.println("File Name..." + file1 + "  File Original Name..." + file.getOriginalFilename());
				FileInputStream fileStream = new FileInputStream(file1);
				// FileInputStream fileStream = new FileInputStream(new
				// File("D:\\EmployeeData.xlsx"));

				// Create Workbook instance holding reference to .xlsx file
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

				// Get first/desired sheet from the workbook
				XSSFSheet ws = workbook.getSheetAt(0);

				List<Employee> empList = new ArrayList<Employee>();

				ws.setForceFormulaRecalculation(true);

				int rowNum = ws.getLastRowNum() + 1;
				int colNum = ws.getRow(0).getLastCellNum();
				int employeeId = -1, employeeCategory = -1, employeeName = -1, employeeDesg = -1,
						employeeSpecialization = -1, employeeCountry = -1, employeeState = -1, employeeCity = -1,
						employeeMobilenumber = -1, status = -1;

				// Read the headers first. Locate the ones you need
				XSSFRow rowHeader = ws.getRow(0);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = rowHeader.getCell(j);
					String cellValue = cellToString(cell);
					if ("Employee_Id".equalsIgnoreCase(cellValue) || "Employee Id".equalsIgnoreCase(cellValue)) {
						employeeId = j;
					} else if ("Employee_Name".equalsIgnoreCase(cellValue)
							|| "Employee Name".equalsIgnoreCase(cellValue)) {
						employeeName = j;
					} else if ("Employee_Designation".equalsIgnoreCase(cellValue)
							|| "Employee Designation".equalsIgnoreCase(cellValue)) {
						employeeDesg = j;
					} else if ("Employee_Category".equalsIgnoreCase(cellValue)
							|| "Employee Category".equalsIgnoreCase(cellValue)) {
						employeeCategory = j;
					} else if ("Country".equalsIgnoreCase(cellValue)) {
						employeeCountry = j;
					} else if ("State".equalsIgnoreCase(cellValue)) {
						employeeState = j;
					} else if ("City".equalsIgnoreCase(cellValue)) {
						employeeCity = j;
					} else if ("Specialization".equalsIgnoreCase(cellValue)) {
						employeeSpecialization = j;
					} else if ("Mobile_Number".equalsIgnoreCase(cellValue)
							|| "Mobile Number".equalsIgnoreCase(cellValue)) {
						employeeMobilenumber = j;
					} else if ("Status".equalsIgnoreCase(cellValue)) {
						status = j;
					}

				}

				if (employeeId == -1 || employeeName == -1 || employeeDesg == -1 || employeeSpecialization == -1
						|| employeeMobilenumber == -1 || employeeCategory == -1 || employeeCountry == -1
						|| employeeState == -1 || employeeCity == -1 || status == -1) {
					try {
						throw new Exception("Could not find header indexes\nemployeeId : " + employeeId
								+ " | EmployeeName : " + employeeName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				for (int i = 1; i < rowNum; i++) {
					XSSFRow row = ws.getRow(i);
					Employee employee = new Employee();

					DataFormatter df=new DataFormatter();
					String emId=df.formatCellValue(row.getCell(employeeId));
					employee.setEmployeeId(emId);
					employee.setEmployeeName(cellToString(row.getCell(employeeName)));
					employee.setEmployeeDesg(cellToString(row.getCell(employeeDesg)));
					employee.setEmployeeSpecialization(cellToString(row.getCell(employeeSpecialization)));
					employee.setEmployeeMobilenumber(cellToString(row.getCell(employeeMobilenumber)));

					String ctgName = (cellToString(row.getCell(employeeCategory)));
					if (categoryCmpList == null) {
						//System.out.println("empDao..." + empDao);
						categoryCmpList = empDao.getCategoryList();
					}
					Category category = empController.compareCategory(categoryCmpList, ctgName);
					//System.out.println("Category......." + category);
					employee.setEmployeeCategory(category);

					String cntName = cellToString(row.getCell(employeeCountry));
					if (countryCmpList == null) {
						//System.out.println("empDao..." + empDao);
						countryCmpList = empDao.getCountryList();
					}
					Countries country = empController.compareCountry(countryCmpList, cntName);
					//System.out.println("country......." + country);
					employee.setEmployeeCountry(country);

					String stName = cellToString(row.getCell(employeeState));
					if (stateCmpList == null) {
						//System.out.println("empDao..." + empDao);
						stateCmpList = empDao.getStates();
					}
					State state = empController.compareState(stateCmpList, stName);
					//System.out.println("state......." + state);
					employee.setEmployeeState(state);

					String ctName = cellToString(row.getCell(employeeCity));
					if (cityCmpList == null) {
						//System.out.println("empDao..." + empDao);
						cityCmpList = empDao.getCities();
					}
					Cities city = empController.compareCity(cityCmpList, ctName);
					//System.out.println("city......." + city);
					employee.setEmployeeCity(city);
					int empStatus=(int) row.getCell(status).getNumericCellValue();
					if(empStatus==1)
						employee.setStatus(true);
					else
						employee.setStatus(false);
					// employee.setStatus(cellToString(row.getCell(status)));

					// employee.setPassword("");
					empList.add(employee);
				}

				if (empList.size() > 0) {
					
						//System.out.println("Inserting Employee");
						empDao.insert(empList);
					
				}
				fileStream.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return "redirect:/Employee/activeEmployee";
		} else
			return "redirect:/login";
	}

	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;
		type = cell.getCellType();

		switch (type) {

		case XSSFCell.CELL_TYPE_NUMERIC:
			result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();

			break;
		case XSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
		}

		return result.toString();
	}

	public HSSFWorkbook getWeeklyHoursWorkBook(List<EmployeeResponseReport> empBean, String fileName) {

		// String file="D:/EmpDataReport.xlsx";
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SNo");
			rowhead.createCell(1).setCellValue("Date From");
			rowhead.createCell(2).setCellValue("Date To");
			rowhead.createCell(3).setCellValue("Emp Name");
			rowhead.createCell(4).setCellValue("Emp Id");
			rowhead.createCell(5).setCellValue("Mon");
			rowhead.createCell(6).setCellValue("Tue");
			rowhead.createCell(7).setCellValue("wed");
			rowhead.createCell(8).setCellValue("Thu");
			rowhead.createCell(9).setCellValue("Fri");
			rowhead.createCell(10).setCellValue("Sat");
			rowhead.createCell(11).setCellValue("Sun");
			rowhead.createCell(12).setCellValue("Total In Hours");
			rowhead.createCell(13).setCellValue("Total Hours");

			Iterator<EmployeeResponseReport> itr = empBean.iterator();
			int index = 1;
			while (itr.hasNext()) {
				EmployeeResponseReport wkEmpBean = itr.next();
				HSSFRow row = sheet.createRow((short) index);
				row.createCell(0).setCellValue(index);
				row.createCell(1).setCellValue(wkEmpBean.getFromDate());
				row.createCell(2).setCellValue(wkEmpBean.getToDate());
				row.createCell(3).setCellValue(wkEmpBean.getEmployeeName());
				row.createCell(4).setCellValue(wkEmpBean.getEmpID());
				row.createCell(5).setCellValue(wkEmpBean.getMonHours());
				row.createCell(6).setCellValue(wkEmpBean.getTueHours());
				row.createCell(7).setCellValue(wkEmpBean.getWedHours());
				row.createCell(8).setCellValue(wkEmpBean.getThuHours());
				row.createCell(9).setCellValue(wkEmpBean.getFriHours());
				row.createCell(10).setCellValue(wkEmpBean.getSatHours());
				row.createCell(11).setCellValue(wkEmpBean.getSunHours());
				row.createCell(12).setCellValue(wkEmpBean.getTotalInHours());
				row.createCell(13).setCellValue(wkEmpBean.getTotalHours());
				index++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		}

		catch (Exception e) {

		}
		return workbook;

	}

	public HSSFWorkbook getMonthlyHoursWorkBook(List<EmployeeResponseReport> empBean, String fileName) {

		// String file="D:/EmpDataReport.xlsx";
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SNo.");
			rowhead.createCell(1).setCellValue("Date From");
			rowhead.createCell(2).setCellValue("Date To");
			rowhead.createCell(3).setCellValue("Emp Name");
			rowhead.createCell(4).setCellValue("Emp Id");
			rowhead.createCell(5).setCellValue("Total In Hours");
			rowhead.createCell(6).setCellValue("Total Hours");

			Iterator<EmployeeResponseReport> itr = empBean.iterator();
			int index = 1;
			while (itr.hasNext()) {
				EmployeeResponseReport wkEmpBean = itr.next();
				HSSFRow row = sheet.createRow((short) index);
				row.createCell(0).setCellValue(index);
				row.createCell(1).setCellValue(wkEmpBean.getFromDate());
				row.createCell(2).setCellValue(wkEmpBean.getToDate());
				row.createCell(3).setCellValue(wkEmpBean.getEmployeeName());
				row.createCell(4).setCellValue(wkEmpBean.getEmpID());
				row.createCell(5).setCellValue(wkEmpBean.getTotalInHours());
				row.createCell(6).setCellValue(wkEmpBean.getTotalHours());
				index++;
			}
			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return workbook;
	}

	public HSSFWorkbook getHssfSwipeWorkBook(List<EmployeeResponseReport> empBean, String fileName) {

		// String file="D:/EmpDataReport.xlsx";
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SNo.");
			rowhead.createCell(1).setCellValue("Emp Id");
			rowhead.createCell(2).setCellValue("Emp Name");
			rowhead.createCell(3).setCellValue("Shift start Time");
			rowhead.createCell(4).setCellValue("Shift End Time");
			rowhead.createCell(5).setCellValue("SwipeTime");
			rowhead.createCell(6).setCellValue("IN_OUT");
			Iterator<EmployeeResponseReport> itr = empBean.iterator();
			int index = 1;
			while (itr.hasNext()) {
				EmployeeResponseReport wkEmpBean = itr.next();
				HSSFRow row = sheet.createRow((short) index);
				row.createCell(0).setCellValue(index);
				row.createCell(1).setCellValue(wkEmpBean.getEmpID());
				row.createCell(2).setCellValue(wkEmpBean.getEmployeeName());
				row.createCell(3).setCellValue(wkEmpBean.getShiftStartTime());
				row.createCell(4).setCellValue(wkEmpBean.getShiftEndTime());
				row.createCell(5).setCellValue(wkEmpBean.getSwipeTime());
				row.createCell(6).setCellValue(wkEmpBean.getSwipeInOut());
				index++;
			}

			FileOutputStream fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		}

		catch (Exception e) {

		}
		return workbook;
	}

	@RequestMapping(value = "searchCategory", method = RequestMethod.GET)
	public String searchCategory(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Category> list = empDao.getCategoryList();
			model.put("ctgList", list);
			return "categoryList";
		} else
			return "redirect:/login";
	}

	@RequestMapping(value = "activeEmployee", method = RequestMethod.GET)
	public ModelAndView employeeList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> employeeList = empDao.getActiveEmployeeList();
			modelObj = new ModelAndView("activeEmployee", "employeeList", employeeList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "inActiveEmp", method = RequestMethod.GET)
	public ModelAndView empList(ModelMap model) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<Employee> empList = empDao.getInActiveEmpList();
			modelObj = new ModelAndView("inActiveEmp", "empList", empList);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return modelObj;
	}

	@RequestMapping(value = "/updateEmployee/{empId}", method = RequestMethod.GET)
	public String updateEmployee(@PathVariable String empId, Model model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			if (empId != null) {
				empDao.updateStatus(empId, true);
			}
			return "redirect:/Employee/inActiveEmp";
		} else
			return "redirect:/login";
	}

}
