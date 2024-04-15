package com.itself.enums;

public enum ApiCode {
    /**
     * 操作成功
     **/
    SUCCESS(200, "api.success", "操作成功"),
    /**
     * 非法访问
     **/
    UNAUTHORIZED(401, "api.unauthorized", "非法访问"),
    /**
     * 没有权限
     **/
    NOT_PERMISSION(403, "api.notPermission", "没有权限"),
    /**
     * 你请求的资源不存在
     **/
    NOT_FOUND(404, "api.notFound", "你请求的资源不存在"),

    TOKEN_REFRESH(5406, "api.tokenRefresh", "token 自动刷新"),

    TOKEN_INVALID(5407, "api.tokenInvalid", "Token 失效， 请重新登录"),

    TOKEN_NOT_FOUND(5408, "api.tokenNotFound", "Token 不存在"),

    /**
     * 操作失败
     **/
    FAIL(500, "api.fail", "操作失败, 详情请联系管理员"),
    /**
     * 登录失败
     **/
    LOGIN_EXCEPTION(4000, "api.loginException", "登录失败"),
    /**
     * 系统异常
     **/
    SYSTEM_EXCEPTION(5000, "api.systemException", "系统异常"),
    /**
     * 请求参数校验异常
     **/
    PARAMETER_EXCEPTION(5001, "api.parameterException", "请求参数校验异常"),
    /**
     * 请求参数解析异常
     **/
    PARAMETER_PARSE_EXCEPTION(5002, "api.parameterParseException", "请求参数解析异常"),
    /**
     * HTTP内容类型异常
     **/
    HTTP_MEDIA_TYPE_EXCEPTION(5003, "api.httpMediaTypeException", "HTTP内容类型异常"),
    /**
     * 系统处理异常
     **/
    SPRING_BOOT_PLUS_EXCEPTION(5100, "api.springBootPlusException", "系统处理异常"),
    /**
     * 业务处理异常
     **/
    BUSINESS_EXCEPTION(5101, "api.businessException", "业务处理异常"),
    /**
     * 数据库处理异常
     **/
    DAO_EXCEPTION(5102, "api.daoException", "数据库处理异常"),
    /**
     * 验证码校验异常
     **/
    VERIFICATION_CODE_EXCEPTION(5103, "api.verificationCodeException", "验证码校验异常"),
    /**
     * 登录授权异常
     **/
    AUTHENTICATION_EXCEPTION(5104, "api.authenticationException", "登录授权异常"),
    /**
     * 没有访问权限
     **/
    UNAUTHENTICATED_EXCEPTION(5105, "api.unauthenticatedException", "没有访问权限"),
    /**
     * 没有访问权限
     **/
    UNAUTHORIZED_EXCEPTION(5106, "api.unauthorizedException", "没有访问权限"),
    /**
     * JWT Token解析异常
     **/
    JWTDECODE_EXCEPTION(5107, "api.jwtdecodeException", "Token解析异常"),

    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(5108, "api.requestMtdException", "不支持的请求方法"),

    ;

    private final int code;

    private String i18nKey;
    private final String message;

    ApiCode(final int code, final String i18nKey, final String message) {
        this.code = code;
        this.i18nKey = i18nKey;
        this.message = message;
    }

    public static ApiCode getApiCode(int code) {
        ApiCode[] ecs = ApiCode.values();
        for (ApiCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public String getMessage() {
        return message;
    }
}