package com.gcs.dbDaoImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.bean.ResourceReportBean;
import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.dbDao.ResourceDao;

@Transactional
@Repository("resourceDao")
public class ResourceDaoImpl extends HibernateDaoSupport implements ResourceDao {

	@Override
	public List<Resourceallocations> getResources() {
		@SuppressWarnings("unchecked")
		List<Resourceallocations> list = getHibernateTemplate().find("from Resourceallocations");
		return list;
	}

	@Override
	public void saveOrUpdate(Resourceallocations resource) {
		getHibernateTemplate().saveOrUpdate(resource);
		getSession().flush();
		// getSession().getTransaction().commit();

	}

	@Override
	public long resourceCount() {
		long count = ((long) getSession().createQuery("select count(*) from Resourceallocations").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}

	

	@Override
	public List<Resourceallocations> getResourcesList() {
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Resourceallocations> list = null;
		Resourceallocations resource = null;
		try {
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call GETRESOURSELIST()}");
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				resource = new Resourceallocations();
				resource.setResId(rs.getInt(1));
				resource.setEmployeeName(rs.getString(2));
				resource.setEmployeeId(rs.getInt(3));
				resource.setProjectId(rs.getInt(4));
				resource.setProjectName(rs.getString(5));
				resource.setProjectFrom(rs.getDate(6));
				if (rs.getDate(7) != null) {
					resource.setProjectTo(rs.getDate(7));
				}
				resource.setProjectCompleted(rs.getString(8));
				resource.setEmpId(rs.getString(9));
				
				list.add(resource);
			}
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	

	@Override
	public List<Resourceallocations> getResourcesListByEmpId(String employeeId) {
		CallableStatement stmt = null;
		ResultSet rs = null;
		List<Resourceallocations> list = null;
		Resourceallocations resource = null;
		try {
			list = new ArrayList<>();
			stmt = getSession().connection().prepareCall("{call GETRESOURCELISTBYEMPID(?)}");
			stmt.setString("empId", employeeId);
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				resource = new Resourceallocations();
				resource.setResId(rs.getInt(1));
				resource.setEmployeeName(rs.getString(2));
				resource.setEmployeeId(rs.getInt(3));
				resource.setProjectId(rs.getInt(4));
				resource.setProjectName(rs.getString(5));
				resource.setProjectFrom(rs.getDate(6));
				if (rs.getDate(7) != null) {
					resource.setProjectFrom(rs.getDate(6));
					resource.setProjectTo(rs.getDate(7));

				}
				resource.setProjectCompleted(rs.getString(8));
				resource.setEmpId(rs.getString(9));
				resource.setAllocation(rs.getString(10));
				list.add(resource);
			}
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Resourceallocations getResourceByProjectId(String empId, String projectId) {
		CallableStatement stmt = null;
		ResultSet rs = null;
		
		Resourceallocations resource = null;
		try {
			stmt = getSession().connection().prepareCall("{call GETRESOURCEBYEMP_PROJECTID(?,?)}");
			stmt.setString("empId", empId);
			stmt.setString("projectId", projectId);
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				resource = new Resourceallocations();
				resource.setResId(rs.getInt(1));
				resource.setEmployeeName(rs.getString(2));
				resource.setEmployeeId(rs.getInt(3));
				resource.setProjectId(rs.getInt(4));
				resource.setProjectName(rs.getString(5));
				resource.setProjectFrom(rs.getDate(6));
				if (rs.getDate(7) != null) {
					resource.setProjectTo(rs.getDate(7));
					resource.setProjectFrom(rs.getDate(6));
				}
				resource.setProjectCompleted(rs.getString(8));
				resource.setEmpId(rs.getString(9));
				resource.setAllocation(rs.getString(10));
			}
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resource;
	}

	
	
	
	@Override
	public void delete(String id, boolean isResource) {
		if (isResource) {
			Resourceallocations resource = null;
			@SuppressWarnings("unchecked")
			List<Resourceallocations> list = getHibernateTemplate()
					.find("from Resourceallocations a where resId like '%" + id + "%'");
			if (list != null && list.get(0) != null)
				resource = list.get(0);
			if (resource != null)
				getHibernateTemplate().delete(resource);
		} else {
			Resourceallocations res;
			res = (Resourceallocations) getHibernateTemplate().load(Resourceallocations.class, id);
			getHibernateTemplate().delete(res);
		}
		getHibernateTemplate().flush();
	}

	@Override
	public void insert(Resourceallocations res) {
		try {

			getHibernateTemplate().saveOrUpdate(res);
			getSession().flush();

		} catch (Exception e) {
			System.out.print(e);
			// getHibernateTemplate()afterPropertiesSet();.rollback();
		}
	}

	@Override
	public Resourceallocations getResourceByResId(int resId) {
		@SuppressWarnings("unchecked")
		List<Resourceallocations> list = getHibernateTemplate().find("from Resourceallocations where resId=" + resId);
		return list.get(0);
	}
	
	@Override
	public void updateResource(Integer resId, Date date) {
		// TODO Auto-generated method stub
		System.out.println("ResId>>>>"+resId+">>>>>>>>date"+date);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		String endDate=sdf.format(date);
			int query = getHibernateTemplate().bulkUpdate("update Resourceallocations set projectTo='"+endDate+"',projectCompleted='Y' where resId= " + resId);
		getHibernateTemplate().flush();

	}
	@Override
	public void updateRes(Integer Id, Date date) {
		// TODO Auto-generated method stub
		//System.out.println("projectId>>>>"+projectId+">>>>>>>>date"+date);
		//SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		//String endDate=sdf.format(date);
		Projects projects=new Projects();
		date=projects.getEndDate();
			int query = getHibernateTemplate().bulkUpdate("update Resourceallocations set projectTo='"+date+"' ,projectCompleted='Y' where projectId=" + Id+ " and projectCompleted='N'");
		getHibernateTemplate().flush();

	}

	
	
	@Override
	public List<ResourceReportBean> getEmployeeReport() {
		List<ResourceReportBean> reportList=new ArrayList<>();;
		CallableStatement stmt = null;
		ResultSet rs = null;
		Resourceallocations resource = null;
		Employee emp=null;
		Category cat=null;
		Cities city=null;
		ResourceReportBean bean=null;
		try {
			stmt = getSession().connection().prepareCall("{call ALLEMPLOYEESREPORT()}");
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				emp=new Employee();
				cat=new Category();
				city=new Cities();
				bean=new ResourceReportBean();
				resource=new Resourceallocations();
				emp.setEmpId(rs.getInt(1));
				emp.setEmployeeId(rs.getString(2));
				emp.setEmployeeMobilenumber(rs.getString(3));
				emp.setEmployeeSpecialization(rs.getString(4));
				emp.setEmployeeName(rs.getString(5));
				emp.setEmployeeDesg(rs.getString(6));
				
				cat.setCategoryName(rs.getString(7));
				city.setName(rs.getString(8));
				
				emp.setEmployeeCategory(cat);
				emp.setEmployeeCity(city);
				
				bean.setEmp(emp);
				bean.setStatus(rs.getString(11));
				bean.setPrimaryProjects(rs.getString(12));
				
				resource.setProjectFrom(rs.getDate(13));
				bean.setResource(resource);
				
				bean.setSecondaryProjects(rs.getString(14));
				reportList.add(bean);
				
			}
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportList;
	}

	@Override
	public List<Resourceallocations> getEmpBenchPeriodReport(int empId, Date startDate, Date endDate) {
		CallableStatement stmt = null;
		int first = 0;
		ResultSet rs = null;
		int found =0;
		Resourceallocations resource = null;
		List<Resourceallocations> resList=new ArrayList<>();
		try {
			stmt = getSession().connection().prepareCall("{call GETEMPBENCHREPORT(?,?,?)}");
			stmt.setInt("empId", empId);
			stmt.setDate("fromDate", new java.sql.Date (startDate.getTime()));
			stmt.setDate("endDate", new java.sql.Date (endDate.getTime()));
			stmt.execute();
			rs = stmt.getResultSet();
			while (rs.next()) {
				resource = new Resourceallocations();
				resource.setResId(rs.getInt(1));
				System.out.println("resid ="+rs.getInt(1));

				resource.setEmployeeId(rs.getInt(2));
				resource.setProjectId(rs.getInt(3));
				resource.setAllocation(rs.getString(4));
				resource.setProjectFrom(rs.getDate(5));
				System.out.println("rs.getDate(6) ="+rs.getDate(6));
				if (rs.getDate(6) != null) {
					resource.setProjectTo(rs.getDate(6));
				}
				
				else
				{
					found++;
					System.out.println("found ="+found);

			/*		if(found==1) {
						System.out.println("adding the resource to list ");
						resList.add(resource); }*/
				}
				//if(found==0)
				resList.add(resource);
				first++;
				}
				
			//System.out.println("resList="+resList);
			
			
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resList;
	}

}
