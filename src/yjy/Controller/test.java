package yjy.Controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import yjy.Model.Student;
import yjy.dao.IStudentDao;

import java.util.List;

public class test extends Application {
    private static final TableView<Student> table = new TableView<Student>();
    private final ObservableList<Student> data = FXCollections.observableArrayList(
            new Student("66","214","zhangke","nan","000","xingong")
    );

    @Override
    public void start(Stage stage) {
        //创建根面板
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 500);
        //创建表格的列
        TableColumn<Student, String> sidCol = new TableColumn<>("学号");
        TableColumn<Student, String> sclCol = new TableColumn<>("班级");
        TableColumn<Student, String> snaCol = new TableColumn<>("姓名");
        TableColumn<Student, String> ssexCol = new TableColumn<>("性别");
        TableColumn<Student, String> sbirCol = new TableColumn<>("出生日期");
        TableColumn<Student, String> scoCol = new TableColumn<>("专业");
        //将表格的列和类的属性进行绑定
        sidCol.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        sclCol.setCellValueFactory(new PropertyValueFactory<>("stuClass"));
        snaCol.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        ssexCol.setCellValueFactory(new PropertyValueFactory<>("stuSex"));
        sbirCol.setCellValueFactory(new PropertyValueFactory<>("stuBirth"));
        scoCol.setCellValueFactory(new PropertyValueFactory<>("stuMajor"));
        List<Student> students = new IStudentDao().getAll();
        ObservableList<Student> data = FXCollections.observableArrayList();
        for (Student student : students) {
            data.add(student);
            System.out.println(students);
        }
        Student student = new Student("66","214","zhangke","nan","000","xingong");
        data.add(student);
        //设置可编辑
        table.setEditable(true);
        //将列添加到TableView中
        table.getColumns().addAll(sidCol,sclCol,snaCol,ssexCol,sbirCol,scoCol);
        //设置数据源
        table.setItems(data);
        //将表格添加至根面板
        root.setCenter(table);
        //为舞台设置场景
        stage.setScene(scene);
        //场景显示
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
