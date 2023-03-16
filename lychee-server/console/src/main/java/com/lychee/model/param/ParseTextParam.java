package com.lychee.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/8/7 1:24
 */

@Data
public class ParseTextParam {

    @ApiModelProperty(value = "src")
    private String src;

    @ApiModelProperty(value = "path")
    private String path;

    @ApiModelProperty(value = "xml/json")
    private String type;

}
