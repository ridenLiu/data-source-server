package com.riden.datasourceserver.common.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.riden.datasourceserver.entity.SysDataConnect;
import com.riden.datasourceserver.mapper.SysDataConnectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DataSourceFactory {
    /*
    volatile关键字
    1. 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的
    2. 禁止进行指令重排序
     */
    // key: 数据链路id,数据源
    private volatile Map<String, DataSource> dataSourceMap = new HashMap<>();


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SysDataConnectMapper sysDataConnectMapper;

    /*
    @PostConstruct注解:
        @PostConstruct该注解被用来修饰一个非静态的void（）方法。
        被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。
        PostConstruct在构造函数之后执行，init（）方法之前执行
     */

    @PostConstruct
    public void init() {
        List<SysDataConnect> connectionList = sysDataConnectMapper.querySysDataConnect("");
        log.info("---------------------DataSourceFactory初始化加载数据库链路,总计有" + connectionList.size() + "个 开始---------------------------");
        if (connectionList.size() > 0) {
            for (SysDataConnect item : connectionList) {
                DataSource dataSource = getDataSource(item);
                if (dataSource != null) {
                    dataSourceMap.put(item.getDb_link_no(), dataSource);
                }
            }
        }
        log.info("---------------------DataSourceFactory初始化加载数据库链路,实际加载" + dataSourceMap.size() + "个 结束---------------------------");
    }

    public DataSource getDataSource(SysDataConnect connObj) {
        // 获取连接数据库的信息
        HashMap<String, String> connInfo = JDBCUtils.getConnectInfo(connObj);
        String username = connInfo.get("username");
        String password = connInfo.get("password");
        String driverClassName = connInfo.get("driverClassName");
        String url = connInfo.get("url");
        // 配置DruidDataSource
        DruidDataSource ds = new DruidDataSource();
        // 主要配置
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        // 其他配置
        ds.setInitialSize(5); //最小连接池数量
        ds.setMaxActive(15); // 最大连接池数量
        ds.setMaxWait(30 * 1000); // 连接等待超时时间(单位:毫秒)
        ds.setTimeBetweenEvictionRunsMillis(30 * 60 * 1000);// 有两个含义：1) Destroy线程会检测连接的间隔时间2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
        ds.setMinEvictableIdleTimeMillis(5 * 60 * 1000); // 配置连接在池中的最小生存时间 毫秒
        ds.setValidationQuery("SELECT 1 FROM DUAL"); //用来检测连接是否有效的sql(注意数据库类型)，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
        ds.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        ds.setTestOnBorrow(true);// 获取连接时执行检测，影响性能,这里建议开启
        ds.setTestOnReturn(false);// 获取连接时执行检测，影响性能,这里建议关闭
        ds.setPoolPreparedStatements(true); // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        ds.setMaxPoolPreparedStatementPerConnectionSize(20); // 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
        ds.setConnectionErrorRetryAttempts(0);// 连接出错后再尝试连接0次
        ds.setBreakAfterAcquireFailure(true);// 数据库服务宕机自动重连机制
        ds.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000"); // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        try {
            // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
            ds.setFilters("stat,wall,slf4j"); // todo 待处理的东西
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("DataSourceFactory数据库链路:" + connObj.getDb_link_name() + "初始化失败,请检查链路信息", e);
            return null;
        }

        return ds;
    }

    /**
     * 获取 JDBC 链接
     */
    public DataSource getById (String id){
        return dataSourceMap.get(id);
    }

    /**
     * 移除 数据源
     */
    public void removeById (String id) {
        dataSourceMap.remove(id);
    }

    /**
     * 添加数据源管理
     */
    public void addDataSource (SysDataConnect sysDataConnect){
        DataSource dataSource = getDataSource(sysDataConnect);
        if(dataSource!=null){
            dataSourceMap.put(sysDataConnect.getDb_link_no(),dataSource);
            log.info("DataSourceFactory添加数据库链路:"+sysDataConnect.getDb_link_name()+",添加成功");
        }
    }

}
