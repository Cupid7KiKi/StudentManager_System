package yjy.Utils;

import java.sql.*;

/*
JDBC工具类 简化JDBC编程
 */
public class DBUtil {
    /*
    工具类中的构造方法都是私有的。
    因为工具类当中的方法都是静态的，不需要new对象，直接采用类名调用。
     */
    private DBUtil(){}

    /*
    静态代码块在类加载时执行，并且只执行一次，性能得到提升。
     */
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    获取数据库连接对象
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studentmanager","root","YJY020908");
    }
//    public List<Student> getAll() {
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            // 1.加载驱动
//            // 2.连接数据库
//            conn = DBUtil.getConnection();
//            // 3.创建语句
//            String sql = "select * from student";
//            System.out.println(sql);
//            ps = conn.prepareStatement(sql);
//            // 4.执行语句
//            rs = ps.executeQuery();
//            // 创建一个集合
//            List<Student> list = new ArrayList<Student>();
//            while (rs.next()) {
//                Student stu = new Student(
//                        rs.getString("stuID"),
//                        rs.getString("stuClass"),
//                        rs.getString("stuName"),
//                        rs.getString("stuSex"),
//                        rs.getString("stuBirth"),
//                        rs.getString("stuMajor"));
//                list.add(stu);
//            }
//            return list;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            DBUtil.close(conn, ps, rs);
//        }
//        return null;
//    }
    /*
    释放资源
     */
    public static void close(Connection conn, Statement ps, ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(ps != null){
            try{
                ps.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(conn != null){
            try{
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
