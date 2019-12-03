package com.itheima.domain;

import java.util.Date;

public class OrderList {
	private String oid;
	private Date createtime;
	private User user;
	private Route route;
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public OrderList(String oid, Date createtime, User user, Route route) {
		super();
		this.oid = oid;
		this.createtime = createtime;
		this.user = user;
		this.route = route;
	}
	public OrderList() {
		super();
	}
	
	
	
}
