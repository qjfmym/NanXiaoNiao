package com.itheima.service;

import java.util.List;

import com.itheima.domain.Comment;
import com.itheima.domain.CommentAnduser;
import com.itheima.domain.PageBean;

public interface CommentService {

	void addComment(Comment commentObj) throws Exception;

	List<CommentAnduser> findAllComment(int rid)throws Exception;

	PageBean<CommentAnduser> findPageAllComment(int pageNumber, int pageSize, int rid) throws Exception;
}
