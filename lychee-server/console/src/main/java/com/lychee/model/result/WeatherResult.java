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
public class WeatherResult {

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "地区编码")
    private String areaCode;

    @ApiModelProperty(value = "天气")
    private String weather;

    @ApiModelProperty(value = "温度")
    private String temperature;

    @ApiModelProperty(value = "湿度")
    private String humidity;

    @ApiModelProperty(value = "风向")
    private String windDirection;

    @ApiModelProperty(value = "风力")
    private String windPower;

    @ApiModelProperty(value = "报告时间")
    private String reportTime;

}
