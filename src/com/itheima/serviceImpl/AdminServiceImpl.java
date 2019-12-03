package com.itheima.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.AdminDao;
import com.itheima.domain.CommentList;
import com.itheima.domain.Lgcount;
import com.itheima.domain.Order;
import com.itheima.domain.OrderList;
import com.itheima.domain.User;
import com.itheima.service.AdminService;
import com.itheima.utils.BeanFactory;
@SuppressWarnings("all")
public class AdminServiceImpl implements AdminService {
	private AdminDao dao = (AdminDao) BeanFactory.getBean("AdminDao");


	public List<User> getAllUser() throws SQLException{
		return dao.getAllUser();
	}


	public void delete(String uid) throws SQLException {
      dao.delete(uid);		
	}

	
	public List<OrderList> getAllOrder() throws Exception {
		
		return dao.getAllOrder();
	}

	public List<CommentList> getAllComment() throws Exception {
		return dao.getAllComment();
	}

	
	public void deleteComment(String commid) throws Exception {
		 dao.deleteComment(commid);
	}


	public int getCount() throws Exception {
		return dao.getCount();
	}


	public int getOrderCount() throws Exception {
		return dao.getOrderCount();
	}


	public int getCommentCount() throws Exception {
		return dao.getCommentCount();
	}

	
	public User getUserByuid(String uid) throws Exception {
		return dao.getUserByuid(uid);
	}

	public List<Lgcount> getdLgCount(int number) throws Exception {
		
		return dao.getdLgCount(number);
	}

	public List<User> likesearch(String time1, String time2, String message) throws Exception {
		return dao.likesearch( time1,  time2,  message);
	}

}
