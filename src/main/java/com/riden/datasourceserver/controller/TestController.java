package com.riden.datasourceserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.riden.datasourceserver.common.jdbc.DataSourceFactory;
import com.riden.datasourceserver.entity.SysDataConnect;
import com.riden.datasourceserver.mapper.SysDataConnectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * 与业务无关的本地测试类!
 */
@RestController
@RequestMapping("/test")
@Api(value = "测试controller", tags = {"本地测试接口"})
public class TestController {

    @Autowired
    DataSourceFactory factory;

    @GetMapping("t1")
    public String test1(Boolean flag) {
        if (flag) {
            throw new NullPointerException("如果这都不算爱,我有什么好悲哀!");
        }

        return "处理结束";
    }


    @GetMapping("/t2")
    public JSONObject test2() throws SQLException {
        JSONObject j = new JSONObject();
        // mariadb: 2c040933f62e4c3d9568fbddfe8aafa0
        // oracle: 3e4a37c5cee1461ca21367f14721ca2e
        DataSource ds = factory.getById("3e4a37c5cee1461ca21367f14721ca2e");
        Connection conn = ds.getConnection();
        String sql = "select * from HT_LXSQ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            for (int i = 0; i < 5; i++) {
                System.out.print(rs.getObject(i + 1) + "\t");
            }
            System.out.println();
        }

        rs.close();
        ps.close();
        conn.close();
        return j;
    }
}
