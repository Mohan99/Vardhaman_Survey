package com.gcs.dbDao;

import com.gcs.db.businessDao.Users;

public interface UserDao {
	public Users getUserData(String email);
}
