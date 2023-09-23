package com.itself.page;

import java.io.Serializable;

/**
 * @Author xxw
 * @Date 2022/08/11
 */
public class PageInfo implements Serializable{
    private int pageNum;
    private int pageSize;
    private boolean needTotal = false;

    public PageInfo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageInfo() {
        this.pageNum = 1;
        this.pageSize = 10;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean getNeedTotal() {
        return needTotal;
    }

    public void setNeedTotal(boolean needTotal) {
        this.needTotal = needTotal;
    }
}