package com.lychee.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jiangwei97@aliyun.com
 * @since 2024/3/29 1:24
 */

@Data
public class ExecScriptParam {

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "方法")
    private String method;

    @ApiModelProperty(value = "参数列表")
    private List<Arg> args;

    @Data
    static class Arg {

        @ApiModelProperty(value = "参数主键")
        private String key;

        @ApiModelProperty(value = "参数值")
        private String value;

        @ApiModelProperty(value = "参数类型")
        private String type;

    }

}
