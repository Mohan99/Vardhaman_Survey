package com.gcs.dbDao;

import java.util.List;


import com.gcs.db.businessDao.Users;
import com.gcs.responseDao.Response;

public interface UsersDao {
	
//public Response validateUsers(Users user);
	public void createUsers(Users u);
	public List<Users> getListUsers();
	public  Response validateUsers(String userName, Integer userId, String loginName, String loginPassword,
			String email, boolean active);
	public void saveOrUpdate(Users userObj); 
	public long usersCount();
	public String userName();
	public Users getUsersData(Integer userId);
	//public Users getUsersData(String userName);
	public Users getUserData(String email);
	public void delete(Integer userId, boolean isUser);
	//public  void saveOrUpdate(UsersList gcsManagerList);
	public void insert(List<Users> usersList);
	//public void deleteAll(Integer userId);
	public void removeUser(Integer userId);
}
