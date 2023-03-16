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

    @ApiModelProperty(value = "info")
    private String info;

    @ApiModelProperty(value = "日期")
    private String date;

}
