package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	//ThreadLocal用来给当前线程赋值
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	//创建连接池对象
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource("itcast");
	//提供获取连接池的方法
	public static ComboPooledDataSource getDataSource(){
		return dataSource;
	}


	//提供获取连接的方法
	public static Connection getConnection() throws SQLException{
		Connection conn = tl.get();
		if(conn==null){
			conn = dataSource.getConnection();
			tl.set(conn);
		}
		return conn;
	}

	//解除绑定,提交事务,归还连接
	public static void commitAndClose(){
		Connection conn;
		try {
			//提交事务,归还连接
			conn = getConnection();
			DbUtils.commitAndCloseQuietly(conn);
			//接触绑定
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//解除绑定,回滚事务,归还连接
	public static void rollbackAndClose(){
		try {
			//回滚事务,归还连接
			Connection conn = getConnection();
			DbUtils.rollbackAndCloseQuietly(conn);
			//解除绑定
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//开启事务
	public static void startTransaction() throws SQLException{
		Connection conn = getConnection();
		conn.setAutoCommit(false);
	}

}
