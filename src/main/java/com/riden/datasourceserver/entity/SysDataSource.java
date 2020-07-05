/**
 * Copyright (C), 2018-2020, 红有软件股份有限公司
 * FileName: SysDataSoure
 * Author:   zlm
 * Date:     2020/4/20 10:39
 * Description: 数据源实体类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间          版本号            描述
 */
package com.riden.datasourceserver.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 数据源实体类
 */
@Data
@ToString
@ApiModel(value="表:SYS_DATA_SOURCE对应实体", description="数据源信息")
public class SysDataSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据源ID")
    private String ds_id;

    @ApiModelProperty(value = "数据源名称")
    private String ds_name;

    @ApiModelProperty(value = "数据源类型：SQL/存储过程")
    private String ds_type;

    @ApiModelProperty(value = "数据源：SQL语句")
    private String ds;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "数据库链接编号,SYS_DATA_CONNECT:DB_LINK_NO")
    private String db_link_no;

    @ApiModelProperty(value = "数据库链接名称")
    private String db_link_name;

    @ApiModelProperty(value = "备用字段" )
    private String other;

    @ApiModelProperty(value = "添加时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_date;

}
