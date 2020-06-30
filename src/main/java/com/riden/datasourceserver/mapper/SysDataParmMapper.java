/**
 * Copyright (C), 2018-2020, 红有软件股份有限公司
 * FileName: SysDataParmMapper
 * Author:   zlm
 * Date:     2020/4/21 11:46
 * Description: 数据源参数数据交互层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间          版本号            描述
 */
package com.riden.datasourceserver.mapper;

import com.riden.datasourceserver.entity.SysDataParm;
import org.apache.ibatis.annotations.*;
import java.util.List;


/**
 * @Description: 数据源参数数据交互层
 * @author: zlm
 * @create: 2020/4/21
 * @since 1.0.0
 */
public interface SysDataParmMapper {

    @Select("<script> " +
            " select s.*,c.ds_name as ds_name " +
            "   from sys_data_source c, sys_data_parm s " +
            "  where s.ds_id = c.ds_id " +
            " and (" +
            "      upper(trim(s.parm_code)) LIKE CONCAT(CONCAT('%', #{parameter}), '%') or" +
            "      upper(trim(s.parm_name)) LIKE CONCAT(CONCAT('%', #{parameter}), '%') " +
            ")" +
            "  <when  test='ds_id!=null'> and  s.ds_id=#{ds_id} </when>   " +
            "  ORDER BY s.parm_num "+
            "</script>")
    List<SysDataParm> querySysDataParm(@Param("parameter") String parameter,
                                              @Param("ds_id") String ds_id);

    @Insert({" insert into sys_data_parm (PARM_ID, DS_ID, PARM_CODE, PARM_NAME, PARM_TYPE, PARM_NUM, DEF_VALUE, PARM_DESC, PARM_DATA_TYPE, OTHER,PARM_QUERY_TYPE) " +
            "  values ( #{parm_id},#{ds_id},#{parm_code},#{parm_name},#{parm_type},#{parm_num},#{def_value},#{parm_desc},#{parm_data_type},#{other},#{parm_query_type} )"})
    int add(SysDataParm sysDataParm);

    @Update({" update SYS_DATA_PARM " +
            "    set PARM_CODE = #{parm_code}, " +
            "        PARM_NAME = #{parm_name}, " +
            "        PARM_TYPE = #{parm_type}, " +
            "        PARM_NUM = #{parm_num}, " +
            "        DEF_VALUE  = #{def_value}, " +
            "        PARM_DESC = #{parm_desc}, " +
            "        PARM_DATA_TYPE = #{parm_data_type}, " +
            "        OTHER = #{other}, " +
            "        PARM_QUERY_TYPE = #{parm_query_type} " +
            "  where PARM_ID = #{parm_id} "})
    int edit(SysDataParm sysDataParm);

    @Select({
            " select max(t.parm_num) as parm_num from sys_data_parm t where t.ds_id=#{ds_id}"
    })
    SysDataParm queryMaxParmNum(@Param("ds_id") String ds_id);

    @Select({
            " select * from sys_data_parm t where t.ds_id=#{ds_id} and t.parm_code=#{parm_code}"
    })
    SysDataParm querySysDataParmByParmCode(@Param("ds_id") String ds_id, @Param("parm_code") String parm_code);

    @Delete("<script> " +
            "  delete from sys_data_parm where parm_id  in " +
            "  <foreach collection=\"array\" item=\"parmIds\" index=\"index\"  open=\"(\" close=\")\" separator=\",\"> " +
            " #{parmIds} " +
            " </foreach> "+
            "</script>")
    int delete(String[] parmIds);

    @Select({
            " select * from sys_data_parm t where t.parm_id=#{parm_id}"
    })
    SysDataParm querySysDataParmById(@Param("parm_id") String parm_id);

    @Update({" update SYS_DATA_PARM " +
            "    set PARM_NUM = #{parm_num} " +
            "  where PARM_ID = #{parm_id} "})
    int editParmNumByParmId(@Param("parm_id") String parm_id, @Param("parm_num") int parm_num);

    @Delete("<script> " +
            "  delete from sys_data_parm where ds_id  in " +
            "  <foreach collection=\"array\" item=\"ds_ids\" index=\"index\"  open=\"(\" close=\")\" separator=\",\"> " +
            " #{ds_ids} " +
            " </foreach> "+
            "</script>")
    int deleteByDsIds(String[] ds_ids);

}
