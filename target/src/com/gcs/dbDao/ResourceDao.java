package com.gcs.dbDao;


import java.util.Date;
import java.util.List;

import com.gcs.bean.ResourceReportBean;
import com.gcs.db.businessDao.Resourceallocations;

public interface ResourceDao {

	public List<Resourceallocations> getResources();
	public void saveOrUpdate(Resourceallocations resource);
	public long resourceCount();
	public List<Resourceallocations> getResourcesList();
	public List<Resourceallocations> getResourcesListByEmpId(String employeeId);
	public Resourceallocations getResourceByProjectId(String empId,String projectId);
	public  void delete(String id,boolean isResource);
	public void insert(Resourceallocations res);
	public Resourceallocations getResourceByResId(int resId);
	public void updateResource(Integer resId, Date date);
	public List<ResourceReportBean> getEmployeeReport();
	//public Resourceallocations getProjectEndDate(Integer id, Date date);
	//public List<Resourceallocations> getProjectEndDate(String id, Date date);
	public void updateRes(Integer projectId, Date projectTo);
	//Resourceallocations getProjectEndDate(String id, Date projectTo);
	public List<Resourceallocations> getEmpBenchPeriodReport(int empId,Date startDate,Date endDate);
}
