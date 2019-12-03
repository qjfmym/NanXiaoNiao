package com.itheima.domain;


public class Lgcount {
private String  id;
private String   loginDate;
private String  count;
public Lgcount() {
	super();
}
public Lgcount(String id, String loginDate, String count) {
	super();
	this.id = id;
	this.loginDate = loginDate;
	this.count = count;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getLoginDate() {
	return loginDate;
}
public void setLoginDate(String loginDate) {
	this.loginDate = loginDate;
}
public String getCount() {
	return count;
}
public void setCount(String count) {
	this.count = count;
}
}
