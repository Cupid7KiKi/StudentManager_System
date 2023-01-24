package yjy.Controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import yjy.Model.Course;
import yjy.Model.Score;
import yjy.Model.Student;

import yjy.bgm.Music;
import yjy.dao.ICourseDao;
import yjy.dao.IScoreDao;
import yjy.dao.IStudentDao;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller1 implements Initializable {
    @FXML
    private MenuItem StopMusic;

    @FXML
    private TextField IUname;

    @FXML
    private Tab tab_stu;

    @FXML
    private Tab tab_course;

    @FXML
    private Tab tab_score;

    @FXML
    private TableColumn<Student, String> sClass = new TableColumn<>("班级");

    @FXML
    private TableColumn<Student, String> sSex = new TableColumn<>("性别");

    @FXML
    private TableColumn<Student, String> sName = new TableColumn<>("姓名");

    @FXML
    private TableColumn<Student, String> sMajor = new TableColumn<>("专业");

    @FXML
    private TableColumn<Student, String> sBirth = new TableColumn<>("出生日期");

    @FXML
    private TableColumn<Student, String> sID = new TableColumn<>("学号");

    @FXML
    private TableColumn<Course, String> cIDCol = new TableColumn<>("课程号");
    @FXML
    private TableColumn<Course, String> cMajorCol = new TableColumn<>("所属专业");
    @FXML
    private TableColumn<Course, String> cNameCol = new TableColumn<>("课程名称");
    @FXML
    private TableColumn<Course, String> cTypeCol = new TableColumn<>("课程类型");
    @FXML
    private TableColumn<Course, String> cStartTremCol = new TableColumn<>("开课学期");
    @FXML
    private TableColumn<Course, String> cPeriodCol = new TableColumn<>("学时数");
    @FXML
    private TableColumn<Course, String> cCreditCol = new TableColumn<>("学分");

    @FXML
    private TableColumn<Score, String> score_sIdCol = new TableColumn<>("学号");
    @FXML
    private TableColumn<Score, String> score_cIdCol = new TableColumn<>("课程号");
    @FXML
    private TableColumn<Score, String> scoreCol = new TableColumn<>("成绩");
    @FXML
    private TableColumn<Score, String> score_creditCol = new TableColumn<>("学分");

    @FXML
    private TableView<Student> studentTableView = new TableView<Student>();

    @FXML
    private TableView<Course> courseTableView = new TableView<Course>();

    @FXML
    private TableView<Score> scoreTableView = new TableView<Score>();

//    Student zk = new Student(66, "214", "zhangke", "nan", "000", "xingong");

    //初始化可视内容
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initStudent();// 默认显示学生表
        initCourse();//刷新课程表
        initScore();//刷新成绩表
         //设置标签与表对应
        setTabVisible(tab_stu, studentTableView);
        setTabVisible(tab_course, courseTableView);
        setTabVisible(tab_score, scoreTableView);
        IUname.setEditable(false);
        System.out.println();
        IUname.setText(LoginController.getUserName());
        IUname.setFocusTraversable(false);
    }

    // 学生表部分
    // 刷新显示学生表
    public void initStudent() {
//            sc = new Scene(root);
        sID.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        sClass.setCellValueFactory(new PropertyValueFactory<>("stuClass"));
        sName.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        sSex.setCellValueFactory(new PropertyValueFactory<>("stuSex"));
        sBirth.setCellValueFactory(new PropertyValueFactory<>("stuBirth"));
        sMajor.setCellValueFactory(new PropertyValueFactory<>("stuMajor"));

        List<Student> students = new IStudentDao().getAll();
        ObservableList<Student> data = FXCollections.observableArrayList();
        for (Student student : students) {
            data.add(student);
//            System.out.println(student);
        }
        //设置数据源
        studentTableView.setItems(data);
    }

    //添加学生
    @FXML
    void addStu_btn(ActionEvent event) {
        Dialog<StudentResults> dialog = new Dialog<>();
        dialog.setTitle("添加学生");
        dialog.setHeaderText("请在下面输入要添加的学生信息：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 60, 10, 10));

        TextField sID = new TextField();
        TextField sClass = new TextField();
        TextField sName = new TextField();
        TextField sSex = new TextField();
        TextField sBirth = new TextField();
        TextField sMajor = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("班级:"), 0, 1);
        grid.add(sClass, 1, 1);
        grid.add(new Label("姓名:"), 0, 2);
        grid.add(sName, 1, 2);
        grid.add(new Label("性别:"), 0, 3);
        grid.add(sSex, 1, 3);
        grid.add(new Label("出生日期:"), 0, 4);
        grid.add(sBirth, 1, 4);
        grid.add(new Label("所在专业:"), 0, 5);
        grid.add(sMajor, 1, 5);

        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                if (sID.getText() == "" || sClass.getText() == "" || sName.getText() == ""
                        || sSex.getText() == "" || sBirth.getText() == "" || sMajor.getText() == "") {
                    alert("错误提示", "请输入需要添加的学生信息！", null, Alert.AlertType.ERROR);
                    return null;
                }
                return new StudentResults(sID.getText(), sClass.getText(),
                        sName.getText(), sSex.getText(),
                        sBirth.getText(), sMajor.getText());
            }
            return null;
        });

        Optional<StudentResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((StudentResults results) -> {
            Student student = new IStudentDao().getStu(results.sID);
            if (student != null) {
                alert("失败提示", "学号为【" + results.sID + "】的学生数据已经存在，无法添加！", null, Alert.AlertType.ERROR);
            } else {
                // 保存信息到数据库
                new IStudentDao().save(new Student(results.sID, results.sClass, results.sName,
                        results.sSex, results.sBirth, results.sMajor));

                alert("成功提示", "成功保存学号为【" + results.sID + "】的学生数据！", null, Alert.AlertType.INFORMATION);
                initStudent(); // 刷新界面
            }
        });
    }

    //删除学生
    @FXML
    void deleteStu_btn(ActionEvent event) {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("删除学生");
        d.setHeaderText("请输入要删除的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Student student = new IStudentDao().getStu(result.get());
            if(null != student){
                System.out.println("删除学号为" + student.getStuID() +"的数据");
                new IStudentDao().delete(student.getStuID());
                alert("成功提示","成功删除学号为【" + student.getStuID() + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                initStudent();//刷新界面
            }else {
                alert("错误提示","没有该学生的记录，无法删除！",null, Alert.AlertType.ERROR);
            }
        }
    }

    //修改学生信息
    @FXML
    void updateStu_btn(ActionEvent event) {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("修改学生信息");
        d.setHeaderText("请输入要修改信息的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }

            Student student = new IStudentDao().getStu(result.get());
            if(null != student){
                Dialog<StudentResults> dialog = new Dialog<>();
                dialog.setTitle("学生数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField sID = new TextField(student.getStuID());
                TextField sClass = new TextField(student.getStuClass());
                TextField sName = new TextField(student.getStuName());
                TextField sSex = new TextField(student.getStuSex());
                TextField sBirth = new TextField(student.getStuBirth());
                TextField sMajor = new TextField(student.getStuMajor());

                grid.add(new Label("学号:"), 0, 0);
                grid.add(sID, 1, 0);
                grid.add(new Label("班级:"), 0, 1);
                grid.add(sClass, 1, 1);
                grid.add(new Label("姓名:"), 0, 2);
                grid.add(sName, 1, 2);
                grid.add(new Label("性别:"), 0, 3);
                grid.add(sSex, 1, 3);
                grid.add(new Label("出生日期:"), 0, 4);
                grid.add(sBirth, 1, 4);
                grid.add(new Label("所在专业:"), 0, 5);
                grid.add(sMajor, 1, 5);

                dialog.getDialogPane().setContent(grid);
                Optional<StudentResults> results = dialog.showAndWait();

                if(results.isPresent()){
                    Student stu = new Student(sID.getText(),sClass.getText(),
                            sName.getText(),sSex.getText(),
                            sBirth.getText(),sMajor.getText());
                    new IStudentDao().update(result.get(), stu);
                    alert("成功提示","成功修改学号为【" + student.getStuID() + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                    initStudent(); // 刷新界面
                }
            }else{
                alert("错误提示","没有该学生的记录，无法修改！",null, Alert.AlertType.ERROR);
            }
        }
    }

    //获取学生信息
    @FXML
    void getStu_btn(ActionEvent event) {
        TextInputDialog d = new TextInputDialog();
        d.setTitle("查询学生");
        d.setHeaderText("请输入要查询的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Student student = new IStudentDao().getStu(result.get());
            if(null != student){
                Dialog<StudentResults> dialog = new Dialog<>();
                dialog.setTitle("学生数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField sID = new TextField(student.getStuID());
                sID.setEditable(false);
                TextField sClass = new TextField(student.getStuClass());
                sClass.setEditable(false);
                TextField sName = new TextField(student.getStuName());
                sName.setEditable(false);
                TextField sSex = new TextField(student.getStuSex());
                sSex.setEditable(false);
                TextField sBirth = new TextField(student.getStuBirth());
                sBirth.setEditable(false);
                TextField sMajor = new TextField(student.getStuMajor());
                sMajor.setEditable(false);

                grid.add(new Label("学号:"), 0, 0);
                grid.add(sID, 1, 0);
                grid.add(new Label("班级:"), 0, 1);
                grid.add(sClass, 1, 1);
                grid.add(new Label("姓名:"), 0, 2);
                grid.add(sName, 1, 2);
                grid.add(new Label("性别:"), 0, 3);
                grid.add(sSex, 1, 3);
                grid.add(new Label("出生日期:"), 0, 4);
                grid.add(sBirth, 1, 4);
                grid.add(new Label("所在专业:"), 0, 5);
                grid.add(sMajor, 1, 5);

                dialog.getDialogPane().setContent(grid);
                dialog.showAndWait();
            }else {
                alert("错误提示","没有该学生的记录，无法查询！",null, Alert.AlertType.ERROR);
            }
        }
    }

    private static class StudentResults {
        String sID;
        String sClass;
        String sName;
        String sSex;
        String sBirth;
        String sMajor;

        public StudentResults(String sID, String sClass, String sName, String sSex, String sBirth, String sMajor) {
            this.sID = sID;
            this.sClass = sClass;
            this.sName = sName;
            this.sSex = sSex;
            this.sBirth = sBirth;
            this.sMajor = sMajor;
        }
    }

    // 课程表部分
    // 刷新显示课程表
    public void initCourse() {
        cIDCol.setCellValueFactory(new PropertyValueFactory<>("cID"));
        cMajorCol.setCellValueFactory(new PropertyValueFactory<>("cMajor"));
        cNameCol.setCellValueFactory(new PropertyValueFactory<>("cName"));
        cTypeCol.setCellValueFactory(new PropertyValueFactory<>("cType"));
        cStartTremCol.setCellValueFactory(new PropertyValueFactory<>("cStartTerm"));
        cPeriodCol.setCellValueFactory(new PropertyValueFactory<>("cPeriod"));
        cCreditCol.setCellValueFactory(new PropertyValueFactory<>("cCredit"));

        List<Course> courses = new ICourseDao().getAll();
        ObservableList<Course> data = FXCollections.observableArrayList();
        for (Course course : courses) {
            data.add(course);
//            System.out.println(course);
        }
        courseTableView.setItems(data);
    }

    //添加课程
    @FXML
    void addCourse_btn(ActionEvent event){
        Dialog<CourseResults> dialog = new Dialog<>();
        dialog.setTitle("添加课程");
        dialog.setHeaderText("请在下面输入要添加的课程信息：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 60, 10, 10));

        TextField cID = new TextField();
        TextField cMajor = new TextField();
        TextField cName = new TextField();
        TextField cType = new TextField();
        TextField cStartTerm = new TextField();
        TextField cPeriod = new TextField();
        TextField cCredit = new TextField();

        grid.add(new Label("课程号:"), 0, 0);
        grid.add(cID, 1, 0);
        grid.add(new Label("所属专业:"), 0, 1);
        grid.add(cMajor, 1, 1);
        grid.add(new Label("课程名称:"), 0, 2);
        grid.add(cName, 1, 2);
        grid.add(new Label("课程类型:"), 0, 3);
        grid.add(cType, 1, 3);
        grid.add(new Label("开课学期:"), 0, 4);
        grid.add(cStartTerm, 1, 4);
        grid.add(new Label("学时数:"), 0, 5);
        grid.add(cPeriod, 1, 5);
        grid.add(new Label("学分:"), 0, 6);
        grid.add(cCredit, 1, 6);

        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                if (cID.getText() == "" || cMajor.getText() == "" || cName.getText() == ""
                        || cType.getText() == "" || cStartTerm.getText() == "" || cPeriod.getText() == "" || cCredit.getText() == "") {
                    alert("错误提示", "请输入需要添加的学生信息！", null, Alert.AlertType.ERROR);
                    return null;
                }
                return new CourseResults(cID.getText(), cMajor.getText(),
                        cName.getText(), cType.getText(),
                        cStartTerm.getText(), cPeriod.getText(),cCredit.getText());
            }
            return null;
        });

        Optional<CourseResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((CourseResults results) -> {
            Course course = new ICourseDao().getCourse(results.cID);
            if (course != null) {
                alert("失败提示", "课程号为【" + results.cID + "】的课程数据已经存在，无法添加！", null, Alert.AlertType.ERROR);
            } else {
                // 保存信息到数据库
                new ICourseDao().save(new Course(results.cID, results.cMajor, results.cName,
                        results.cType, results.cStartTerm, results.cPeriod,results.cCredit));

                alert("成功提示", "成功保存课程号为【" + results.cID + "】的课程数据！", null, Alert.AlertType.INFORMATION);
                initCourse(); // 刷新界面
            }
        });
    }

    //删除课程
    @FXML
    void deleteCourse_btn (ActionEvent event){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("删除课程");
        d.setHeaderText("请输入要删除的课程号：");
        d.setContentText("课程号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Course course = new ICourseDao().getCourse(result.get());
            if(null != course){
                System.out.println("删除课程号为" + course.getcID() +"的数据");
                new ICourseDao().delete(course.getcID());
                alert("成功提示","成功删除课程号为【" + course.getcID() + "】的课程数据！",null, Alert.AlertType.INFORMATION);
                initCourse();//刷新界面
            }else {
                alert("错误提示","没有该课程的记录，无法删除！",null, Alert.AlertType.ERROR);
            }
        }
    }
    //修改课程
    @FXML
    void updateCourse_btn (ActionEvent event){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("修改课程信息");
        d.setHeaderText("请输入要修改信息的课程号：");
        d.setContentText("课程号:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }

            Course course = new ICourseDao().getCourse(result.get());
            if(null != course){
                Dialog<CourseResults> dialog = new Dialog<>();
                dialog.setTitle("课程数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField cID = new TextField(course.getcID());
                TextField cMajor = new TextField(course.getcMajor());
                TextField cName = new TextField(course.getcName());
                TextField cType = new TextField(course.getcType());
                TextField cStartTerm = new TextField(course.getcStartTerm());
                TextField cPeriod = new TextField(course.getcPeriod());
                TextField cCredit = new TextField(course.getcCredit());

                grid.add(new Label("课程号:"), 0, 0);
                grid.add(cID, 1, 0);
                grid.add(new Label("所属专业:"), 0, 1);
                grid.add(cMajor, 1, 1);
                grid.add(new Label("课程名称:"), 0, 2);
                grid.add(cName, 1, 2);
                grid.add(new Label("课程类型:"), 0, 3);
                grid.add(cType, 1, 3);
                grid.add(new Label("开课学期:"), 0, 4);
                grid.add(cStartTerm, 1, 4);
                grid.add(new Label("学时数:"), 0, 5);
                grid.add(cPeriod, 1, 5);
                grid.add(new Label("学分:"), 0, 6);
                grid.add(cCredit, 1, 6);

                dialog.getDialogPane().setContent(grid);
                Optional<CourseResults> results = dialog.showAndWait();

                if(results.isPresent()){
                    Course cou = new Course(cID.getText(),cMajor.getText(),cName.getText(),cType.getText(),cStartTerm.getText(),cPeriod.getText(),cCredit.getText());
                    new ICourseDao().update(result.get(), cou);
                    alert("成功提示","成功修改课程号为【" + course.getcID() + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                    initCourse(); // 刷新界面
                }
            }else{
                alert("错误提示","没有该课程号的记录，无法修改！",null, Alert.AlertType.ERROR);
            }
        }
    }
    //获取课程信息
    @FXML
    void getCourse_btn (ActionEvent event){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("查询课程");
        d.setHeaderText("请输入要查询的课程号：");
        d.setContentText("课程号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Course course = new ICourseDao().getCourse(result.get());
            if(null != course){
                Dialog<CourseResults> dialog = new Dialog<>();
                dialog.setTitle("课程数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField cID = new TextField(course.getcID());
                cID.setEditable(false);
                TextField cMajor = new TextField(course.getcMajor());
                cMajor.setEditable(false);
                TextField cName = new TextField(course.getcName());
                cName.setEditable(false);
                TextField cType = new TextField(course.getcType());
                cType.setEditable(false);
                TextField cStartTerm = new TextField(course.getcStartTerm());
                cStartTerm.setEditable(false);
                TextField cPeriod = new TextField(course.getcPeriod());
                cPeriod.setEditable(false);
                TextField cCredit = new TextField(course.getcCredit());
                cCredit.setEditable(false);

                grid.add(new Label("课程号:"), 0, 0);
                grid.add(cID, 1, 0);
                grid.add(new Label("所属专业:"), 0, 1);
                grid.add(cMajor, 1, 1);
                grid.add(new Label("课程名称:"), 0, 2);
                grid.add(cName, 1, 2);
                grid.add(new Label("课程类型:"), 0, 3);
                grid.add(cType, 1, 3);
                grid.add(new Label("开课日期:"), 0, 4);
                grid.add(cStartTerm, 1, 4);
                grid.add(new Label("学时数:"), 0, 5);
                grid.add(cPeriod, 1, 5);
                grid.add(new Label("学分:"), 0, 6);
                grid.add(cCredit, 1, 6);

                dialog.getDialogPane().setContent(grid);
                dialog.showAndWait();
            }else {
                alert("错误提示","没有该课程的记录，无法查询！",null, Alert.AlertType.ERROR);
            }
        }
    }

    private static class CourseResults{
        private String cID;
        private String cMajor;
        private String cName;
        private String cType;
        private String cStartTerm;
        private String cPeriod;
        private String cCredit;

        public CourseResults(String cID, String cMajor, String cName, String cType, String cStartTerm, String cPeriod, String cCredit) {
            this.cID = cID;
            this.cMajor = cMajor;
            this.cName = cName;
            this.cType = cType;
            this.cStartTerm = cStartTerm;
            this.cPeriod = cPeriod;
            this.cCredit = cCredit;
        }
    }

    //成绩表部分
    // 刷新显示成绩表
    public void initScore() {
        score_sIdCol.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        score_cIdCol.setCellValueFactory(new PropertyValueFactory<>("cID"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        score_creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));

        List<Score> scores = new IScoreDao().getAll();
        ObservableList<Score> data = FXCollections.observableArrayList();
        for (Score score : scores) {
            data.add(score);
//            System.out.println(score);
        }
        scoreTableView.setItems(data);
    }

    //添加分数
    @FXML
    void addScore_btn (ActionEvent event){
        Dialog<ScoreResults> dialog = new Dialog<>();
        dialog.setTitle("添加成绩");
        dialog.setHeaderText("请输入对应学号、课程号和成绩：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField cID = new TextField();
        TextField _score = new TextField();
        // TextField credit = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);
        grid.add(new Label("成绩:"), 0, 2);
        grid.add(_score, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new ScoreResults(sID.getText(),cID.getText(), _score.getText());
            }
            return null;
        });

        Optional<ScoreResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ScoreResults results) -> {
            Score score = new IScoreDao().get(results.stuID, results.cID);
            Course course = new ICourseDao().getCourse(results.cID);
            if(course == null){ // 课程不存在
                alert("失败提示", "该门课不存在，无法添加！", null, Alert.AlertType.ERROR);
                return;
            }
            if(score != null){ // 分数已经存在
                alert("失败提示","该学生的这门课已有成绩，无法添加！",null, Alert.AlertType.ERROR);
            }else{
                // 保存信息到数据库
                new IScoreDao().save(results.stuID, results.cID,
                        new Score(results.stuID, results.cID, results.score,
                                String.format("%.3s",Double.parseDouble(results.score) / 100 * Integer.parseInt(course.getcCredit()) + "")));
                // 50 / 100 *

                alert("成功提示","成功保存此门成绩！",null, Alert.AlertType.INFORMATION);
                initScore(); // 刷新分数页面
            }
        });
    }
    //删除分数
    @FXML
    void deleteScore_btn (ActionEvent event){
        Dialog<ScoreResults> dialog = new Dialog<>();
        dialog.setTitle("删除成绩");
        dialog.setHeaderText("请输入要对应的学号，课程号：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField cID = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new ScoreResults(sID.getText(),cID.getText());
            }
            return null;
        });

        Optional<ScoreResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ScoreResults results) -> {
            Score score = new IScoreDao().get(results.stuID, results.cID);
            if(score == null){ // 分数已经存在
                alert("失败提示","没有这门课，无法删除！",null, Alert.AlertType.ERROR);
            }else{
                // 数据库删除信息
                new IScoreDao().delete(results.stuID, results.cID);
                alert("成功提示","成功删除这门成绩！",null, Alert.AlertType.INFORMATION);
                initScore(); // 刷新分数页面
            }
        });
    }
    //修改分数
    @FXML
    void updateScore_btn (ActionEvent event){
        Dialog<ScoreResults> dialog = new Dialog<>();
        dialog.setTitle("修改成绩");
        dialog.setHeaderText("请输入对应学号、课程号和成绩：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField cID = new TextField();
        TextField _score = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);
        grid.add(new Label("成绩:"), 0, 2);
        grid.add(_score, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new ScoreResults(sID.getText(),cID.getText(), _score.getText());
            }
            return null;
        });

        Optional<ScoreResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ScoreResults results) -> {
            Score score = new IScoreDao().get(results.stuID, results.cID);
            Course course = new ICourseDao().getCourse(results.cID);
            if(score == null){ // 分数不存在
                alert("失败提示","成绩不存在，无法修改！",null, Alert.AlertType.ERROR);
            }else{
                // score != null;
                score.setCredit(String.format("%.3s",Double.parseDouble(results.score) / 100 * Integer.parseInt(course.getcCredit()) + ""));
                // 修改成绩
                new IScoreDao().update(results.stuID, score);

                alert("成功提示","成功修改成绩！",null, Alert.AlertType.INFORMATION);
                initScore(); // 刷新分数页面
            }
        });
    }

    private static class ScoreResults{
        private String stuID;
        private String cID;
        private String score;

        public ScoreResults(String stuID, String cID) {
            this.stuID = stuID;
            this.cID = cID;
        }

        public ScoreResults(String stuID, String cID, String score) {
            this.stuID = stuID;
            this.cID = cID;
            this.score = score;
        }
    }

    @FXML
    void About(ActionEvent event){
        alert("关于作者","本人是一名大二在校生，正处于学习阶段，不够完善有待理解！","感谢！", Alert.AlertType.INFORMATION);
    }

    private interface Task {
        void execute();
    }
    private void setTabVisible(Tab tab, TableView tableView){
        setTabAction(tab, new Task() {
            @Override
            public void execute() {
                if(tableView.equals(studentTableView)){
                    courseTableView.setVisible(false);
                    studentTableView.setVisible(true);
                    scoreTableView.setVisible(false);
                }
                else if(tableView.equals(courseTableView)){
                    studentTableView.setVisible(false);
                    courseTableView.setVisible(true);
                    scoreTableView.setVisible(false);
                }
                else if(tableView.equals(scoreTableView)){
                    studentTableView.setVisible(false);
                    courseTableView.setVisible(false);
                    scoreTableView.setVisible(true);
                }
            }
        });
    }
    private void setTabAction(Tab tab, Task task) {
        tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                task.execute();
            }
        });
    }

    /*
     * 检测ID合法
     */
    private boolean checkIdIllegal(String sID){
        if(sID.length() >= 20){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("输入的数据不合法！");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /*
     * 弹框
     */
    private void alert(String title, String content, String header, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void OpenMusic (ActionEvent event){
        Music.open();
    }

    @FXML
    void pauseMusic(ActionEvent event){
        Music.pause();
    }


}
