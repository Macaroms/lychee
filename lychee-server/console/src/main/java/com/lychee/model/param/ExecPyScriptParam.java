package com.lychee.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiangwei97@aliyun.com
 * @since 2024/4/9 10:24
 */

@Data
public class ExecPyScriptParam {

    @ApiModelProperty(value = "代码")
    private String code;

}
