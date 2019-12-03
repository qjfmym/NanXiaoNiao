package com.itheima.domain;

import java.io.Serializable;

/**
 * �û�ʵ����
 */
public class User implements Serializable {
	private int uid;// �û�id
	private String username;// �û������˺�
	private String password;// ����
	private String name;// ��ʵ����
	private String birthday;// ��������
	private String sex;// �л�Ů
	private String telephone;// �ֻ���
	private String email;// ����
	private String status;// ����״̬��Y�����N����δ����
	private String code;// �����루Ҫ��Ψһ��
	private String image;// �û���ͷ��

	/**
	 * �޲ι���
	 */
	public User() {
		super();
	}

	/**
	 * �вι���
	 * 
	 * @param uid
	 * @param username
	 * @param password
	 * @param name
	 * @param birthday
	 * @param sex
	 * @param telephone
	 * @param email
	 * @param status
	 * @param code
	 * @param image
	 */
	public User(int uid, String username, String password, String name, String birthday, String sex, String telephone,
			String email, String status, String code, String image) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
		this.sex = sex;
		this.telephone = telephone;
		this.email = email;
		this.status = status;
		this.code = code;
		this.image = image;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
