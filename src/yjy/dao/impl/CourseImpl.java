package yjy.dao.impl;

import yjy.Model.Course;

import java.util.List;

public interface CourseImpl {
    //保存课程（增加课程）
    public void save(Course course);

    //删除课程
    public void delete(String cId);

    //修改课程
    public void update(String cId, Course course);

    //获取指定课程
    public Course getCourse(String cId);

    //获取所有课程
    public List<Course> getAll();
}
