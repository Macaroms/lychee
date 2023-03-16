package com.lychee.enums;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/3/11 14:05
 */
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    ERROR(500, "系统内部异常"),
    ;

    private final Integer code;

    private final String msg;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
