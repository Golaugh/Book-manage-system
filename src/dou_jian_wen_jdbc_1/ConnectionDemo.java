package practice.dou_jian_wen_jdbc_1;

import java.sql.*;

public class ConnectionDemo {
    public static void main(String[] args) {

        //连接的数据库的路径
        String url  = "jdbc:sqlserver://localhost:1433;databaseName=studentdb";
        //登录数据库的用户名
        String user = "sa";
        //登录数据库的密码
        String password = "sa";
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            System.out.println("找不到驱动类");
        }
        try {
            //获取连接
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！");
        } catch (SQLException e2) {
            e2.printStackTrace();
            System.out.println("数据库连接失败！");
        }
        try {
            Statement stmt = conn.createStatement();
            String sql = "select * from student where name="+"'"+"王莉"+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("sex"));
                System.out.println(rs.getString("classname"));
            }
            System.out.println("查询完毕！");
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("修改记录失败！");
        }
        // 关闭资源
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
















        /*
       // 1.加载数据库驱动

        // 2.获取数据库连接




        */

//增加记录
/*

*/

//删除记录
        /*
        try {
            Statement stmt = conn.createStatement();
            String sql = "delete student where id=";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("增加记录失败！");
        }
        */