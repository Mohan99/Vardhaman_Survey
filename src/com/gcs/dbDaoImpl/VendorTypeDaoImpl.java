package com.gcs.dbDaoImpl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.db.businessDao.Vendor;
import com.gcs.db.businessDao.VendorType;
import com.gcs.dbDao.VendorTypeDao;

@Transactional
@Repository("vendorTypeDao")
public class VendorTypeDaoImpl extends HibernateDaoSupport implements VendorTypeDao {

	@Override
	public void saveOrUpdate(VendorType vendorType) {
		getHibernateTemplate().saveOrUpdate(vendorType);
		getSession().flush();

	}

	@Override
	public List<VendorType> getVendorTypeList() {
		List<VendorType> list=getHibernateTemplate().find("from VendorType");
		return list;
	}
	
	@Override
	public List<Vendor> getVendorList(int vendor_type) {
	List<Vendor> list =   getHibernateTemplate().find("from Vendor where vendorType="+vendor_type);
	return list;
	}
	
	@Override
	public long vendorTypeCount() {
		// TODO Auto-generated method stub
		long count = ((long) getSession().createQuery("select count(*) from VendorType").uniqueResult());
		// System.out.println("Count........."+count);
		return count;
	}

}
