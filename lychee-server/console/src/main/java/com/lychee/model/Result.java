package com.lychee.model;

import com.lychee.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/3/11 14:15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "返回对象", description = "返回对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "是否成功")
    private Boolean success = true;

    @ApiModelProperty(value = "返回数据对象")
    private T data;

    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    public Result(ResultCodeEnum resultCodeEnum){
        this.code = resultCodeEnum.code();
        this.message = resultCodeEnum.msg();
    }

    public Result(ResultCodeEnum resultCodeEnum, T data){
        this.code = resultCodeEnum.code();
        this.message = resultCodeEnum.msg();
        this.data = data;
    }

    public Result(int code, String message, boolean success){
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public Result(int code, String message, boolean success, T data){
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public static Result<?> ok(){
        return new Result<>(ResultCodeEnum.SUCCESS);
    }

    public static<T> Result<T> ok(T data){
        return new Result<>(ResultCodeEnum.SUCCESS, data);
    }

    public static Result<?> fail(){
        return new Result<>(ResultCodeEnum.ERROR);
    }

    public static Result<?> fail(Integer code, String message){
        return new Result<>(code, message, false);
    }

    public static<T> Result<T> fail(T data){
        return new Result<>(ResultCodeEnum.ERROR);
    }



}
