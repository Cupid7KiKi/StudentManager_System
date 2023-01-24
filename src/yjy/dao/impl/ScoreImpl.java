package yjy.dao.impl;

import yjy.Model.Score;

import java.util.List;

public interface ScoreImpl {
    /*
     * 增加学生成绩
     */
    public void save(String sId,String cId, Score score);
    /*
     * 删除学生成绩
     */
    public void delete(String sId, String cId);
    /*
     * 更新学生成绩
     */
    public void update(String sId, Score score);
    /*
     * 根据学号和课程号获取学生单门课程成绩
     */
    public Score get(String sId, String cId);
    /*
     * 获取全部学生成绩
     */
    public List<Score> getAll();
}
