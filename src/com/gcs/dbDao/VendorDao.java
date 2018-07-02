package com.gcs.dbDao;

import java.util.List;

import com.gcs.db.businessDao.Vendor;

public interface VendorDao {

	public void saveOrUpdate(Vendor vendor);
	public List<Vendor> getVendorsList();
	public Vendor getVendorData(String email);
	long vendorCount();
}
