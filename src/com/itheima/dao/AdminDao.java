package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.CommentList;
import com.itheima.domain.Lgcount;
import com.itheima.domain.Order;
import com.itheima.domain.OrderList;
import com.itheima.domain.User;

public interface AdminDao {

	List<User> getAllUser() throws SQLException;

	void delete(String uid)throws SQLException;

	List<OrderList> getAllOrder()throws  Exception;

	List<CommentList> getAllComment()throws  Exception;

	void deleteComment(String commid)throws  Exception;

	int getCount()throws  Exception;

	int getOrderCount()throws  Exception;

	int getCommentCount()throws  Exception;

	User getUserByuid(String uid)throws  Exception;

	List<Lgcount> getdLgCount(int number)throws  Exception;

	List<User> likesearch(String time1, String time2, String message)throws  Exception;

	

}
