/**
 * Copyright (C), 2020, 红有软件股份有限公司
 * FileName: SysDataColumnMapper
 * Author:   zlm
 * Date:     2020/4/23 22:26
 * Description: 数据源字段数据服务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间         版本号              描述
 */
package com.riden.datasourceserver.mapper;

import com.riden.datasourceserver.entity.SysDataColumn;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: 数据源字段数据服务层
 * @author: zlm
 * @create: 2020/4/23
 * @since: 1.0.0
 */
public interface SysDataColumnMapper {

    @Select("<script> " +
            " select s.*,c.ds_name as ds_name " +
            "   from sys_data_source c, sys_data_column s " +
            "  where s.ds_id = c.ds_id " +
            " and (" +
            "      upper(trim(s.column_title)) LIKE CONCAT(CONCAT('%', #{parameter}), '%') or" +
            "      upper(trim(s.column_name)) LIKE CONCAT(CONCAT('%', #{parameter}), '%') " +
            ")" +
            "  <when  test='ds_id!=null'> and  s.ds_id=#{ds_id} </when>   " +
            "  ORDER BY s.column_num "+
            "</script>")
    List<SysDataColumn> querySysDataColumn(@Param("parameter") String parameter, @Param("ds_id") String ds_id);

    @Insert({
            "<script>",
            "insert into sys_data_column (COLUMN_ID, COLUMN_TITLE, COLUMN_NAME, DS_ID, COLUMN_NUM, COLUMN_LENGTH, COLUMN_PRIMARY_KEY, COLUMN_UNIQUE, COLUMN_TYPE, COLUMN_ISNULL) values",
            "( #{column_id},#{column_title},#{column_name},#{ds_id},#{column_num},#{column_length},#{column_primary_key},#{column_unique},#{column_type},#{column_isnull})",
            "</script>"
    })
    int add(SysDataColumn sysDataColumn);

    @Update({" update sys_data_column " +
            "    set COLUMN_TITLE = #{column_title}, " +
            "        COLUMN_NAME = #{column_name}, " +
            "        COLUMN_NUM = #{column_num}, " +
            "        COLUMN_LENGTH = #{column_length}, " +
            "        COLUMN_TYPE  = #{column_type}, " +
            "        COLUMN_ISNULL = #{column_isnull} " +
            "  where COLUMN_ID = #{column_id} "})
    int edit(SysDataColumn sysDataColumn);

    @Delete("<script> " +
            "  delete from sys_data_column where COLUMN_ID  in " +
            "  <foreach collection=\"array\" item=\"columnIds\" index=\"index\"  open=\"(\" close=\")\" separator=\",\"> " +
            " #{columnIds} " +
            " </foreach> "+
            "</script>")
    int delete(String[] columnIds);

    @Delete({
            " delete from sys_data_column t where t.ds_id=#{ds_id} "
    })
    int deleteByDsId(@Param("ds_id") String ds_id);


    @Update({" update sys_data_column " +
            "    set column_num = #{column_num} " +
            "  where column_id = #{column_id} "})
    int editColumnNumByColumnId(@Param("column_id") String column_id, @Param("column_num") int column_num);

    @Delete("<script> " +
            "  delete from sys_data_column where ds_id  in " +
            "  <foreach collection=\"array\" item=\"ds_ids\" index=\"index\"  open=\"(\" close=\")\" separator=\",\"> " +
            " #{ds_ids} " +
            " </foreach> "+
            "</script>")
    int deleteByDsIds(String[] ds_ids);
}
