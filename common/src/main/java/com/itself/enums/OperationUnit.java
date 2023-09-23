package com.itself.enums;

/**
 * @Author xxw
 * @Date 2021/08/17
 */
public enum OperationUnit {
    /**
     * 被操作的单元
     */
    UNKNOWN("unknown"),
    USER("user"),
    EMPLOYEE("employee"),
    Redis("redis");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    OperationUnit(String s){
        this.value = s;
    }

}
