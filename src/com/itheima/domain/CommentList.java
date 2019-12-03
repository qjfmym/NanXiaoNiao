package com.itheima.domain;

import java.util.Date;

public class CommentList {
	// 评论的id
	private int id;
	// 路线
	private Route route;
	// 用户
	private User user;
	// 评论的内容
	private String comment;
	// 评论发布的日期
	private Date craeteDdate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCraeteDdate() {
		return craeteDdate;
	}
	public void setCraeteDdate(Date craeteDdate) {
		this.craeteDdate = craeteDdate;
	}
	public CommentList(int id, Route route, User user, String comment, Date craeteDdate) {
		super();
		this.id = id;
		this.route = route;
		this.user = user;
		this.comment = comment;
		this.craeteDdate = craeteDdate;
	}
	public CommentList() {
		super();
	}
	@Override
	public String toString() {
		return "CommentList [id=" + id + ", route=" + route + ", user=" + user + ", comment=" + comment
				+ ", craeteDdate=" + craeteDdate + "]";
	}
	

}
