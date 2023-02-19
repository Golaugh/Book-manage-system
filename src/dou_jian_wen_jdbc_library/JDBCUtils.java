package dou_jian_wen_jdbc_library;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtils{

    public static Connection getConnection() throws Exception {
        //读取配置文件中的4个基本信息
        InputStream is1 = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.propertices");

        // FileInputStream fis=new FileInputStream("src//jdbc.propertices");
        Properties pro=new Properties();
        pro.load(is1);
        String user = pro.getProperty("user");
        String url = pro.getProperty("url");
        String password = pro.getProperty("password");
        String driver = pro.getProperty("driver");
        //加载驱动
        Class.forName(driver);

        //获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static void closeResource(Connection conn, Statement s){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            s.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResource(Connection conn, Statement s, ResultSet rs){
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            s.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




    //通用增删改操作
    public static void update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.propertices");
            Properties p = new Properties();
            p.load(is);
            //获取连接
            conn = JDBCUtils.getConnection();
            //预编译sql语句，返回Preparedstatement的实例
            ps = conn.prepareStatement(sql);

            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            //执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResource(conn, ps);
        }

    }

    //对任意一个表进行任意行数的数据查询
    public static <T> List<T> query(Class<T> clazz, String sql, Object...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();//获取结果集

            ResultSetMetaData rsmd = rs.getMetaData();//获取结果集的元数据

            int columnCount = rsmd.getColumnCount();//通过ResultSetMetaData获取结果集中的列数

            ArrayList<T> list = new ArrayList<>();
            while (rs.next()) {//rs有值返回true
                T t = clazz.newInstance();
                //处理一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);//获取每一个列具体值
                    // String columnName = rsmd.getColumnName(i + 1);//获取每个列的列名。（不推荐使用）
                    String columnLabel = rsmd.getColumnLabel(i + 1);//获取每个列的别名，没起别名默认取到列名
                    //给book对象指定的columnName属性，赋值为columnValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, value);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);

        }
        return null;
    }
}

