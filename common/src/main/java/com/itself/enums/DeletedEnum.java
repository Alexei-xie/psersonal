package com.itself.enums;

import java.util.Objects;

/**
 * 逻辑删除枚举类
 * @Author xxw
 * @Date 2022/07/15
 */
public enum DeletedEnum {
    /**
     * 未删除
     */
    UN_DELETED(0,"未删除"),
    /**
     * 已删除
     */
    DELETE(1,"已删除");

    private final Integer value;

    private final String desc;

    DeletedEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * 根据值获取枚举
     */
    public DeletedEnum findByValue(Integer value) {
        for (DeletedEnum deletedEnum : DeletedEnum.values()) {
            if (Objects.equals(deletedEnum.getValue(),value)){
                return deletedEnum;
            }
        }
        return null;
    }

}
