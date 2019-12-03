package com.itheima.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.itheima.dao.RouteDao;
import com.itheima.domain.Order;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.RouteService;
import com.itheima.utils.BeanFactory;

public class RouteServiceImpl implements RouteService {
	/*
	 * 封装pageBean分页展示业务逻辑处理
	 */
	public PageBean<Route> findRouteByPage(int cid, int curPage) throws SQLException {
		// 定义pageSize
		int pageSize = 8;
		// 调用dao获取总条数
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		// 查询到数据count
		int count = dao.findRountCountByCid(cid);

		// PageBean<Route> pb = new PageBean<Route>();
		// 获取索要展示页数的列表项集合
		int startIndex = (curPage - 1) * pageSize;
		List<Route> data = dao.findPageBeanList(startIndex, pageSize, cid);

		PageBean<Route> pb = new PageBean<Route>();
		pb.setData(data);
		pb.setCount(count);
		pb.setCurPage(curPage);
		pb.setPageSize(pageSize);

		return pb;
	}

	// 人气
	public String popularitytravel() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.popularitytravel();
		return JSON.toJSONString(list);
	}

	// 最新
	public String newtravel() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.newtravel();
		return JSON.toJSONString(list);
	}

	// 主题
	public String themetravel() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.themetravel();
		return JSON.toJSONString(list);
	}

	// 国内游
	public String inland() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.inland();
		return JSON.toJSONString(list);
	}

	// 境外游
	public String overseas() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.overseas();
		return JSON.toJSONString(list);
	}

	/**
	 * 通过id查找路线
	 */
	public Route findByrid(String rid) throws SQLException {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		return dao.findByrid(rid);
	}

	/**
	 * 添加订单
	 */
	public void addOreder(Order order) throws SQLException {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		dao.addOreder(order);
	}

	public User getOrderUser(int uid) throws SQLException {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		return dao.getOrderUser(uid);
	}

		//热门推荐

		public String Hottravel() throws SQLException {
			RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
			List<Route> list = dao.Hottravel();
			return JSON.toJSONString(list);
			
		}

}
