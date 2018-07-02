package com.gcs.dbDao;

import com.gcs.db.businessDao.User;
import com.gcs.responseDao.Response;

public interface LoginDao {
public Response validateLogin(String email,String password);

public Response changePassword(Integer userId, String oldPassword,String newPassword);

public User getUserData(String email);
public void saveOrupdateUser(User user);

}
