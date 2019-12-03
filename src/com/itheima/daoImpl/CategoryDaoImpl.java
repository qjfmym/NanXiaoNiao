package com.itheima.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.C3P0Utils;

public class CategoryDaoImpl implements CategoryDao {

	
	/**
	 * 瀵艰埅鏉℃煡鎵炬墍鏈夊垎绫�
	 */
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from tab_category ";
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

}
