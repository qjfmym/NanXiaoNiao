package com.itheima.service;

import java.sql.SQLException;

import com.itheima.domain.Order;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.domain.User;

public interface RouteService {

	String popularitytravel() throws Exception;

	String newtravel() throws Exception;

	String themetravel() throws Exception;

	String inland() throws Exception;

	String overseas() throws Exception;

	public PageBean<Route> findRouteByPage(int cid,int curPage) throws SQLException ;

	Route findByrid(String rid)throws SQLException;

	void addOreder(Order order)throws SQLException;

	User getOrderUser(int uid) throws SQLException;

	String Hottravel() throws SQLException;

}
