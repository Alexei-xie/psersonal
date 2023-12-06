package com.itself.responsibility.springboot;

/** 请求参数实体
 * @Author duJi
 * @Date 2023/12/06
 */
public class HandlerRequest {
    private Integer count;

    public HandlerRequest(Integer count) {
        this.count = count;
    }

    public HandlerRequest() {
    }

    public Integer getCount() {
        return count;
    }

    public void setContent(Integer count) {
        this.count = count;
    }
}
