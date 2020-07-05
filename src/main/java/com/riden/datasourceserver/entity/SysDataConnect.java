/**
 * Copyright (C), 2018-2020, 红有软件股份有限公司
 * FileName: SysDataConnect
 * Author:   zlm
 * Date:     2020/4/18 22:30
 * Description: 数据库信息实体类
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
 * @Description: 数据库信息实体类
 */
@Data
@ToString
@ApiModel(value = "表:SYS_DATA_CONNECT对应实体", description = "数据库信息")
public class SysDataConnect implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据库链接编号")
    private String db_link_no;

    @ApiModelProperty(value = "数据库链接名称")
    private String db_link_name;

    @ApiModelProperty(value = "数据库链接描述")
    private String db_link_desc;

    @ApiModelProperty(value = "数据库链接类型")
    private String db_link_type;

    @ApiModelProperty(value = "数据库ip")
    private String db_ip;

    @ApiModelProperty(value = "数据库名称")
    private String db_name;

    @ApiModelProperty(value = "数据库用户")
    private String db_user;

    @ApiModelProperty(value = "数据库口令")
    private String db_pw;

    @ApiModelProperty(value = "显示顺序",example = "123")
    private Integer sequ_num;

    @ApiModelProperty(value = "数据库状态 显示停用或启用")
    private String db_state;

    @ApiModelProperty(value = "添加人姓名")
    private String create_user_id;

    @ApiModelProperty(value = "添加时间")
    //@JSONField 对数据进行处理.默认使用Jackson框架,现已改为FastJson,参见HttpMessageConverterConfig
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;

    @ApiModelProperty(value = "端口")
    private String server_port;

    @ApiModelProperty(value = "数据库版本")
    private String db_ver;

    @ApiModelProperty(value = "是否是SID，针对oracle数据库，Y：代表SID，N：代表SERVICE_NAME", hidden = true)
    private String issid;

    @ApiModelProperty(value = "备用字段", hidden = true)
    private String other;

}
