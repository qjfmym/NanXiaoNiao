package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface UserDao {

	User login(String username, String password) throws Exception;

	User findByCode(String code) throws Exception;

	void active(User user) throws Exception;

	public void register(User user) throws SQLException;

	public User findUserByUsername(String username) throws SQLException;

	public User findUserByEmail(String email) throws SQLException;

	public User findUserByTel(String telephone) throws SQLException;
}
