package com.gcs.dbDao;

import com.gcs.responseDao.Response;

public interface LoginDao {
public Response validateLogin(String email,String password);
//public Response validateeLogin(String email,String password,boolean isActive);

public Response changePassword(Integer userId, String oldPassword,String newPassword);


}
