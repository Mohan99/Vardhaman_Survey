package com.gcs.controller;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Users;
import com.gcs.responseDao.EmployeeResponseReport;

import org.springframework.context.annotation.ScopedProxyMode;

@Component("sessionObj")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	
	private boolean isValidLogin;
	private String statusCode;
	private String statusMessage;
	
	private String LoginUserName;
	private Employee empObj;
	private Users userObj;
	private String UserName;
	private String empId;
	private String projectId;
	private Date date;
	private String emialCallPath;
	
	private String oldDate;
	private String currentDate;
	
	private boolean isDataDeleted;
	private long employeeCount;
	private long projectsCount;
	private long resourceCount;
	private long usersCount;
	
	public long getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(long usersCount) {
		this.usersCount = usersCount;
	}

	private List<EmployeeResponseReport> responseReport;
	private String reportFrom;
	private boolean isTableDataEmpty;
	private String toEmail;
	private String mailSubject="Last week attendance report";
	private String mailMessage="Hi, Please find the enclosed Gemini Attendance Analysis Report of your team for february ";
	
	
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public boolean getIsValidLogin() {
		return isValidLogin;
	}

	public void setValidLogin(boolean isValidLogin) {
		this.isValidLogin = isValidLogin;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getLoginUserName() {
		return LoginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		LoginUserName = loginUserName;
	}

	public Employee getEmpObj() {
		return empObj;
	}

	public void setEmpObj(Employee empObj) {
		this.empObj = empObj;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public long getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(long employeeCount) {
		this.employeeCount = employeeCount;
	}


	public Users getUserObj() {
		return userObj;
	}

	public void setUserObj(Users userObj) {
		this.userObj = userObj;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(String mailMessage) {
		this.mailMessage = mailMessage;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getEmialCallPath() {
		return emialCallPath;
	}

	public void setEmialCallPath(String emialCallPath) {
		this.emialCallPath = emialCallPath;
	}

	public List<EmployeeResponseReport> getResponseReport() {
		return responseReport;
	}

	public void setResponseReport(List<EmployeeResponseReport> responseReport) {
		this.responseReport = responseReport;
	}

	public String getReportFrom() {
		return reportFrom;
	}

	public void setReportFrom(String reportFrom) {
		this.reportFrom = reportFrom;
	}

	public String getOldDate() {
		return oldDate;
	}

	public void setOldDate(String oldDate) {
		this.oldDate = oldDate;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public boolean isDataDeleted() {
		return isDataDeleted;
	}

	public void setDataDeleted(boolean isDataDeleted) {
		this.isDataDeleted = isDataDeleted;
	}

	public boolean isTableDataEmpty() {
		return isTableDataEmpty;
	}

	public void setTableDataEmpty(boolean isTableDataEmpty) {
		this.isTableDataEmpty = isTableDataEmpty;
	}

	public long getProjectsCount() {
		return projectsCount;
	}

	public void setProjectsCount(long projectsCount) {
		this.projectsCount = projectsCount;
	}

	public long getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(long resourceCount) {
		this.resourceCount = resourceCount;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
	
}
