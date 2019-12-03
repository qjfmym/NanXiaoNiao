package com.itheima.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.RouteDao;
import com.itheima.domain.Order;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.Constant;

public class RouteDaoImpl implements RouteDao {

	// 人气
	public List<Route> themetravel() throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rflag=? and isthemetour=?  limit 4";
		return qr.query(sql, new BeanListHandler<Route>(Route.class), Constant.RFLAG_SHANGJIA,
				Constant.ISTHEMETOUR_YES);
	}

	// 最新
	public List<Route> newtravel() throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rflag=? order by count desc limit 4";
		return qr.query(sql, new BeanListHandler<Route>(Route.class), Constant.RFLAG_SHANGJIA);
	}

	// 主题
	public List<Route> popularitytravel() throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rflag=? order by rdate desc limit 4";
		return qr.query(sql, new BeanListHandler<Route>(Route.class), Constant.RFLAG_SHANGJIA);
	}

	// 国内游
	public List<Route> inland() throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rflag=? limit 6;";
		return qr.query(sql, new BeanListHandler<Route>(Route.class), Constant.RFLAG_SHANGJIA);
	}

	// 境外游
	public List<Route> overseas() throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rflag=? and cid=4  limit 6";
		return qr.query(sql, new BeanListHandler<Route>(Route.class), Constant.RFLAG_SHANGJIA);
	}

	/**
	 * 路线的总的数目
	 */
	public int findRountCountByCid(int cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select count(*) from tab_route where cid = ? ";
		Long l = (Long) qr.query(sql, new ScalarHandler(), cid);
		return l.intValue();
	}

	/**
	 * 发现页面的数据
	 */
	public List<Route> findPageBeanList(int startIndex, int pageSize, int cid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where cid = ? limit ?,? ";
		Object[] params = { cid, startIndex, pageSize };
		return qr.query(sql, new BeanListHandler<Route>(Route.class), params);

	}

	public Route findByrid(String rid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rid = ?";
		return qr.query(sql, new BeanHandler<Route>(Route.class), rid);
	}

	public void addOreder(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "insert into tab_order values(?,?,?,?)";
		runner.update(sql, order.getOid(), order.getUid(), order.getRid(), order.getCreatetime());
	}

	public User getOrderUser(int uid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_user where uid=?";
		return runner.query(sql, new BeanHandler<User>(User.class), uid);
	}

	// 热门推荐
	public List<Route> Hottravel() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_route where rflag=? and isthemetour=?  limit 5";
		return qr.query(sql, new BeanListHandler<Route>(Route.class), Constant.RFLAG_SHANGJIA,
				Constant.ISTHEMETOUR_YES);
	}

}
