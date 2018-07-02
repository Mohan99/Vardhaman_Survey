package com.gcs.requestDao;

import java.util.Date;


public class ResourceRequest {
	private int resId;
	private int employeeId;
	private int projectId;
	private String allocation;
	private String employeeName;
	private String projectName;
	private Date projectFrom;
	private Date projectTo;
	private boolean projectCompleted;
	
	
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public String getAllocation() {
		return allocation;
	}
	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}
	public Date getProjectFrom() {
		return projectFrom;
	}
	public void setProjectFrom(Date projectFrom) {
		this.projectFrom = projectFrom;
	}
	public Date getProjectTo() {
		return projectTo;
	}
	public void setProjectTo(Date projectTo) {
		this.projectTo = projectTo;
	}
	public boolean getProjectCompleted() {
		return projectCompleted;
	}
	public void setProjectCompleted(boolean projectCompleted) {
		this.projectCompleted = projectCompleted;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Override
	public String toString() {
		return "ResourceRequest [resId=" + resId + ", employeeId=" + employeeId + ", projectId=" + projectId
				+ ", allocation=" + allocation + ", employeeName=" + employeeName + ", projectName=" + projectName
				+ ", projectFrom=" + projectFrom + ", projectTo=" + projectTo + ", projectCompleted=" + projectCompleted
				+ "]";
	}
	

}
