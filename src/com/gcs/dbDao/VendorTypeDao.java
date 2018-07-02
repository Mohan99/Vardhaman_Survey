package com.gcs.dbDao;

import java.util.List;

import com.gcs.db.businessDao.Vendor;
import com.gcs.db.businessDao.VendorType;

public interface VendorTypeDao {

	public void saveOrUpdate(VendorType vendorType); 
	public List<VendorType> getVendorTypeList();
	
	public List<Vendor> getVendorList(int vendortype);
	long vendorTypeCount();
}
