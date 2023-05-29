package com.lychee.model.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/5/29 02:01
 */

@Data
public class RubbishClassifyResult {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "消息")
    private String msg;

    @ApiModelProperty(value = "数据")
    private RubbishClassifyData data;

    @Data
    static class RubbishClassifyData{

        @ApiModelProperty(value = "商品属性")
        private GoodsType aim;

        @ApiModelProperty(value = "推荐列表")
        private List<GoodsType> recommendList;

    }

    @Data
    static class GoodsType{

        @ApiModelProperty(value = "物品名称")
        private String goodsName;

        @ApiModelProperty(value = "物品类型")
        private String goodsType;

    }

}
