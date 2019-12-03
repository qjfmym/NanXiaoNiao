package com.itheima.serviceImpl;

import java.util.List;

import com.itheima.dao.CommentDao;
import com.itheima.domain.Comment;
import com.itheima.domain.CommentAnduser;
import com.itheima.domain.PageBean;
import com.itheima.service.CommentService;
import com.itheima.utils.BeanFactory;

public class CommentServiceImpl implements CommentService {

	public void addComment(Comment commentObj) throws Exception {
		CommentDao dao = (CommentDao) BeanFactory.getBean("CommentDao");
		dao.addComment(commentObj);
	}

	public List<CommentAnduser> findAllComment(int rid) throws Exception {
		CommentDao dao = (CommentDao) BeanFactory.getBean("CommentDao");
		return dao.findAllComment(rid);
	}

	public PageBean<CommentAnduser> findPageAllComment(int pageNumber, int pageSize, int rid) throws Exception {
		CommentDao dao = (CommentDao) BeanFactory.getBean("CommentDao");
		PageBean<CommentAnduser> pageBean = new PageBean<CommentAnduser>();
		int totalCount  =  dao.getCountComment(rid);
		pageBean.setCount(totalCount);
		pageBean.setCurPage(pageNumber);
		pageBean.setPageSize(pageSize);
		int indexNumber = pageBean.getStartIndex();
		List<CommentAnduser> list = dao.findPageDate(indexNumber,pageSize,rid);
		pageBean.setData(list);
		return pageBean;
	}

}
