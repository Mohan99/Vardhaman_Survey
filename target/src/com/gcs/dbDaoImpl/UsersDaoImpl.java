package com.gcs.dbDaoImpl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gcs.constant.ConstantVariables;

import com.gcs.db.businessDao.Users;
import com.gcs.dbDao.UsersDao;

import com.gcs.responseDao.Response;

@Repository("usersDao")
@Transactional
public class UsersDaoImpl extends HibernateDaoSupport implements UsersDao {

	@Transactional
	public Response validateLogin(String email, String password) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Users> list = getHibernateTemplate()
				.find("from Users where isActive=true and email = '" + email + "' and loginPassword='" + password + "'");
		/*
		 * List list=null;s
		 * System.out.println("sessionFactory:::"+sessionFactory); Session
		 * session = sessionFactory.getCurrentSession(); Query query = session.
		 * createQuery("from Users as u where u.email=? and u.loginPassword=?");
		 * query.setParameter(0, email); query.setParameter(1, password); list =
		 * query.list();
		 */ Response response = new Response();
		if (list.size() > 0) {
			System.out.println("User Exists::" + list);
			response.setStatusCode(ConstantVariables.SC0);
			response.setStatusMessage(ConstantVariables.TTRMSGSUCESS);

		} else {
			System.out.println("User doesn't Exists::" + list);
			response.setStatusCode(ConstantVariables.SC1);
			response.setStatusMessage(ConstantVariables.TTRMSGFAIL);
		}

		return response;

	}

	@Override
	public void createUsers(Users u) {
		// TODO Auto-generated method stub

	}

	@Override
	public Response validateUsers(String userName, Integer userId, String loginName, String loginPassword, String email,
			boolean active) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(Users userObj) {
		getHibernateTemplate().saveOrUpdate(userObj);
		getSession().flush();
		// TODO Auto-generated method stub

	}

	@Override
	public List<Users> getListUsers() {
		@SuppressWarnings("unchecked")
		List<Users> usersList = getHibernateTemplate().find("from Users where isActive=true");
		return usersList;
	}

	@Override
	public Users getUsersData(Integer userId) {
		@SuppressWarnings("unchecked")
		List<Users> usersList = getHibernateTemplate().find("from Users a where userId like '%" + userId + "%'");
		if (usersList != null && usersList.get(0) != null)
			return usersList.get(0);

		return null;
	}

	@Override
	public void delete(Integer userId, boolean isUser) {
		if (isUser) {
			/*
			 * Users users=null;
			 * 
			 * @SuppressWarnings("unchecked") List<Users> list
			 * =getHibernateTemplate().find("from Users a where userId like '%"
			 * +userId+"%'"); if(list!=null && list.get(0)!=null) users=
			 * list.get(0); if(users!=null)
			 * getHibernateTemplate().delete(users); } else { Users users; users
			 * = (Users)getHibernateTemplate().load(Users.class,userId);
			 * getHibernateTemplate().delete(users); }
			 * getHibernateTemplate().flush();
			 */
			int query =  getHibernateTemplate().bulkUpdate("update Users set isActive=false where userId= "+userId);
			
		}
	}

	@Override
	public void insert(List<Users> usersList) {
		try {
			for (int i = 0; i < usersList.size(); i++) {
				Users users = usersList.get(i);
				getHibernateTemplate().saveOrUpdate(users);
				getSession().flush();
			}

			// getSession().flush();
		} catch (Exception e) {
			System.out.print(e);
			// getHibernateTemplate()afterPropertiesSet();.rollback();
		}

	}

	@Override
	public long usersCount() {
		long count = ((long) getSession().createQuery("select count(*) from Users").uniqueResult());

		return count;
	}
	/*
	 * public void deleteAll(Users users) { String sql =
	 * "DELETE FROM users WHERE userID=?"; try {
	 * 
	 * Connection conn = getConnection();
	 * 
	 * PreparedStatement ps = conn.prepareStatement(sql);
	 * 
	 * ps.setInt(1, users.getUserId());
	 * 
	 * ps.executeUpdate();
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * } }
	 * 
	 * private Connection getConnection() { // TODO Auto-generated method stub
	 * return null; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public void removeUser(Integer userId) {
		// TODO Auto-generated method stub
		/*
		 * List<Users> usersList
		 * =getHibernateTemplate().find("from Users a where userId like '%"
		 * +userId+"%'"); //List<Users> usersList; usersList =
		 * (List<Users>)getHibernateTemplate().load(Users.class,userId); if
		 * (null !=usersList ) { getHibernateTemplate().delete(usersList); }
		 * getHibernateTemplate().flush(); }
		 * 
		 * if(isUser) { Users users=null;
		 * 
		 * @SuppressWarnings("unchecked") List<Users> list
		 * =getHibernateTemplate().find("from Users a where userId like '%"
		 * +userId+"%'"); if(list!=null && list.get(0)!=null) users=
		 * list.get(0); if(users!=null) getHibernateTemplate().delete(users); }
		 * else { Users users; users =
		 * (Users)getHibernateTemplate().load(Users.class,userId);
		 * getHibernateTemplate().delete(users); }
		 * getHibernateTemplate().flush(); }
		 */
		// Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unused")
		List<Users> list = this.getHibernateTemplate().find("from Users a where userId like '%\"+userId+\"%'");
		Users users = (Users) getHibernateTemplate().load(Users.class, new Integer(userId));
		if (null != users) {
			getHibernateTemplate().delete(users);
		}
		/*
		 * System.out.println("Person deleted successfully, person details="+p);
		 */
	}

	@Override
	public String userName() {
		return null;
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public Users getUsersData(String userName) {
	 * 
	 * @SuppressWarnings("unchecked") List<Users> usersList
	 * =getHibernateTemplate().find("from Users a where userName like '%"
	 * +userName+"%'"); if(usersList!=null && usersList.get(0)!=null){ return
	 * usersList.get(0); } return null; }
	 */

	@Override
	public Users getUserData(String email) {
		@SuppressWarnings("unchecked")
		List<Users> usersList = getHibernateTemplate().find("from Users a where email like '%" + email + "%'");
		if (usersList != null && usersList.get(0) != null) {
			return usersList.get(0);
		}
		return null;
	}

}
