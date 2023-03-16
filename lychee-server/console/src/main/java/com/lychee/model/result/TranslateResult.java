package com.lychee.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/3/17 00:52
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslateResult {

    @ApiModelProperty(value = "源语言")
    private String from;

    @ApiModelProperty(value = "译文语言")
    private String to;

    @ApiModelProperty(value = "原文")
    private String src;

    @ApiModelProperty(value = "译文")
    private String dst;

}
