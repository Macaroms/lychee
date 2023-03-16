package com.lychee.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/3/12 18:37
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoetryResult {

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "来源")
    private String origin;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "类型")
    private String category;

}
