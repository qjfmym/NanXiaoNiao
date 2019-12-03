package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.Category;
import com.itheima.domain.Route;
import com.itheima.domain.RouteImg;
import com.itheima.domain.Seller;

public interface FavoriteDao {

	Route findRouteByRid(int rid) throws SQLException;

	Category findCategoryByCid(int cid) throws SQLException;

	Seller findSellerBySid(int sid) throws SQLException;

	List<RouteImg> findMaplistByRid(int rid) throws SQLException;

	int isFavoriteByRid(int rid, int uid) throws SQLException;

	void addFavorite(int rid, String dt, int uid) throws SQLException;

	void addCount(int rid) throws SQLException;

	int getCount(int rid) throws SQLException;


}
