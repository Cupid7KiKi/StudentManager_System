package yjy.dao.impl;

import yjy.Model.Student;

import java.util.List;

public interface StudentImpl {
    //保存学生（增加）
    public void save (Student stu);

    //删除学生
    public void delete (String sID);

    //更改学生信息
    public void update (String sID, Student stu);

    //获取指定学生的信息
    public Student getStu(String sID);

    //获取所有学生信息
    public List<Student> getAll();
}
