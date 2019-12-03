package com.itheima.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.AdminDao;
import com.itheima.domain.CommentList;
import com.itheima.domain.Lgcount;
import com.itheima.domain.OrderList;
import com.itheima.domain.Route;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;

public class AdminDaoImpl implements AdminDao {
	private QueryRunner runner = new QueryRunner(C3P0Utils.getDataSource());

	
	public List<User> getAllUser() throws SQLException {
		String sql = "select * from tab_user";
		List<User> query = runner.query(sql, new BeanListHandler<User>(User.class));
		return query;
	}

	public void delete(String uid) throws SQLException {
		String sql = "delete from tab_user where uid= ?";
		runner.update(sql, uid);
	}

	public List<OrderList> getAllOrder() throws Exception {
		String sql = "select * from tab_route,tab_user,tab_order where tab_route.rid = tab_order.rid and tab_order.uid = tab_user.uid";
		List<Map<String, Object>> query = runner.query(sql, new MapListHandler());
		List<OrderList> list = new ArrayList<OrderList>();
		for (Map<String, Object> map : query) {
			OrderList orderList = new OrderList();
			User user = new User();
			Route route = new Route();
			BeanUtils.populate(user, map);
			BeanUtils.populate(route, map);
			orderList.setUser(user);
			orderList.setRoute(route);
			BeanUtils.populate(orderList, map);
			list.add(orderList);
		}
		return list;
	}

	public List<CommentList> getAllComment() throws Exception {
		String sql = "select * from tab_user ,tab_comment,tab_route where tab_user.uid = tab_comment.uid and tab_comment.rid = tab_route.rid";
		List<Map<String, Object>> query = runner.query(sql, new MapListHandler());
		List<CommentList> commentList = new ArrayList<CommentList>();
		for (Map<String, Object> map : query) {
	      CommentList commen = new CommentList();
	      User user= new User();
	      Route route = new Route();
	      BeanUtils.populate(user, map);
	      BeanUtils.populate(route, map);
	      commen.setUser(user);
	      commen.setRoute(route);
	     
	      BeanUtils.populate(commen, map);
	      commentList.add(commen);
        }
		return commentList;
	}

	public void deleteComment(String commid) throws Exception {
      	String sql = "delete from tab_comment where id= ?";	
      	runner.update(sql, commid);
	}

	public int getCount() throws Exception {
		String sql = "select count(*) from  tab_user";
		Long query = (Long) runner.query(sql, new ScalarHandler());
		return query.intValue();
	}

	
	public int getOrderCount() throws Exception {
		String sql = "select count(*) from  tab_order";
		Long query = (Long) runner.query(sql, new ScalarHandler());
	    return query.intValue();
	}

	public int getCommentCount() throws Exception {
		String sql = "select count(*) from  tab_comment";
		Long query = (Long) runner.query(sql, new ScalarHandler());
	    return query.intValue();
	}

	
	public User getUserByuid(String uid) throws Exception {
		String sql = "select *  from  tab_user where uid=?";
		User query = runner.query(sql,new BeanHandler<User>(User.class) ,uid);
		return query;
	}

	
	public List<Lgcount> getdLgCount(int number) throws Exception {
		String sql = "select *  from  tab_lgcount limit ?,?";
		List<Lgcount> query = runner.query(sql, new BeanListHandler<Lgcount>(Lgcount.class),0,number);
		return query;
	}
	


	public List<User> likesearch(String time1, String time2, String message) throws Exception {
		       // 拼接sql
				String sql = "select * from tab_user where 1=1 AND birthday BETWEEN ? AND  ? ";
				// 拼接参数
				List list = new ArrayList();
				if(time1==null||time1.trim()=="") {
					list.add("2000-00-00");
				}else {
					list.add(time1);
				}
				if(time2==null||time2.trim()=="") {
					list.add("2100-00-00");
				}else 
				{
					list.add(time2);
				}
				//判断商品名称是否为空
				if(message!=""){
					sql += "and name like ? ";
					list.add("%"+message+"%");
				}
				// 将list转数组
				Object[] params = list.toArray();
				return runner.query(sql, new BeanListHandler<User>(User.class),params);
	}

}
