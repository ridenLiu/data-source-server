import com.riden.datasourceserver.common.jdbc.JDBCUtils;
import com.riden.datasourceserver.entity.SysDataConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 于业务无关的 测试类
 */
public class MainTest {
    public static void main(String[] args) throws SQLException {
        test1();
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
