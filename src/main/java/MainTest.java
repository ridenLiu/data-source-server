import com.riden.datasourceserver.common.jdbc.JDBCUtils;
import com.riden.datasourceserver.entity.SysDataConnect;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;


class MyResource implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("资源关闭了!");
    }
}

/**
 * 于业务无关的 测试类
 */
public class MainTest {
    public static void main(String[] args) throws Exception {
        String str = "%7B  %7D";
        String res = URLDecoder.decode(str, StandardCharsets.ISO_8859_1);
        System.out.println(res);

    }
    // 将资源是声明写在try后的括号里,在try语句快(try(){语句块...})执行完后,会自动关闭资源(调用close()方法)
    static void testAutoCloseable() throws Exception {
        try (MyResource r = new MyResource()) {
            System.out.println("running");
        }
        System.out.println("over");
    }


    static void test2() {
        //
        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").length());
        System.out.println("402593d2e8a845d5b5d1dc4c134ab975".length());
    }

    static void test1() throws SQLException {
        SysDataConnect obj = new SysDataConnect();
        obj.setDb_ip("localhost");
        obj.setServer_port("1521");
        obj.setDb_link_type("oracle");
        obj.setDb_name("orcl");
        obj.setDb_user("RIDEN");
        obj.setDb_pw("oracle");
//        obj.setDb_ip("localhost");
//        obj.setServer_port("3306");
//        obj.setDb_link_type("mysql");
//        obj.setDb_name("mytest");
//        obj.setDb_user("root");
//        obj.setDb_pw("mariadb");

        // url -> jdbc:oracle:thin:@//localhost:1521/{service_name}


        Connection conn = JDBCUtils.getConnection(obj);
        String sql = "select * from ht_lxsq";
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
    }


}
