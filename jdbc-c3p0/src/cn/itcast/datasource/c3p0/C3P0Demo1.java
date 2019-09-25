package cn.itcast.datasource.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * c3p0演示
 */
public class C3P0Demo1 {
    public static void main(String[] args) throws SQLException {
        //1.创建数据库连接对象,此时使用的是默认配置
        //如果要用指定的配置：DataSource ds = new ComboPooledDataSource("otherc3p0");
        DataSource ds = new ComboPooledDataSource();
        //2.获取连接对象
        Connection conn = ds.getConnection();
        //后面就跟JDBC一样的代码
        System.out.println(conn);
    }
}
