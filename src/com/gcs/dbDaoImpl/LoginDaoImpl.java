package com.gcs.dbDaoImpl;

import java.util.List;

import org.hibernate.Query;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.constant.ConstantVariables;
import com.gcs.db.businessDao.User;
import com.gcs.dbDao.LoginDao;
import com.gcs.responseDao.Response;

@Repository("loginDao")
@Transactional
public class LoginDaoImpl extends HibernateDaoSupport implements LoginDao {

	/*
	 * private SessionFactory sessionFactory;
	 * 
	 * public void setSessionFactory(SessionFactory sf) { this.sessionFactory = sf;
	 * }
	 */

	@Override
	public Response validateLogin(String email, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate()
				.find("from User where emailid = '" + email + "' and password='" + password + "'");
		Response response = new Response();
		if (list.size() > 0) {

			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {

			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response changePassword(Integer userId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		List<User> list = getHibernateTemplate()
				.find("from User a where id = '" + userId + "' and password='" + oldPassword + "'");
		Response response = new Response();
		if (list != null && list.size() > 0) {
			Query q = getSession().createQuery("update User set password=:password where id=:Id");
			q.setString("password", newPassword);
			q.setInteger("Id", userId);
			int x = q.executeUpdate();
			if (x == -1) {
				response.setStatusCode(ConstantVariables.SC1);
				response.setStatusMessage("password not updated");
			} else {
				response.setStatusCode(ConstantVariables.SC0);
				response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);
			}

		} else {
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage("old password not matched");
		}
		return response;
	}

	@Override
	public User getUserData(String email) {
		List<User> list = getHibernateTemplate().find("from User a where emailid = '" + email + "'");
		if (list.size() != 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public void saveOrupdateUser(User user) {
		getHibernateTemplate().saveOrUpdate(user);
	}

}
