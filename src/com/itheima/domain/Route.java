package com.itheima.domain;

import java.io.Serializable;
import java.util.List;

/**
 * ������·��Ʒʵ����
 */
public class Route implements Serializable {

    private int rid;//��·id������
    private String rname;//��·���ƣ�����
    private double price;//�۸񣬱���
    private String routeIntroduce;//��·����
    private String rflag;   //�Ƿ��ϼܣ����䣬0����û���ϼܣ�1�������ϼ�
    private String rdate;   //�ϼ�ʱ��
    private String isThemeTour;//�Ƿ��������Σ����䣬0�����ǣ�1������
    private int count;//�ղ�����
    private int cid;//�������࣬����
    private String rimage;//����ͼ
    private int sid;//�����̼�
    private String sourceId;//ץȡ���ݵ���Դid

    private Category category;//��������
    private Seller seller;//�����̼�
    private List<RouteImg> routeImgList;//��Ʒ����ͼƬ�б�



    /**
     * �޲ι��췽��
     */
    public Route(){}

    /**
     * �вι��췽��
     * @param rid
     * @param rname
     * @param price
     * @param routeIntroduce
     * @param rflag
     * @param rdate
     * @param isThemeTour
     * @param count
     * @param cid
     * @param rimage
     * @param sid
     * @param sourceId
     */
    public Route(int rid, String rname, double price, String routeIntroduce, String rflag, String rdate, String isThemeTour, int count, int cid, String rimage, int sid, String sourceId) {
        this.rid = rid;
        this.rname = rname;
        this.price = price;
        this.routeIntroduce = routeIntroduce;
        this.rflag = rflag;
        this.rdate = rdate;
        this.isThemeTour = isThemeTour;
        this.count = count;
        this.cid = cid;
        this.rimage = rimage;
        this.sid = sid;
        this.sourceId = sourceId;
    }

    public List<RouteImg> getRouteImgList() {
        return routeImgList;
    }

    public void setRouteImgList(List<RouteImg> routeImgList) {
        this.routeImgList = routeImgList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRouteIntroduce() {
        return routeIntroduce;
    }

    public void setRouteIntroduce(String routeIntroduce) {
        this.routeIntroduce = routeIntroduce;
    }

    public String getRflag() {
        return rflag;
    }

    public void setRflag(String rflag) {
        this.rflag = rflag;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getIsThemeTour() {
        return isThemeTour;
    }

    public void setIsThemeTour(String isThemeTour) {
        this.isThemeTour = isThemeTour;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getRimage() {
        return rimage;
    }

    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

	@Override
	public String toString() {
		return "Route [rid=" + rid + ", rname=" + rname + ", price=" + price + ", routeIntroduce=" + routeIntroduce
				+ ", rflag=" + rflag + ", rdate=" + rdate + ", isThemeTour=" + isThemeTour + ", count=" + count
				+ ", cid=" + cid + ", rimage=" + rimage + ", sid=" + sid + ", sourceId=" + sourceId + ", category="
				+ category + ", seller=" + seller + ", routeImgList=" + routeImgList + "]";
	}
    
}
