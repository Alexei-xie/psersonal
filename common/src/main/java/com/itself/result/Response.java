package com.itself.result;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.itself.enums.ApiCode;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author xxw
 * @Date 2022/08/09
 *
 * 通用返回对象封装类
 */
@Data
@Builder
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success = true;

    /**
     * 返回处理消息
     */
    private String message = "OK!";

    /**
     * 响应代码
     */
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    private T data;

    /**
     * 响应时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public Response() {
        time  = new Date();
    }

    public static Response<Boolean> result(boolean flag){
        if(flag){
            return ok();
        }
        return error();
    }

    public static Response<Boolean> ok(){
        return ok(true);
    }

    public static Response<Boolean> ok(String message){
        return result(ApiCode.SUCCESS, message, true);
    }

    public static <T> Response<T> ok(T data){
        return result(ApiCode.SUCCESS,data);
    }

    public static <T> Response<T> ok(T data, String message){
        return result(ApiCode.SUCCESS,message,data);
    }

    public static Response<Boolean> error(){
        return error(false);
    }

    public static <T> Response<T> error(T data){
        return result(ApiCode.FAIL,data);
    }

    public static Response<Boolean> error(String message){
        return result(ApiCode.FAIL,message,false);
    }

    public static Response<Boolean> error(ApiCode apiCode){
        return result(apiCode,null);
    }

    public static <T> Response<T> error(ApiCode apiCode, T data){
        if (ApiCode.SUCCESS == apiCode){
            throw new RuntimeException("失败结果状态码不能为" + ApiCode.SUCCESS.getCode());
        }
        return result(apiCode,data);
    }

    public static Response<Object> error(int code, String msg) {
        return Response.builder()
                .code(code)
                .message(msg)
                .success(false)
                .build();
    }

    public static Response<Map<String,Object>> error(String key, Object value){
        Map<String,Object> map = new HashMap<>(1);
        map.put(key,value);
        return result(ApiCode.FAIL,map);
    }

    public static <T> Response<T> result(ApiCode apiCode, T data){
        return result(apiCode,null,data);
    }

    public static <T> Response<T> result(ApiCode apiCode, String message, T data){
        boolean success = false;
        if (apiCode.getCode() == ApiCode.SUCCESS.getCode()){
            success = true;
        }
        String apiMessage = apiCode.getMessage();
        if (StringUtils.isBlank(message)){
            message = apiMessage;
        }
        return (Response<T>) Response.builder()
                .code(apiCode.getCode())
                .message(message)
                .data(data)
                .success(success)
                .time(new Date())
                .build();
    }
}