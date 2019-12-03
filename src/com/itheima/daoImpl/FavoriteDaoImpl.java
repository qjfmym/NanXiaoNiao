package com.itheima.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.FavoriteDao;
import com.itheima.domain.Category;
import com.itheima.domain.Route;
import com.itheima.domain.RouteImg;
import com.itheima.domain.Seller;
import com.itheima.utils.C3P0Utils;

public class FavoriteDaoImpl implements FavoriteDao {

	public Route findRouteByRid(int rid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rid = ?";
		Route route = runner.query(sql, new BeanHandler<Route>(Route.class),rid);
		return route;
	}

	public Category findCategoryByCid(int cid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_category where cid = ? ";
		int param = cid;
		Category category = runner.query(sql, new BeanHandler<Category>(Category.class), param);
		return category;
	}

	public Seller findSellerBySid(int sid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_seller where sid = ? ";
		int param = sid;
		Seller seller = runner.query(sql, new BeanHandler<Seller>(Seller.class), param);
		return seller;
	}

	public List<RouteImg> findMaplistByRid(int rid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route_img where rid = ? ";
		List<RouteImg> list = runner.query(sql, new  BeanListHandler<RouteImg>(RouteImg.class), rid);
		return list;
	}

	public int isFavoriteByRid(int rid, int uid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from tab_favorite where rid = ? and uid = ?";
		return ((Long)runner.query(sql, new ScalarHandler(), rid,uid)).intValue();
	}

	public void addFavorite(int rid,String dt,int uid) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into tab_favorite values(?,?,?)";
		Object[] params = {rid,dt,uid};
		runner.update(C3P0Utils.getConnection(), sql, params);
	}

	public void addCount(int rid) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "update tab_route set count=count+1 where rid = ?";
		runner.update(C3P0Utils.getConnection(), sql,rid);
	}


	public int getCount(int rid) throws SQLException {
		QueryRunner runner = new QueryRunner();
		String sql = "select * from tab_route where rid = ?";
		Route route = runner.query(C3P0Utils.getConnection(), sql, new BeanHandler<Route>(Route.class),rid);
		int count = route.getCount();
		return count;
	}

}
