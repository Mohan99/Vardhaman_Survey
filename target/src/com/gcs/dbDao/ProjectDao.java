package com.gcs.dbDao;

import java.util.Date;
import java.util.List;


import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;

public interface ProjectDao {

	public List<Projects> getProjects();
	public void saveOrUpdate(Projects project);
	public Projects getProjectData(String projectID);
	public List<Projects> getClosedProjects();
	public List<Projects> getActiveProjects();
//	public List<Projects> getProjectEndDate(String id, Date date);
	//public Projects getProjectEndDate(String id, Date date);
	Boolean setProjectEndDate(Integer id, Date date);
}
