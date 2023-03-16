package com.lychee.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/3/17 00:46
 */

@Data
public class TranslateParam {

    @ApiModelProperty(value = "请求翻译query")
    private String query;

    @ApiModelProperty(value = "翻译源语言")
    private String from;

    @ApiModelProperty(value = "译文语言")
    private String to;

}
