package com.lychee.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/8/7 1:24
 */

@Data
public class PropsConvertParam {

    @ApiModelProperty(value = "配置文件文本")
    private String text;

    @ApiModelProperty(value = "源类型")
    private String source;

    @ApiModelProperty(value = "目标类型")
    private String target;

}
