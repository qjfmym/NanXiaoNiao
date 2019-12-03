package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {
	//ThreadLocal��������ǰ�̸߳�ֵ
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	//�������ӳض���
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource("itcast");
	//�ṩ��ȡ���ӳصķ���
	public static ComboPooledDataSource getDataSource(){
		return dataSource;
	}


	//�ṩ��ȡ���ӵķ���
	public static Connection getConnection() throws SQLException{
		Connection conn = tl.get();
		if(conn==null){
			conn = dataSource.getConnection();
			tl.set(conn);
		}
		return conn;
	}

	//�����,�ύ����,�黹����
	public static void commitAndClose(){
		Connection conn;
		try {
			//�ύ����,�黹����
			conn = getConnection();
			DbUtils.commitAndCloseQuietly(conn);
			//�Ӵ���
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//�����,�ع�����,�黹����
	public static void rollbackAndClose(){
		try {
			//�ع�����,�黹����
			Connection conn = getConnection();
			DbUtils.rollbackAndCloseQuietly(conn);
			//�����
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//��������
	public static void startTransaction() throws SQLException{
		Connection conn = getConnection();
		conn.setAutoCommit(false);
	}

}
