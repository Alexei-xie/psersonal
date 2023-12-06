package com.itself.responsibility.springboot;

/**责任链节点的接口
 * @Author duJi
 * @Date 2023/12/06
 */
public interface Handler  {
    /**
     * 节点处理请求接口
     * @param request
     */
    void handleRequest(HandlerRequest request);

    /**
     * 设置下一个节点处理者
     * @param nextHandler
     */
    void setNextHandler(Handler nextHandler);
}
