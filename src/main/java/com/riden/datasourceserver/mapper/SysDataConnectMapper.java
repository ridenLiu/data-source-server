/**
 * Copyright (C), 2020, 红有软件股份有限公司
 * FileName: SysDataConnectMapper
 * Author:   zlm
 * Date:     2020/4/18 23:54
 * Description: 数据库信息数据交互层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间         版本号              描述
 */
package com.riden.datasourceserver.mapper;

import com.riden.datasourceserver.entity.SysDataConnect;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: 数据库信息数据交互层
 * @author: zlm
 * @create: 2020/4/18
 * @since: 1.0.0
 */
public interface SysDataConnectMapper {


    @Select(" select * " +
            "   from sys_data_connect t " +
            "  where (upper(trim(t.db_link_name)) LIKE CONCAT(CONCAT('%', #{parameter}), '%') or" +
            "   upper(trim(t.db_ip)) LIKE CONCAT(CONCAT('%', #{parameter}), '%')) order by t.sequ_num")
    List<SysDataConnect> querySysDataConnect(@Param("parameter") String parameter);

    @Select({" select * " +
            "   from sys_data_connect t " +
            "  where t.db_link_no=#{db_link_no}"})
    SysDataConnect querySysDataConnectById(@Param("db_link_no") String db_link_no);

    @Insert(" insert into sys_data_connect " +
            "   (DB_LINK_NO, " +
            "    DB_LINK_NAME, " +
            "    DB_LINK_DESC, " +
            "    DB_LINK_TYPE, " +
            "    DB_IP, " +
            "    DB_NAME, " +
            "    DB_USER, " +
            "    DB_PW, " +
            "    SEQU_NUM, " +
            "    DB_STATE, " +
            "    CREATE_USER_ID, " +
            "    CREATE_DATE, " +
            "    SERVER_PORT, " +
            "    DB_VER, " +
            "    ISSID, " +
            "    OTHER) " +
            " values " +
            "   (#{db_link_no}, " +
            "    #{db_link_name}, " +
            "    #{db_link_desc}, " +
            "    #{db_link_type}, " +
            "    #{db_ip}, " +
            "    #{db_name}, " +
            "    #{db_user}, " +
            "    #{db_pw}, " +
            //"    (select Max(t.sequ_num)+1 from sys_data_connect t), " +
            "    (select case when  Max(sequ_num)+1 is null then 1 else Max(sequ_num)+1 end xh from sys_data_connect), " +
            "    #{db_state}, " +
            "    #{create_user_id}, " +
            "   sysdate, " +
            "    #{server_port}, " +
            "    #{db_ver}, " +
            "    #{issid}, " +
            "    #{other}) ")
    int add(SysDataConnect sysDataConnect);

    @Update({" update sys_data_connect " +
            "    set DB_LINK_NAME = #{db_link_name}, " +
            "        DB_LINK_DESC = #{db_link_desc}, " +
            "        DB_LINK_TYPE = #{db_link_type}, " +
            "        DB_IP        = #{db_ip}, " +
            "        DB_NAME      = #{db_name}, " +
            "        DB_USER      = #{db_user}, " +
            "        DB_PW        = #{db_pw}, " +
            "        DB_STATE     = #{db_state}, " +
            "        SERVER_PORT  = #{server_port}, " +
            "        DB_VER       = #{db_ver}, " +
            "        ISSID        = #{issid} " +
            "  where DB_LINK_NO = #{db_link_no} "})
    int edit(SysDataConnect sysDataConnect);

    @Select({" select * " +
            "   from sys_data_connect t " +
            "  where t.db_state='启用' order by t.sequ_num "})
    List<SysDataConnect> listAllDbState();

    @Delete("<script> " +
            "  delete from sys_data_connect where db_link_no  in " +
            "  <foreach collection=\"array\" item=\"db_link_nos\" index=\"index\"  open=\"(\" close=\")\" separator=\",\"> " +
            " #{db_link_nos} " +
            " </foreach> "+
            "</script>")
    int delete(String[] db_link_nos);


}
