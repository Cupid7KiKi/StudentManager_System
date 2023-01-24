package yjy.dao;

import yjy.Utils.DBUtil;
import yjy.Model.Student;
import yjy.dao.impl.StudentImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IStudentDao implements StudentImpl {
    /*
     * 1.设计一个方法 2.要求传入两个参数 一个sql语句 一个参数 第一个参数sql语句模板 第二个参数为可变参数，设置语句参数值 3.返回值
     * 返回值为int，受影响的行数。
     * 调用示意图
     * 可变参数的本质是一个数组
     */

    public int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = DBUtil.getConnection();
            // 3.创建语句
            ps = conn.prepareStatement(sql);
            // 遍历参数
            for (int i = 0; i < params.length; i++) {
                // ps.setString(1, stu.getName());
                // ps.setInt(2, stu.getAge());
                ps.setObject(i + 1, params[i]);
            }
            // 4.执行语句
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            DBUtil.close(conn, ps, null);
        }
        return 0;
    }

    public void save(Student stu) {
        String sql = "insert into student(stuID,stuClass,stuName,stuSex,stuBirth,stuMajor) values (?,?,?,?,?,?)";
        this.executeUpdate(sql, stu.getStuID(), stu.getStuName(), stu.getStuClass(), stu.getStuSex(), stu.getStuBirth(), stu.getStuMajor());
    }

    public void delete(String sID) {
        String sql = "delete from student where stuID = ?";
        this.executeUpdate(sql, sID);
    }

    public void update(String sID, Student stu) {
        String sql = "update student set stuClass=?, stuName=?, stuSex=?,stuBirth=?,stuMajor=? where stuId =? ";
        this.executeUpdate(sql, stu.getStuClass(), stu.getStuName(), stu.getStuSex(),
                stu.getStuBirth(), stu.getStuMajor(), sID);
    }

    public Student getStu(String sID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = DBUtil.getConnection();
            // 3.创建语句
            String sql = "select * from student where stuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, sID);
            // 4.执行语句
            rs = ps.executeQuery();
            if (rs.next()) {
                Student stu = new Student(rs.getString("stuID"), rs.getString("stuClass"),
                        rs.getString("stuName"), rs.getString("stuSex"), rs.getString("stuBirth"),
                        rs.getString("stuMajor"));
                return stu;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    public List<Student> getAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = DBUtil.getConnection();
            // 3.创建语句
            String sql = "select * from student";
//            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            // 4.执行语句
            rs = ps.executeQuery();
            // 创建一个集合
            List<Student> list = new ArrayList<Student>();
            while (rs.next()) {
                Student stu = new Student(
                        rs.getString("stuID"),
                        rs.getString("stuClass"),
                        rs.getString("stuName"),
                        rs.getString("stuSex"),
                        rs.getString("stuBirth"),
                        rs.getString("stuMajor"));
                list.add(stu);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }
}
