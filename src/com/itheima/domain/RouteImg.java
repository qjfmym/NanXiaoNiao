package com.itheima.domain;

import java.io.Serializable;

/**
 * ������·ͼƬʵ����
 */
public class RouteImg implements Serializable {
    private int rgid;//��ƷͼƬid
    private int rid;//������Ʒid
    private String bigPic;//������Ʒ��ͼ
    private String smallPic;//������ƷСͼ

    /**
     * �޲ι��췽��
     */
    public RouteImg() {
    }

    /**
     * �вι��췽��
     * @param rgid
     * @param rid
     * @param bigPic
     * @param smallPic
     */
    public RouteImg(int rgid, int rid, String bigPic, String smallPic) {
        this.rgid = rgid;
        this.rid = rid;
        this.bigPic = bigPic;
        this.smallPic = smallPic;
    }

    public int getRgid() {
        return rgid;
    }

    public void setRgid(int rgid) {
        this.rgid = rgid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }
}
