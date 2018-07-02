package com.gcs.dbDaoImpl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Vendor;
import com.gcs.dbDao.VendorDao;

@Transactional
@Repository("vendorDao")
public class VendorDaoImpl extends HibernateDaoSupport implements VendorDao {

	@Override
	public void saveOrUpdate(Vendor vendor) {
		getHibernateTemplate().saveOrUpdate(vendor);
		getSession().flush();

		
	}

	@Override
	public List<Vendor> getVendorsList() {
		List<Vendor> list=getHibernateTemplate().find("from Vendor");
		return list;
	}

	@Override
	public Vendor getVendorData(String email) {
		List<Vendor> list=getHibernateTemplate().find("from Vendor where emailId='"+email+"'");
		return list.get(0);
	}
	
	@Override
	public long vendorCount() {
		// TODO Auto-generated method stub
		long count = ((long) getSession().createQuery("select count(*) from Vendor").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}

}
