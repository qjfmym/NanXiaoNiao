package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.Route;
import com.itheima.domain.User;

public interface RouteDao {
	public List<Route> themetravel() throws Exception;

	public List<Route> newtravel() throws Exception;

	public List<Route> popularitytravel() throws Exception;

	public List<Route> inland() throws Exception;

	public List<Route> overseas() throws Exception;

	int findRountCountByCid(int cid) throws SQLException;

	List<Route> findPageBeanList(int startIndex, int pageSize, int cid) throws SQLException;

	public Route findByrid(String rid) throws SQLException;

	public void addOreder(Order order) throws SQLException;

	public User getOrderUser(int uid)throws SQLException;

	public List<Route> Hottravel()throws SQLException;
}
