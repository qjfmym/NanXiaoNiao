package com.itheima.service;

import java.sql.SQLException;
import java.util.List;

import com.itheima.domain.CommentList;
import com.itheima.domain.Lgcount;
import com.itheima.domain.OrderList;
import com.itheima.domain.User;

public interface AdminService {

	public List<User> getAllUser() throws SQLException;

	public void delete(String uid) throws SQLException;

	public List<OrderList> getAllOrder() throws Exception;

	public List<CommentList> getAllComment() throws Exception;

	public void deleteComment(String commid) throws Exception;

	public int getCount()throws Exception;

	public int getOrderCount()throws Exception;

	public int getCommentCount()throws Exception;

	public User getUserByuid(String uid)throws Exception;

	public List<Lgcount> getdLgCount(int number)throws Exception;

	public List<User> likesearch(String time1, String time2, String message)throws Exception;
}
