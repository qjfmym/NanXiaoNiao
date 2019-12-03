package com.itheima.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.IndexDao;
import com.itheima.domain.PageBean;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.service.IndexService;
import com.itheima.utils.BeanFactory;

public class IndexServiceImpl implements IndexService {

	public List<Object> search(String content) throws SQLException {
		IndexDao dao = (IndexDao)BeanFactory.getBean("IndexDao");
		List<Object> list = dao.search(content);
		return list;
		
	}

	public int getPageSize(String rname) throws SQLException {
		IndexDao dao = (IndexDao)BeanFactory.getBean("IndexDao");
		int pageSize = dao.getPageSize(rname);
		return pageSize;
	}

	public PageBean<Route> getRoute(String rname, int curPage, int pageSize) throws SQLException {
		IndexDao dao = (IndexDao)BeanFactory.getBean("IndexDao");
		
		PageBean<Route> pageBean = new PageBean<Route>();
		int count = dao.getPageSize(rname);
		pageBean.setCurPage(curPage);
		pageBean.setCount(count);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalPage(pageBean.getTotalPage());
		if(curPage > pageBean.getTotalPage()) {
			pageBean.setCurPage(1);
		}
		List<Route> list = dao.getRoute(rname,pageBean.getStartIndex(),pageSize);
		pageBean.setData(list);
		return pageBean;
	}

	public PageBean<Route> getFavoriterank(String rname2, String startPrice, String endPrice, int curPage, int pageSize) throws SQLException {
		IndexDao dao = (IndexDao)BeanFactory.getBean("IndexDao");
		
		PageBean<Route> pageBean = new PageBean<Route>();
		int count = dao.getPageSize(rname2,startPrice,endPrice);
		pageBean.setCurPage(curPage);
		pageBean.setCount(count);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalPage(pageBean.getTotalPage());
		if(curPage > pageBean.getTotalPage()) {
			pageBean.setCurPage(1);
		}
		List<Route> list = dao.getFavoriterank(rname2,startPrice,endPrice,pageBean.getStartIndex(),pageSize);
		System.out.println(list.size());
		pageBean.setData(list);
		return pageBean;
		
	}

	public Route getRouteDetail(String rid) throws SQLException {
		IndexDao dao = (IndexDao)BeanFactory.getBean("IndexDao");
		
		return dao.getRouteDetail(rid);
	}
	// service层,无需改动,需要实现indexService接口
	public PageBean<Route> getFavoriteByUser(int curPage, int pageSize) throws SQLException {
		IndexDao dao = (IndexDao)BeanFactory.getBean("IndexDao");
		PageBean<Route> pageBean = new PageBean<Route>();
		pageBean.setCurPage(curPage);
		pageBean.setPageSize(pageSize);
		pageBean.setCount(dao.getCount());
		
		List<Route> route = dao.getRouteByRid(pageBean.getStartIndex(),pageSize);			
		pageBean.setTotalPage(pageBean.getTotalPage());
		if(curPage > pageBean.getTotalPage()) {
			pageBean.setCurPage(1);
		}
		pageBean.setData(route);
		return pageBean;
		
	}

}
