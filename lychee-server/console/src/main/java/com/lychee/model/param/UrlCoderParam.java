package com.lychee.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/8/7 1:24
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class UrlCoderParam extends CoderParam {

    @ApiModelProperty(value = "编码格式")
    private String enc;

}
