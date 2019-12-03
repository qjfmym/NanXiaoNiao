package com.itheima.domain;
import java.io.Serializable;
import java.util.List;

/**
 * ��ҳ��
 */
public class PageBean<T> implements Serializable {
    private List<T> data;//��ǰҳ�б�
    private int firstPage=1;//��ҳ
    private int prePage;//��һҳ
    private int curPage;//��ǰҳ
    private int nextPage;//��һҳ
    private int totalPage;//��ҳ��
    private int count;//�ܼ�¼��
    private int pageSize;//ÿҳ������

	public int getStartIndex(){
        return (curPage-1)*pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        prePage =this.curPage-1;
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getNextPage() {
        nextPage = this.curPage+1;
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalPage() {
        totalPage = count%pageSize==0?count/pageSize:count/pageSize+1;
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public PageBean(){}
    public PageBean(List<T> data, int firstPage, int prePage, int curPage, int nextPage, int totalPage, int count, int pageSize) {
        this.data = data;
        this.firstPage = firstPage;
        this.prePage = prePage;
        this.curPage = curPage;
        this.nextPage = nextPage;
        this.totalPage = totalPage;
        this.count = count;
        this.pageSize = pageSize;
    }
}
