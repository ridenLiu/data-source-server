/**
 * Copyright (C), 2018-2020, 红有软件股份有限公司
 * FileName: SysDataParm
 * Author:   zlm
 * Date:     2020/4/21 11:17
 * Description: 数据源参数管理
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
 * @Description: 数据源参数实体类
 */
@Data
@ToString
@ApiModel(value="表:SYS_DATA_PARAM对应实体", description="数据源参数信息")
public class SysDataParm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数ID")
    private String parm_id;

    @ApiModelProperty(value = "数据源ID")
    private String ds_id;

    @ApiModelProperty(value = "数据源名称")
    private String ds_name;

    @ApiModelProperty(value = "参数代码")
    private String parm_code;

    @ApiModelProperty(value = "参数名称")
    private String parm_name;

    @ApiModelProperty(value = "参数类型：IN/OUT")
    private String parm_type;

    @ApiModelProperty(value = "序号",example = "123")
    private Integer parm_num;

    @ApiModelProperty(value = "默认值")
    private String def_value;

    @ApiModelProperty(value = "描述")
    private String parm_desc;

    @ApiModelProperty(value = "参数值类型,String,Integer,Long,Double,Float,Data,DataTime,结果集(OUT)")
    private String parm_data_type;

    @ApiModelProperty(value = "查询方式,0:非模糊查询,1:模糊查询(包含),2:模糊查询(开头),3:模糊查询(结尾)")
    private String parm_query_type;

    @ApiModelProperty(value = "备用字段",example = "123",hidden = true)
    private String other;
}
