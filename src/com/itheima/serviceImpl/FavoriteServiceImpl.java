package com.itheima.serviceImpl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itheima.dao.FavoriteDao;
import com.itheima.domain.Category;
import com.itheima.domain.ResultInfo;
import com.itheima.domain.Route;
import com.itheima.domain.RouteImg;
import com.itheima.domain.Seller;
import com.itheima.service.FavoriteService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.C3P0Utils;

public class FavoriteServiceImpl implements FavoriteService {

	public ResultInfo findRouteByRid(int rid){
		
		//调用dao层,创建一个ResultInfo实体
		 ResultInfo result = new ResultInfo();
		try {
			//根据rid获取Route对象,List<RouteImg> routeImgList
			FavoriteDao dao = (FavoriteDao) BeanFactory.getBean("FavoriteDao");
			Route data = dao.findRouteByRid(rid);
			List<RouteImg> list = dao.findMaplistByRid(rid);
			data.setRouteImgList(list);
			//根据data得到cid,sid;
			int cid = data.getCid();
			int sid = data.getSid();
			//根据cid查询旅游路线分类,根据sid查询所属商家
			Category category = dao.findCategoryByCid(cid);
			Seller seller = dao.findSellerBySid(sid);
			//将category和seller封装到data中
			data.setCategory(category);
			data.setSeller(seller);
			//封装相应数据到ResultInfo实体中并返回
			result.setData(data);
			result.setFlag(true);
		} catch (SQLException e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
		} 
		return result;
	}

	public ResultInfo isFavoriteByRid(int rid,int uid) {
		ResultInfo result = new ResultInfo();
		try {
			FavoriteDao dao = (FavoriteDao) BeanFactory.getBean("FavoriteDao");
			int count = dao.isFavoriteByRid(rid,uid);
			if(count==0){
				result.setFlag(true);
				result.setData(false);
			}else{
				result.setFlag(true);
				result.setData(true);
			}
		} catch (SQLException e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
		}
		return result ;
	}

	public ResultInfo addFavorite(int rid, int uid) {
		ResultInfo result = new ResultInfo();
		try {
			C3P0Utils.startTransaction();
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dt = format.format(date);
			FavoriteDao dao = (FavoriteDao) BeanFactory.getBean("FavoriteDao");
			dao.addFavorite(rid,dt,uid);
			dao.addCount(rid);
			int count = dao.getCount(rid);
			result.setFlag(true);
			result.setData(count);
			C3P0Utils.commitAndClose();
		} catch (SQLException e) {
			result.setFlag(false);
			result.setErrorMsg(e.getMessage());
			C3P0Utils.rollbackAndClose();
		}
		return result;
	}


}
