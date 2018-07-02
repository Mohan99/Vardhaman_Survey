package com.gcs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gcs.bean.EmpBenchBean;
import com.gcs.bean.ResourceReportBean;
import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.dbDao.ProjectDao;
import com.gcs.dbDao.ResourceDao;
import com.gcs.requestDao.EmpReportDataRequest;
import com.gcs.requestDao.ResourceRequest;
import com.gcs.responseDao.EmployeeResponseReport;
import com.gcs.responseDao.Response;

@Controller
@RequestMapping("Reports")
public class ReportsController extends BaseController {
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

	private List<Employee> empActiveList;
	private List<Resourceallocations> resCmpList;
	private List<Projects> projCmpList;
	private ReportsController controller;

	/*
	 * private void setEmilIdToSession(String empID) {
	 * sessionobj.setToEmail(empID!=null?empDao.getEmployeeData(empID).getEmailId():
	 * null); }
	 */

	public Projects getProjectObj(List<Projects> projList, int projId) {
		Projects projObj = null;
		for (Projects projects : projList) {
			if (projId == projects.getProjectId()) {
				projObj = new Projects();
				projObj.setProjectId(projects.getProjectId());
				projObj.setProjectName(projects.getProjectName());
				projObj.setStartDate(projects.getStartDate());
				projObj.setEndDate(projects.getEndDate());
				projObj.setStatus(projects.isStatus());
			}
		}
		return projObj;
	}

	@RequestMapping(value = "getAllEmpReport", method = RequestMethod.GET)
	public String allEmployeeReport(ModelMap model) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<ResourceReportBean> resourceList = resourceDao.getEmployeeReport();

			try {
				empActiveList = empDao.getActiveEmployeeList();
				resCmpList = resourceDao.getResources();
				Employee employee = null;
				Resourceallocations resource = null;
				ResourceReportBean bean = null;
				for (Employee emp : empActiveList) {
					boolean found = false;
					for (Resourceallocations res : resCmpList) {
						// System.out.println("emp.getEmpId()="+emp.getEmpId()+",res.getEmployeeId()="+res.getEmployeeId());
						if (emp.getEmpId() == res.getEmployeeId()) {
							found = true;
						}
					}

					if (found == false) {
						System.out.println("emp.getEmpId()=" + emp.getEmpId());
						employee = new Employee();
						bean = new ResourceReportBean();
						resource = new Resourceallocations();
						employee.setEmpId(emp.getEmpId());
						employee.setEmployeeId(emp.getEmployeeId());
						employee.setEmployeeName(emp.getEmployeeName());
						employee.setEmployeeDesg(emp.getEmployeeDesg());
						employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
						employee.setEmployeeCategory(emp.getEmployeeCategory());
						employee.setEmployeeCity(emp.getEmployeeCity());
						employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());

						resource.setProjectFrom(null);

						bean.setEmp(emp);
						bean.setResource(resource);
						bean.setStatus("Bench");
						resourceList.add(bean);
					}

					model.put("resourceList", resourceList);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "employeeReport";

	}

	@RequestMapping(value = "exportEmployeesReport", method = RequestMethod.GET)
	public String exportEmployeeReport(ModelMap model, HttpServletResponse response) {
		ModelAndView modelObj = null;
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			List<ResourceReportBean> resourceList = resourceDao.getEmployeeReport();
			controller = new ReportsController();
			try {
				/*
				 * if (empActiveList == null) empActiveList = empDao.getActiveEmployeeList(); if
				 * (resCmpList == null) resCmpList = resourceDao.getResources();
				 */
				Employee employee = null;
				ResourceReportBean bean = null;
				Resourceallocations resource = null;
				for (Employee emp : empActiveList) {
					boolean found = false;
					for (Resourceallocations res : resCmpList) {
						// System.out.println("emp.getEmpId()="+emp.getEmpId()+",res.getEmployeeId()="+res.getEmployeeId());
						if (emp.getEmpId() == res.getEmployeeId()) {
							found = true;
						}
					}

					if (found == false) {
						System.out.println("emp.getEmpId()=" + emp.getEmpId());
						employee = new Employee();
						resource = new Resourceallocations();
						bean = new ResourceReportBean();
						employee.setEmpId(emp.getEmpId());
						employee.setEmployeeId(emp.getEmployeeId());
						employee.setEmployeeName(emp.getEmployeeName());
						employee.setEmployeeDesg(emp.getEmployeeDesg());
						employee.setEmployeeSpecialization(emp.getEmployeeSpecialization());
						employee.setEmployeeCategory(emp.getEmployeeCategory());
						employee.setEmployeeCity(emp.getEmployeeCity());
						employee.setEmployeeMobilenumber(emp.getEmployeeMobilenumber());

						resource.setProjectFrom(null);
						bean.setResource(resource);
						bean.setEmp(emp);
						bean.setStatus("Bench");
						resourceList.add(bean);
					}
				}
				controller.doExport(resourceList, response);
				model.put("resourceList", resourceList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "employeeReport";
		} else
			return "employeeReport";

	}

	@RequestMapping(value = "/empBenchSearch", method = RequestMethod.GET)
	public String empBenchSearch(ModelMap model) {
		ModelAndView modelObj = null;
		List<Resourceallocations> resourceList = null;
		List<Employee> empList = null;
		ResourceRequest resReq = new ResourceRequest();
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			resourceList = resourceDao.getResourcesList();
			empList = empDao.getActiveEmployeeList();
			System.out.println("searchResource......." + resourceList);
			// modelObj = new ModelAndView("resourceList", resourceList);
			model.put("resourceList", resourceList);
			model.put("empList", empList);
			model.put("resReq", resReq);
		} else {
			Response resp = getResponse(ConstantVariables.SC1, ConstantVariables.TTRMsgInvalidsession);
			modelObj = getLogoutView(resp);
		}
		return "empBenchReport";
	}
	
	public Employee getEmployeeObj(int empId,List<Employee> empList) {
		Employee emp=null;
		for (Employee employee : empList) {
			if(employee.getEmpId()==empId) {
				emp=new Employee();
				emp.setEmployeeId(employee.getEmployeeId());
				emp.setEmployeeName(employee.getEmployeeName());
			}
		}
		return emp;
		
	}

	@RequestMapping(value = "/empBenchPeriod", method = RequestMethod.GET)
	public String empBenchPeriodReport(ModelMap model, @RequestParam int empId, @RequestParam Date startDate,
			@RequestParam Date endDate) throws ParseException {
		System.out.println("empId==" + empId + " ,startDate=" + startDate + " ,endDate" + endDate);
		List<Resourceallocations> resList = resourceDao.getEmpBenchPeriodReport(empId, startDate, endDate);
		Date todate=null;
		Date fromdate=null;
		Date currDate = new Date();
	    Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");  
	    String strDate= formatter.format(date);  
		int i=0;
		EmpBenchBean bean = null;
		List<EmpBenchBean> benchList = new ArrayList<EmpBenchBean>();
		Employee emp=null;
		empActiveList=empDao.getActiveEmployeeList();
		emp=getEmployeeObj(empId,empActiveList);
		for(Resourceallocations myList:resList)
		{
			if(myList.getProjectTo()!=null)
			{
				todate = myList.getProjectTo();
			}
			else
				todate = currDate;
			i++;
			if(todate==null)
			{
				break;
			}
			else
			{
			if(i<resList.size()) {
			Resourceallocations res = resList.get(i);
			fromdate = res.getProjectFrom();
			if(todate!=null) {
			long diff = fromdate.getTime() - todate.getTime();
			if(diff>0)
			{
				bean = new EmpBenchBean();
				bean.setEmployeeId(emp.getEmployeeId());
				bean.setEmployeeName(emp.getEmployeeName());
				bean.setFromDate(todate);	
				bean.setToDate(fromdate);
				bean.setBenchPeriod(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
				benchList.add(bean);
			}
			
			}
			else
			{
				System.out.println("This is the current project");
			}
			
					
		}
			if(i==resList.size() && (todate!=null || todate.before(currDate))) {
				Resourceallocations res = resList.get(i-1);
				fromdate = res.getProjectFrom();
				long diff = currDate.getTime() - todate.getTime();
				if(diff>0)
				{
					bean = new EmpBenchBean();
					bean.setEmployeeId(emp.getEmployeeId());
					bean.setEmployeeName(emp.getEmployeeName());
					bean.setFromDate(todate);	
					SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
					String CurrDate =  s.format(currDate);
	    			//sbean.setToDate(currDate.getDate());
					bean.setBenchPeriod(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
					benchList.add(bean);
				}
			}
	
		}
		}
		model.put("benchList", benchList);
		return "empBenchPeriodReport";
		
	}

	private void downloadFile(final String fileName, HttpServletResponse response) {
		final File f = new File(fileName);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "max-age=0");
		FileInputStream fin = null;
		ServletOutputStream os = null;
		try {
			fin = new FileInputStream(f);
			final int size = 1024;
			response.setContentLength(fin.available());
			final byte[] buffer = new byte[size];
			os = response.getOutputStream();
			int length = 0;
			while ((length = fin.read(buffer)) != -1) {
				os.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fin != null)
					fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void doExport(List<ResourceReportBean> resourceList, HttpServletResponse response) throws Exception {
		System.out.println("Exporting Resources");
		if (resourceList != null && !resourceList.isEmpty()) {
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet sheet = workBook.createSheet();
			HSSFRow headingRow = sheet.createRow(0);
			headingRow.createCell((short) 0).setCellValue("ID");
			headingRow.createCell((short) 1).setCellValue("Name");
			headingRow.createCell((short) 2).setCellValue("Designation");
			headingRow.createCell((short) 3).setCellValue("Service Category");
			headingRow.createCell((short) 4).setCellValue("Primary Assignment");
			headingRow.createCell((short) 5).setCellValue("Other Assignments");
			headingRow.createCell((short) 6).setCellValue("Specialization");
			headingRow.createCell((short) 7).setCellValue("Mobile No");
			headingRow.createCell((short) 8).setCellValue("Location");
			headingRow.createCell((short) 9).setCellValue("Date");
			/*
			 * headingRow.createCell((short)12).setCellValue("Created Date");
			 * headingRow.createCell((short)13).setCellValue("Updated Date");
			 */
			/* headingRow.createCell((short)12).setCellValue("Status"); */
			short rowNo = 1;
			for (ResourceReportBean details : resourceList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellValue(details.getEmp().getEmployeeId());
				row.createCell((short) 1).setCellValue(details.getEmp().getEmployeeName());
				row.createCell((short) 2).setCellValue(details.getEmp().getEmployeeDesg());
				row.createCell((short) 3).setCellValue(details.getEmp().getEmployeeCategory().getCategoryName());
				if (details.getStatus() == "Bench") {
					row.createCell((short) 4).setCellValue("Bench");
				} else {
					row.createCell((short) 4).setCellValue(details.getPrimaryProjects());
				}

				if (details.getStatus() == "Bench") {
					row.createCell((short) 5).setCellValue("Bench");
				} else {
					row.createCell((short) 5).setCellValue(details.getSecondaryProjects());
				}

				row.createCell((short) 6).setCellValue(details.getEmp().getEmployeeSpecialization());
				row.createCell((short) 7).setCellValue(details.getEmp().getEmployeeMobilenumber());
				row.createCell((short) 8).setCellValue(details.getEmp().getEmployeeCity().getName());
				if (details.getResource().getProjectFrom() == null) {
					row.createCell((short) 9).setCellValue("");
				} else {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					String date = formatter.format(details.getResource().getProjectFrom());
					row.createCell((short) 9).setCellValue(date);
				}
				/*
				 * SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); String
				 * strDate= formatter.format(details.getCreatedDt());
				 * row.createCell((short)12).setCellValue(strDate); String strDate1=
				 * formatter.format(details.getUpdatedDt());
				 * row.createCell((short)13).setCellValue(strDate1);
				 */
				/*
				 * row.createCell((short)12).setCellValue(details.isValidYn());
				 */
				rowNo++;
			}

			String file = "EmployeeResourceReport.xlsx";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				fos.flush();
				fos.close();
				downloadFile(file, response);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
	}

}
