package cn.itcast.jdbc;

import cn.itcast.utils.JDBCUtils;

import java.sql.*;

/**
 * JDBC快速入门
 */
public class JdbcDemo1 {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //1.导入驱动jar包
            //2.通过JDBC工具类，注册驱动和获取数据连接对象
            conn = JDBCUtils.getConnection();
            //3.定义sql语句
            String sql="select * from t_user";
            //4.获取执行sql的对象
            pstmt = conn.prepareStatement(sql);
            //5.执行sql
            rs = pstmt.executeQuery();
            //6.获取结果集
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString("username");
                String loginacct = rs.getString("loginacct");
                String password = rs.getString("userpswd");
                String email = rs .getString("email");
                System.out.println("id:"+id+"  username:"+name+"  loginacct:"+loginacct+"  userpswd:"+password+"  email:"+email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.close(rs,pstmt,conn);
    }
}
