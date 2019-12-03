package com.itheima.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Constant;
import com.itheima.utils.JedisPoolUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

	
	/**
	 * 瀵艰埅鏉℃煡鎵炬墍鏈夊垎绫�
	 */
	public String findAllCategory() throws SQLException {
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		Jedis jedis = JedisPoolUtils.getJedis();
		String jlist = jedis.get(Constant.CATELISTJSON);
		if (jlist == null) {
			List<Category> list = dao.findAllCategory();
			jlist = JSON.toJSONString(list);
			jedis.set(Constant.CATELISTJSON, jlist);
			jedis.close();
		}
		return jlist;
	}

}
