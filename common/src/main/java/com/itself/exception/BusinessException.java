package com.itself.exception;


import com.itself.enums.ApiCode;
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

    public BusinessException(ApiCode apiCode){
        super(apiCode.getMessage());
        this.code = apiCode.getCode();
        this.msg = apiCode.getMessage();
    }

    public BusinessException(ApiCode apiCode, String msg){
        super(msg != null ? (apiCode.getMessage() + "," +msg) : apiCode.getMessage());
        this.msg =super.getMessage();
        this.code = apiCode.getCode();
    }
}