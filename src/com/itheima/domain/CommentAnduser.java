package com.itheima.domain;
/**
 * 封装用户评论的数据
 * @author Jiayutao
 *
 */
import java.util.Date;
public class CommentAnduser {
	private String comment;
	private Date  craeteDdate;
	private String image;
	private String name;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CommentAnduser(String comment, Date craeteDdate, String image, String name) {
		super();
		this.comment = comment;
		this.craeteDdate = craeteDdate;
		this.image = image;
		this.name = name;
	}
	public CommentAnduser() {
		super();
	}
	
}
