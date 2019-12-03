package com.itheima.daoImpl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.Constant;


public class UserDaoImpl implements UserDao{

	//用户登录
	public User login(String username, String password) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from tab_user where username=? and password=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class),username,password);
		return user;
	}

	//根据激活码查询用户信息
	public User findByCode(String code) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from tab_user where code = ? ";
		return qr.query(sql, new BeanHandler<User>(User.class),code);
	}
	
	//账户激活
	public void active(User user) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		long currentTimeMillis = System.currentTimeMillis();
		String sql="update tab_user set status=?,code=? where uid=?";
		String param = null;
		if("Y".equals(user.getStatus())) {
			 param =Constant.USER_NOT_ACTIVE;
		}else {
			 param =Constant.USER_YES_ACTIVE;
		}
		
		qr.update(sql,param,currentTimeMillis+"",user.getUid()); 
	}
	public void register(User user) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into tab_user values(?,?,?,?,?,?,?,?,?,?,?) ";
		Object[] params = {
				null,user.getUsername(),user.getPassword(),user.getName(),
				user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),
				user.getStatus(),user.getCode(),user.getImage()
		};
		qr.update(C3P0Utils.getConnection(), sql, params);
	}

	public User findUserByUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_user where username = ? ";
		return qr.query(sql, new BeanHandler<User>(User.class), username);
	}

	public User findUserByEmail(String email) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_user where email = ? ";
		return qr.query(sql, new BeanHandler<User>(User.class), email);
	}


	public User findUserByTel(String telephone) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_user where telephone = ? ";
		return qr.query(sql, new BeanHandler<User>(User.class),telephone);
	}
}
