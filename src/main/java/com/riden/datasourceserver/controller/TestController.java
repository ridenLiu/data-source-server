package com.riden.datasourceserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

/**
 * 与业务无关的本地测试类!
 */
@RestController
@RequestMapping("/test")
@Api(value = "测试controller", tags = {"本地测试接口"})
public class TestController {

    @Autowired
    DataSource dataSource;
    @Autowired
    PageHelper pageHelper;


    @GetMapping("/sayHello")
    public JSONObject hello() {
        JSONObject res = new JSONObject();
        res.put("world", "hello World");
        return res;
    }


    @ApiOperation("Post请求测试")
    @PostMapping("/postt")// 只要接口中,返回值中存在实体类,它就会被扫描到swagger中
    public String postTest(@ApiParam(value = "姓名", name = "jkName", required = true) @RequestBody Map<String, Object> map) {
        return "收到,收到: " + map.get("jkName");
    }

    /*
    value用于方法描述
    notes用于提示内容
    tags可以重新分组（视情况而用）
     */
    @ApiOperation(value = "获取一条学生数据", notes = "注意问题点")
    @GetMapping("/data")
    public JSONObject test1() {
        JSONObject res = new JSONObject();
        Connection conn = null;
        try {
            System.out.println(dataSource);
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM STUDENT");
            ResultSetMetaData rsm = ps.getMetaData();
            int columnCount = rsm.getColumnCount();
            String[] names = new String[columnCount];
            for (int i = 0; i < names.length; i++) {
                names[i] = rsm.getColumnName(i + 1);
            }
            ResultSet rs = ps.executeQuery();
            int count = 1;
            while (rs.next()) {
                for (int i = 0; i < names.length; i++) {
                    res.put(names[i], rs.getObject(i + 1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("返回结果: " + res);
        return res;
    }

}
