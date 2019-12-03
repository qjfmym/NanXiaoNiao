package com.itheima.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.IndexDao;
import com.itheima.domain.Category;
import com.itheima.domain.Route;
import com.itheima.domain.Seller;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;

public class IndexDaoImpl implements IndexDao {


	public List<Object> search(String content) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select rname from tab_route where rname like ? ORDER BY rdate DESC limit 5";
		List<Object> list = qr.query(sql, new ColumnListHandler(),"%" + content + "%");
		return list;
	}
	
	

	public List<Route> getRoute(String rname,int startIndex, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rname like ? ORDER BY rdate DESC limit ?,?";
		List<Route> list = qr.query(sql, new BeanListHandler<Route>(Route.class),"%" + rname + "%",startIndex,pageSize);
		return list;
	}

	public int getPageSize(String rname) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from tab_route where rname like ?";
		return ((Long)(qr.query(sql, new ScalarHandler(),"%" + rname + "%"))).intValue();
	}
	public int getPageSize(String rname2, String startPrice, String endPrice) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from tab_route where price BETWEEN ? and ? and count > 0 ";
		List<Object> arrayList = new ArrayList<Object>();
		arrayList.add(startPrice);
		arrayList.add(endPrice);
		if(rname2 != "") {
			sql += "and rname like ? ";
			arrayList.add("%" + rname2 + "%");
		}
		return ((Long)(qr.query(sql, new ScalarHandler(),arrayList.toArray()))).intValue();
	}


	public List<Route> getFavoriterank(String rname2,  String startPrice, String endPrice,int startIndex, int pageSize) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		List<Object> arrayList = new ArrayList<Object>();
		String sql = "select * from tab_route where 1 = 1 and price BETWEEN ? and ? and count > 0 ";
		
		// 判断前台参数是否为空,动态拼接sql
		arrayList.add(startPrice);
		arrayList.add(endPrice);
		if(rname2 != "") {
			sql += "and rname like ? ORDER BY count DESC ";
			arrayList.add("%" + rname2 + "%");
		}
		arrayList.add(startIndex);
		arrayList.add(pageSize);
		Object[] array = arrayList.toArray();
		sql += "limit ?,?";
		List<Route> list = qr.query(sql, new BeanListHandler<Route>(Route.class),array);
		return list;
	}


	public Route getRouteDetail(String rid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rid = ?";
		Route route = qr.query(sql, new BeanHandler<Route>(Route.class),rid);
		String sql1 = "select * from tab_seller where sid = ?";
		Seller seller = qr.query(sql1, new BeanHandler<Seller>(Seller.class),route.getSid());
		String sql2 = "select * from tab_category where cid = ?";
		Category category = qr.query(sql2, new BeanHandler<Category>(Category.class),route.getCid());
		route.setSeller(seller);
		route.setCategory(category);
		return route;
	}

      /**
       * 下列三种方法实现搜索的功能
       */
		// 通过username获取user实体
		public User getUser(String username) throws SQLException {
			QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
			String sql = "select * from tab_user where username = ?";
			User user = qr.query(sql, new BeanHandler<User>(User.class),username);
			return user;
		}

		      // 通过user实体中uid获取pagesize
				public int getCount() throws SQLException {
					QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
					String sql = "select count(*) from tab_route where count > 0";
					return  ((Long)qr.query(sql, new ScalarHandler())).intValue();
				}

				// 获取页面展示旅游项目
				public List<Route> getRouteByRid(int startIndex,int pageSize) throws SQLException {
					QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
					String sql = "select * from tab_route where count > 0 ORDER BY count desc limit ?,?";
					List<Route> query = qr.query(sql, new BeanListHandler<Route>(Route.class),startIndex,pageSize);
					return query;
				}


}
