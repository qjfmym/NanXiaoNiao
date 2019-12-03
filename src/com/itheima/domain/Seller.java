package com.itheima.domain;

import java.io.Serializable;

/**
 * �̼�ʵ����
 */
public class Seller implements Serializable {
    private int sid;//�̼�id
    private String sname;//�̼�����
    private String consphone;//�̼ҵ绰
    private String address;//�̼ҵ�ַ

    /**
     * �޲ι��췽��
     */
    public Seller(){}

    /**
     * ���췽��
     * @param sid
     * @param sname
     * @param consphone
     * @param address
     */
    public Seller(int sid, String sname, String consphone, String address) {
        this.sid = sid;
        this.sname = sname;
        this.consphone = consphone;
        this.address = address;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getConsphone() {
        return consphone;
    }

    public void setConsphone(String consphone) {
        this.consphone = consphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
