package com.lychee.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/8/7 1:24
 */

@Data
public class UrlCoderParam {

    private String src;

    @ApiModelProperty(value = "0:encode 1:decode")
    private Integer type;

}
