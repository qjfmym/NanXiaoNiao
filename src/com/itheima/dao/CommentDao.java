package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Comment;
import com.itheima.domain.CommentAnduser;

public interface CommentDao {

	void addComment(Comment commentObj) throws Exception;

	List<CommentAnduser> findAllComment(int rid)throws Exception;

	int getCountComment(int rid) throws Exception;

	List<CommentAnduser> findPageDate(int indexNumber, int pageSize, int rid)throws Exception;

}
