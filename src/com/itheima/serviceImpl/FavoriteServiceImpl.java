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
		
		//����dao��,����һ��ResultInfoʵ��
		 ResultInfo result = new ResultInfo();
		try {
			//����rid��ȡRoute����,List<RouteImg> routeImgList
			FavoriteDao dao = (FavoriteDao) BeanFactory.getBean("FavoriteDao");
			Route data = dao.findRouteByRid(rid);
			List<RouteImg> list = dao.findMaplistByRid(rid);
			data.setRouteImgList(list);
			//����data�õ�cid,sid;
			int cid = data.getCid();
			int sid = data.getSid();
			//����cid��ѯ����·�߷���,����sid��ѯ�����̼�
			Category category = dao.findCategoryByCid(cid);
			Seller seller = dao.findSellerBySid(sid);
			//��category��seller��װ��data��
			data.setCategory(category);
			data.setSeller(seller);
			//��װ��Ӧ���ݵ�ResultInfoʵ���в�����
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
