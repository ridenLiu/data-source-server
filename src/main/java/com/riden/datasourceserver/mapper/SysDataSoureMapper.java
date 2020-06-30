/**
 * Copyright (C), 2020, 红有软件股份有限公司
 * FileName: SysDataSoureMapper
 * Author:   zlm
 * Date:     2020/4/20 15:17
 * Description: 数据源-数据服务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间         版本号              描述
 */
package com.riden.datasourceserver.mapper;

import com.riden.datasourceserver.entity.SysDataSoure;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @Description: 数据源-数据交互层
 * @author: zlm
 * @create: 2020/4/20
 * @since: 1.0.0
 */
public interface SysDataSoureMapper {
    @Select("<script> " +
            " select s.*,c.db_link_name as db_link_name " +
            "   from sys_data_connect c, sys_data_source s " +
            "  where s.db_link_no = c.db_link_no " +
            " and (upper(trim(s.ds_id)) LIKE CONCAT(CONCAT('%', #{parameter}), '%') or" +
            "      upper(trim(s.ds_name)) LIKE CONCAT(CONCAT('%', #{parameter}), '%') or" +
            "      upper(trim(s.ds)) LIKE CONCAT(CONCAT('%', #{parameter}), '%') )" +
            "  <when  test='db_link_no!=null'> and  s.db_link_no=#{db_link_no} </when>   " +
            "  ORDER BY s.create_date desc"+
            "</script>")
    List<SysDataSoure> querySysDataSoure(@Param("parameter") String parameter,
                                                @Param("db_link_no") String db_link_no);

    @Insert({" insert into sys_data_source (DS_ID, DS_NAME, DS_TYPE, DS, REMARKS, DB_LINK_NO, OTHER, CREATE_DATE) " +
            "  values ( #{ds_id},#{ds_name},#{ds_type},#{ds},#{remarks},#{db_link_no},#{other},sysdate )"})
    int add(SysDataSoure sysDataSoure);

    @Update({" update sys_data_source " +
            "    set DS_NAME = #{ds_name}, " +
            "        DS_TYPE = #{ds_type}, " +
            "        DS = #{ds}, " +
            "        REMARKS = #{remarks}, " +
            "        DB_LINK_NO  = #{db_link_no}, " +
            "        OTHER = #{other} " +
            "  where DS_ID = #{ds_id} "})
    int edit(SysDataSoure sysDataSoure);

    @Select({" select * from sys_data_source s where s.ds_id=#{ds_id} "})
    SysDataSoure queryById(@Param("ds_id") String ds_id);

    @Select("<script> " +
            "  select ds_id from sys_data_source where db_link_no in " +
            "  <foreach collection=\"array\" item=\"db_link_nos\" index=\"index\"  open=\"(\" close=\")\" separator=\",\"> " +
            " #{db_link_nos} " +
            " </foreach> "+
            "</script>")
    String[] getDsIdsByDblinkNo(String[] db_link_nos);

    @Delete("<script> " +
            "  delete from sys_data_source where ds_id  in " +
            "  <foreach collection=\"array\" item=\"ds_ids\" index=\"index\"  open=\"(\" close=\")\" separator=\",\"> " +
            " #{ds_ids} " +
            " </foreach> "+
            "</script>")
    int deleteByDsIds(String[] ds_ids);

}
