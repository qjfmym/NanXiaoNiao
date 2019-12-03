package com.itheima.domain;

import java.io.Serializable;

/**
 * �ղ�ʵ����
 */
public class Favorite implements Serializable {
    private Route route;//������·����
    private String date;//�ղ�ʱ��
    private User user;//�����û�

    /**
     * �޲ι��췽��
     */
    public Favorite() {
    }

    /**
     * �вι��췽��
     * @param route
     * @param date
     * @param user
     */
    public Favorite(Route route, String date, User user) {
            this.route = route;
            this.date = date;
            this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
