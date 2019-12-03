package com.itheima.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.PageBean;
import com.itheima.domain.Route;

public interface IndexService {

	List<Object> search(String content) throws SQLException;

	int getPageSize(String rname) throws SQLException;

	PageBean<Route> getRoute(String rname, int curPage, int pageSize) throws SQLException;

	PageBean<Route> getFavoriterank(String rname2, String startPrice, String endPrice, int curPage, int pageSize) throws SQLException;

	Route getRouteDetail(String rid) throws SQLException;
	
	public PageBean<Route> getFavoriteByUser(int curPage, int pageSize) throws SQLException ;
}
