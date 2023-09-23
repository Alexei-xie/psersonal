package com.itself.result;

import java.util.Objects;

/**
 * @Author xxw
 * @Date 2022/08/14
 *
 * 返回code
 */
public enum ReturnCode{

    /**
     * 通用
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    UNDEFINED_ERROR(9999,"服务器开小差了，请稍后重试"),

    UN_FIND_ERROR(10000,"为找到该数据"),
    ;


    private Integer code;

    private String msg;

    ReturnCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 通过code获取对一个的枚举类型
     */
    public static ReturnCode fromValue(String code) {
        for (ReturnCode returnCode : ReturnCode.values()) {
            if (Objects.equals(returnCode.getCode(), code)){
                return returnCode;
            }
        }
        return null;
    }
}