package com.lychee.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yang
 * @since 2021-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_role")
@ApiModel(value="Role对象", description="")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String role_code;

    @ApiModelProperty(value = "角色名称")
    private String role_name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "创建人")
    private String create_by;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime create_time;

    @ApiModelProperty(value = "更新人")
    private String update_by;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime update_time;


}
