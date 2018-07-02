package com.gcs.dbDao;

import java.util.List;

import com.gcs.db.businessDao.Category;




public interface CategoryDao {
	
	public List<Category> getCategory();
	public void saveOrUpdate(Category category); 
	public long categoryCount();
	public Category getCategory(String categoryID);
	//public void editCategory(String categoryID);
	

}
