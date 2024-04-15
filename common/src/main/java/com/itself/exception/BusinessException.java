package com.itself.exception;


import java.io.Serializable;

/**
 * @Author xxw
 * @Date 2022/08/14
 *
 * 自定义业务异常类
 */
public class BusinessException extends RuntimeException implements Serializable{
    private Integer code;
    private String msg;

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


    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(Throwable cause, Integer code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(ReturnCode returnCode){
        super(returnCode.getMsg());
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
    }

    public BusinessException(ReturnCode returnEum, String msg){
        super(msg != null ? (returnEum.getMsg() + "," +msg) : returnEum.getMsg());
        this.msg =super.getMessage();
        this.code = returnEum.getCode();
    }
}