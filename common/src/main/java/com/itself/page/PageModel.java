package com.itself.page;

import java.util.List;

/**
 * @Author xxw
 * @Date 2022/08/09
 *
 * 分页数据封装工具类
 */
public class PageModel<T> extends PageInfo{
    /**
     * 总记录数
     */
    private int totalRecord = 0;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 结果集
     */
    private List<T> list;

    public PageModel() {
        super();
    }

    public PageModel(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        /**
         * 在设置总页数的时候计算出对应的总页数
         */
        this.totalPage = totalRecord % getPageSize() == 0 ? totalRecord / getPageSize() : totalRecord / getPageSize()+ 1;
    }


    /**
     * 将数据接收进来并返回给封装好的分页数据
     */
    public PageModel<T> handlePageData(List<T> data,Integer pageNum,Integer pageSize){
        com.github.pagehelper.PageInfo<T> pageInfo = new com.github.pagehelper.PageInfo<>(data);
        PageModel<T> pageModel = new PageModel<>();
        pageModel.setPageNum(pageNum);
        pageModel.setPageSize(pageSize);
        pageModel.setTotalRecord((int)pageInfo.getTotal());
        pageModel.setList(data);
        return pageModel;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}