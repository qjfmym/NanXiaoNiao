package com.itheima.domain;

import java.util.Date;

public class Order {
	private String oid;
	private int uid;
	private int rid;
	private Date createtime;
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Order(String oid, int uid, int rid, Date createtime) {
		super();
		this.oid = oid;
		this.uid = uid;
		this.rid = rid;
		this.createtime = createtime;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
