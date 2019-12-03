package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Route;
import com.itheima.domain.User;

public interface IndexDao {

	List<Object> search(String content) throws SQLException;

	List<Route> getRoute(String rname, int startIndex, int pageSize) throws SQLException;

	int getPageSize(String rname) throws SQLException;

	int getPageSize(String rname2, String startPrice, String endPrice) throws SQLException;

	List<Route> getFavoriterank(String rname2, String startPrice, String endPrice, int startIndex, int pageSize)
			throws SQLException;

	Route getRouteDetail(String rid) throws SQLException;

	public User getUser(String username) throws SQLException;

	public int getCount() throws SQLException;

	public List<Route> getRouteByRid( int startIndex, int pageSize) throws SQLException;

}
