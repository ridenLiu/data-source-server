package com.riden.datasourceserver.common.jdbc;

import com.riden.datasourceserver.entity.SysDataConnect;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JDBCUtils {


    /**
     * oracle 驱动
     */
    public static final String ORACLE_DRIVER_CLASS_NAME = "oracle.jdbc.OracleDriver";

    /**
     * mysql 驱动
     */
    public static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    /**
     * sqlserver 驱动
     */
    public static final String SQLSERVER_DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    /**
     * 通过链路对象获取一个包含JDBC连接数据库信息的map
     *
     * @param connObj 链路对象
     * @return HashMap: map中包含: driverClassName,url,username,password,databaseType
     */
    public static HashMap<String, String> getConnectInfo(SysDataConnect connObj) {
        String linkNo = connObj.getDb_link_no();
        String dbIP = connObj.getDb_ip();// ip地址
        String port = connObj.getServer_port(); // 端口号
        String dbName = connObj.getDb_name(); // 数据库名
        String username = connObj.getDb_user();
        String password = connObj.getDb_pw();
        String databaseType = connObj.getDb_link_type().toUpperCase();
        // 数据库连接信息
        HashMap<String, String> connectInfo = new HashMap<>();
        // 配置url
        String driverClassName = null;
        String url = null;
        Connection conn = null;
        // 数据库类型
        switch (databaseType) {
            case "ORACLE":
                driverClassName = ORACLE_DRIVER_CLASS_NAME;
                String isSid = connObj.getIssid();// 是否使用sid模式连接
                if ("Y".equals(isSid)) {
                    // Oracle JDBC Thin using an SID
                    // 例: jdbc:oracle:thin:@localhost:1521:orcl
                    // 获取数据库sid的sql: select instance_name from  V$instance;
                    url = "jdbc:oracle:thin:@{ip}:{port}:{service_name}";
                } else {
                    // 这种格式是Oracle 推荐的格式，因为对于集群来说，每个节点的SID 是不一样的
                    //  Oracle JDBC Thin using a ServiceName:
                    // 例:  jdbc:oracle:thin:@//localhost:1521/orcl.city.com
                    // 获取service_name的sql: select value from v$parameter where name='service_names';
                    url = "jdbc:oracle:thin:@//{ip}:{port}/{service_name}";
                }
                break;
            case "MYSQL":
                driverClassName = MYSQL_DRIVER_CLASS_NAME;
                url = "jdbc:mysql://{ip}:{port}/{service_name}?characterEncoding=utf8&serverTimezone=UTC";
                break;
            default:
                log.error("数据库类型错误或暂不支持!当前数据库类型: " + databaseType + " 数据链路ID: " + linkNo);
                return null;
        }
        url = url.replace("{ip}", dbIP);
        url = url.replace("{port}", port);
        url = url.replace("{service_name}", dbName);

        connectInfo.put("databaseType", databaseType);
        connectInfo.put("driverClassName", driverClassName);
        connectInfo.put("username", username);
        connectInfo.put("password", password);
        connectInfo.put("url", url);
        return connectInfo;
    }

    /**
     * 获取一个数据库连接
     *
     * @param connInfo HashMap,需要包含: driverClassName,url,username,password
     * @return 数据库连接
     */
    public static Connection getConnection(Map<String, String> connInfo) {
        String driverClassName = connInfo.get("driverClassName");
        String url = connInfo.get("url");
        String username = connInfo.get("username");
        String password = connInfo.get("password");
        Connection conn = null;
        try {
            Class.forName(driverClassName); // 注册驱动
            DriverManager.setLoginTimeout(10); // 配置连接超时时长(单位:秒)
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return conn;
    }

    /**
     * 通过链路对象获取一个数据库连接
     *
     * @param connObj
     * @return
     */
    public static Connection getConnection(SysDataConnect connObj) {
        HashMap<String, String> connInfo = getConnectInfo(connObj);
        return getConnection(connInfo);
    }


}
