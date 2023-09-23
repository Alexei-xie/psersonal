package com.itself.result;


/**
 * @Author xxw
 * @Date 2022/08/09
 *
 * 通用返回对象封装类
 */
public class Response<T> {
    private Integer code ;
    private String message;
    private T data;

    protected Response() {
    }

    protected Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public static Response<Void> error(String message){
        return new Response(ReturnCode.UNDEFINED_ERROR.getCode(),message);
    }
    public static Response<Void> error(Integer code, String message){
        return new Response(code,message);
    }
    public static Response<Void> error(Integer code,String message, Object data){
        return new Response(code,message,data);
    }

    public static <T>  Response<T> success(T data){
        return new Response<T>(ReturnCode.SUCCESS.getCode(),"操作成功",data);
    }

    public static Response<Void> success(Integer code, String message){
        return new Response(code,message);
    }


    public Boolean success(){
        return this.code == 200;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}