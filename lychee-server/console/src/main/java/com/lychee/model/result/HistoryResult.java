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
public class HistoryResult {

    @ApiModelProperty(value = "年")
    private String year;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "类型")
    private String type;

}
