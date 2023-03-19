package com.lychee.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/3/20 0:11
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickTextResult {

    @ApiModelProperty(value = "length")
    private int size;

    @ApiModelProperty(value = "text")
    private String text;

}
