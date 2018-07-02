package com.gcs.dbDaoImpl;

import java.util.List;


import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Category;
import com.gcs.db.businessDao.Employee;
import com.gcs.dbDao.CategoryDao;

@Transactional
@Repository("categoryDao")
public class CategoryDaoImpl extends HibernateDaoSupport implements CategoryDao   {

	@Override
	public long categoryCount() {
		// TODO Auto-generated method stub
		long count = ((long) getSession().createQuery("select count(*) from Category").uniqueResult());
		 System.out.println("Count........."+count);
		return count;
	}

	@Override
	public List<Category> getCategory() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Category> list =getHibernateTemplate().find("from Category"); 					
		return list;
	}

	@Override
	public void saveOrUpdate(Category category) {
		// TODO Auto-generated method stub
		 getHibernateTemplate().saveOrUpdate(category);
		 getSession().flush();
		
	}
	
		
	@Override
	public Category getCategory(String categoryID) {
		@SuppressWarnings("unchecked")
		List<Category> list = getHibernateTemplate().find("from Category"
				+ " where categoryId like '%" + categoryID + "%'");
		if (list != null && list.get(0) != null) {
			System.out.println("Category By Id.........." + list.get(0));
			return list.get(0);
		}

		return null;
	}	
	
	/*@Override
	public void editCategory(String categoryID) {
		@SuppressWarnings("unchecked")
		List<Category> list = getHibernateTemplate().find("update Category"
				+ " where categoryId like '%" + categoryID + "%'");
		if (list != null && list.get(0) != null) {
			System.out.println("Category By Id.........." + list.get(0));
			return list.get(0);
		}

		return null;
		int query =  getHibernateTemplate().bulkUpdate("update Employee set status=false where categoryId= "+categoryID);
		getHibernateTemplate().flush();
	}	*/

}
