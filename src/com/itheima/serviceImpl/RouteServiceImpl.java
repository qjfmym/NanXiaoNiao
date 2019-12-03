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
	 * ��װpageBean��ҳչʾҵ���߼�����
	 */
	public PageBean<Route> findRouteByPage(int cid, int curPage) throws SQLException {
		// ����pageSize
		int pageSize = 8;
		// ����dao��ȡ������
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		// ��ѯ������count
		int count = dao.findRountCountByCid(cid);

		// PageBean<Route> pb = new PageBean<Route>();
		// ��ȡ��Ҫչʾҳ�����б����
		int startIndex = (curPage - 1) * pageSize;
		List<Route> data = dao.findPageBeanList(startIndex, pageSize, cid);

		PageBean<Route> pb = new PageBean<Route>();
		pb.setData(data);
		pb.setCount(count);
		pb.setCurPage(curPage);
		pb.setPageSize(pageSize);

		return pb;
	}

	// ����
	public String popularitytravel() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.popularitytravel();
		return JSON.toJSONString(list);
	}

	// ����
	public String newtravel() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.newtravel();
		return JSON.toJSONString(list);
	}

	// ����
	public String themetravel() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.themetravel();
		return JSON.toJSONString(list);
	}

	// ������
	public String inland() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.inland();
		return JSON.toJSONString(list);
	}

	// ������
	public String overseas() throws Exception {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		List<Route> list = dao.overseas();
		return JSON.toJSONString(list);
	}

	/**
	 * ͨ��id����·��
	 */
	public Route findByrid(String rid) throws SQLException {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		return dao.findByrid(rid);
	}

	/**
	 * ��Ӷ���
	 */
	public void addOreder(Order order) throws SQLException {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		dao.addOreder(order);
	}

	public User getOrderUser(int uid) throws SQLException {
		RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
		return dao.getOrderUser(uid);
	}

		//�����Ƽ�

		public String Hottravel() throws SQLException {
			RouteDao dao = (RouteDao) BeanFactory.getBean("RouteDao");
			List<Route> list = dao.Hottravel();
			return JSON.toJSONString(list);
			
		}

}
