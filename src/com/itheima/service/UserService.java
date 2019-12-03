package com.itheima.service;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface UserService {

	User login(String username, String password) throws Exception;

	User active(String code) throws Exception;
	void register(User user) throws SQLException;

	User findByUsername(String username)  throws SQLException;

	User findByTelephone(String telephone)  throws SQLException;

	User findByEmail(String email)  throws SQLException;
}
