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
public class IpDataResult {

    @ApiModelProperty(value = "IPV4/IPV6")
    private String type;

    @ApiModelProperty(value = "IP")
    private String text;

    @ApiModelProperty(value = "地区")
    private String region;

    @ApiModelProperty(value = "拥有者")
    private String owner;

    @ApiModelProperty(value = "地区编码")
    private String areaCode;

}
