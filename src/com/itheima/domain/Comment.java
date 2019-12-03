package com.itheima.domain;

import java.util.Date;

/**
 * 用户评论表
 * @author 
 *
 */
public class Comment {
	// 评论的id
	private int id;
	// 路线的id
	private int rid;
	// 用户的id
	private int uid;
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
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
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
	public Comment(int id, int rid, int uid, String comment, Date craeteDdate) {
		super();
		this.id = id;
		this.rid = rid;
		this.uid = uid;
		this.comment = comment;
		this.craeteDdate = craeteDdate;
	}
	public Comment() {
		super();
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", rid=" + rid + ", uid=" + uid + ", comment=" + comment + ", craeteDdate="
				+ craeteDdate + "]";
	}
	
	
}
