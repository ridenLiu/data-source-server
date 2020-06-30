/**
 * Copyright (C), 2018-2020, 红有软件股份有限公司
 * FileName: SysDataColumn
 * Author:   zlm
 * Date:     2020/4/23 22:02
 * Description: 数据源字段表实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间          版本号            描述
 */
package com.riden.datasourceserver.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 数据源字段表实体类
 */
@Data
@ToString
@ApiModel(value="表:SYS_DATA_COLUMN对应实体", description="数据源字段")
public class SysDataColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段ID")
    private String column_id;

    @ApiModelProperty(value = "字段标题")
    private String column_title;

    @ApiModelProperty(value = "字段名")
    private String column_name;

    @ApiModelProperty(value = "数据源ID")
    private String ds_id;

    @ApiModelProperty(value = "数据源名称")
    private String ds_name;

    @ApiModelProperty(value = "序号")
    private Integer column_num;

    @ApiModelProperty(value = "字段长度")
    private Integer column_length;

    @ApiModelProperty(value = "是否主键  0:非主键 1:主键")
    private Integer column_primary_key;

    @ApiModelProperty(value = "可唯一 0:不唯一 1:唯一")
    private Integer column_unique;

    @ApiModelProperty(value = "字段类型")
    private String column_type;

    @ApiModelProperty(value = "可非空 0:可为空 1:空")
    private Integer column_isnull;

}
