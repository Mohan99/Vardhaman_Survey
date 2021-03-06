package com.gcs.db.businessDao;
// Generated Mar 17, 2018 12:55:27 PM by Hibernate Tools 5.2.6.Final

import javax.persistence.GeneratedValue;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	@GeneratedValue
	private int id;
	private String name;
	private String username;
	private String password;
	private String emailid;

	public User() {
	}

	public User(String name, String username, String password, String emailid) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.emailid = emailid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailid() {
		return this.emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", emailid="
				+ emailid + "]";
	}

}
