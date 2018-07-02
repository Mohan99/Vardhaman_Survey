package com.gcs.dbDaoImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Cities;
import com.gcs.db.businessDao.Countries;
import com.gcs.db.businessDao.Employee;
import com.gcs.db.businessDao.Projects;
import com.gcs.db.businessDao.Resourceallocations;
import com.gcs.db.businessDao.State;
import com.gcs.dbDao.EmployeeDao;
import com.gcs.requestDao.EmpReportDataRequest;

@Transactional
@Repository("employeeDao")
public class EmployeeDaoImpl extends HibernateDaoSupport implements EmployeeDao {

	@Override
	public List<Employee> getInActiveEmpList() {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee where status=0");
		System.out.println("Employee Data...." + list);
		return list;
	}

	@Override
	public Employee getEmployeeData(String empID) {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee a where empId like '%" + empID + "%'");
		if (list != null && list.get(0) != null) {
			System.out.println("Emplyee By Id.........." + list.get(0));
			return list.get(0);
		}

		return null;
	}

	@Override
	public void delete(String id, boolean isEmployee) {
		if (isEmployee) {
			/*
			 * Employee emp=null;
			 * 
			 * @SuppressWarnings("unchecked") List<Employee> list
			 * =getHibernateTemplate().find("from Employee a where empId like '%"+id+"%'");
			 * if(list!=null && list.get(0)!=null) emp= list.get(0); if(emp!=null)
			 * getHibernateTemplate().delete(emp); } else { Employee employee; employee =
			 * (Employee )getHibernateTemplate().load(Employee .class,id);
			 * getHibernateTemplate().delete(employee);
			 */
			int query = getHibernateTemplate().bulkUpdate("update Employee set status=false where empId= " + id);
		}
		getHibernateTemplate().flush();

	}

	/*
	 * @Override public void insert(Employee emp) { try {
	 * 
	 * getHibernateTemplate().saveOrUpdate(emp);
	 * 
	 * int lastId = ((Long)
	 * getHibernateTemplate().getSessionFactory().getCurrentSession().
	 * createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult()).intValue();
	 * System.out.println("lastId....."+lastId);
	 * 
	 * getSession().flush();
	 * 
	 * } catch (Exception e) { System.out.print(e); //
	 * getHibernateTemplate()afterPropertiesSet();.rollback(); } }
	 */

	@Override
	public void insert(List<Employee> empList) {
		try {
			List<Employee> listEmpDB = getEmpList();
			for (int i = 0; i < empList.size(); i++) {
				Employee emp = empList.get(i);
				boolean isOldEmpId = false;
				for (int j = 0; j < listEmpDB.size(); j++) {
					Employee empDB = listEmpDB.get(j);

					if (empDB.getEmployeeId().equalsIgnoreCase(emp.getEmployeeId())) {
						isOldEmpId = true;
						emp.setEmpId(empDB.getEmpId());
						break;
					} else
						isOldEmpId = false;
				}
				if (!isOldEmpId) {
					getHibernateTemplate().save(emp);
				} else {
					getHibernateTemplate().merge(emp);
				}

				// getHibernateTemplate().save(emp);
				// getSession().flush();
				// getSession().getTransaction().commit();
			}

			// getSession().flush();
		} catch (Exception e) {
			System.out.print(e);
			// getHibernateTemplate()afterPropertiesSet();.rollback();
		}

	}

	@Override
	public ResultSet getSwipeData(EmpReportDataRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long empCount() {
		long count = ((long) getSession().createQuery("select count(*) from Employee").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}

	@Override
	public boolean deleteSwipeData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllEmployees() {
		// TODO Auto-generated method stub
		String hql = "delete  from Employee";
		Query query = getSession().createQuery(hql);
		int result = query.executeUpdate();
		System.out.println(result);

	}

	@Override
	public List<Category> getCategoryList() {
		@SuppressWarnings("unchecked")
		List<Category> list = getHibernateTemplate().find("from Category");
		return list;
	}

	@Override
	public List<Countries> getCountryList() {
		@SuppressWarnings("unchecked")
		List<Countries> list = getHibernateTemplate().find("from Countries");
		return list;
	}

	@Override
	public long projectsCount() {
		long count = ((long) getSession().createQuery("select count(*) from Projects").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}

	@Override
	public void saveOrUpdate(Employee emp) {
		getHibernateTemplate().saveOrUpdate(emp);
		getSession().flush();
		// getSession().getTransaction().commit();

	}

	@Override
	public List<State> getSatesList(int countryId) {
		@SuppressWarnings("unchecked")
		List<State> list = getHibernateTemplate().find("from State where countryId=" + countryId);
		return list;
	}

	@Override
	public List<Cities> getCitiesList(int stateId) {
		@SuppressWarnings("unchecked")
		List<Cities> list = getHibernateTemplate().find("from Cities where stateId=" + stateId);
		return list;
	}

	@Override
	public List<State> getStates() {
		@SuppressWarnings("unchecked")
		List<State> list = getHibernateTemplate().find("from State ");
		return list;
	}

	@Override
	public List<Cities> getCities() {
		@SuppressWarnings("unchecked")
		List<Cities> list = getHibernateTemplate().find("from Cities ");
		return list;
	}

	@Override
	public Cities getCityByName(String cityName) {
		List<Cities> list = getHibernateTemplate().find("from Cities where name='" + cityName + "'");
		return list.get(0);
	}

	@Override
	public List<Employee> getActiveEmployeeList() {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee where status=1");
		System.out.println("Employee Data...." + list);
		return list;
	}

	@Override
	public List<Employee> getEmpList() {
		@SuppressWarnings("unchecked")
		List<Employee> list = getHibernateTemplate().find("from Employee");
		System.out.println("Employee Data...." + list);
		return list;
	}

	@Override
	public void updateStatus(String empId, boolean isEmployee) {
		// TODO Auto-generated method stub
		if (isEmployee) {

			int query = getHibernateTemplate().bulkUpdate("update Employee set status=true where empId= " + empId);
		}
		getHibernateTemplate().flush();

	}
	
	@Override
	public Boolean setEndDateByEmpId(Integer empid, Date date) {
		CallableStatement stmt = null;
		
		try {
			
			stmt = getSession().connection().prepareCall("{call SETENDDATEBYEMP_ID(?,?)}");
			stmt.setInt("empid", empid);
			//date=project.getEndDate();
			//System.out.println("getCurrentDate()..."+getCurrentDate());
			stmt.setDate("date", new java.sql.Date(date.getTime()));
			stmt.execute();
			System.out.println("stmt.execute()  "+stmt.execute());
			
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
		return false;
	}
	
}
