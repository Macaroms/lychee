package com.lychee.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/5/29 01:41
 */

@Data
public class IpSearchResult {

    @ApiModelProperty(value = "success")
    private String status;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "国家编码")
    private String countryCode;

    @ApiModelProperty(value = "省编码")
    private String region;

    @ApiModelProperty(value = "省")
    private String regionName;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "经度")
    private double lon;

    @ApiModelProperty(value = "纬度")
    private double lat;

    @ApiModelProperty(value = "时区")
    private String timezone;

    @ApiModelProperty(value = "归属")
    private String isp;

    @ApiModelProperty(value = "归属")
    private String as;

    @ApiModelProperty(value = "组织")
    private String org;

    @ApiModelProperty(value = "消息")
    private String message;

}
