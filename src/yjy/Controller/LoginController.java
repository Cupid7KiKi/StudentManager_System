package yjy.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import yjy.Utils.DBUtil;
import yjy.Stage.main;
import yjy.bgm.Music;

import javax.swing.*;
import java.sql.*;

public class LoginController {
    @FXML
    private Label tip;

    @FXML
    private Button Login_btn;

    @FXML
    private TextField userName;

    public static String iname;

    @FXML
    private Button Resgist_btn;

    @FXML
    private TextField passwordName;

    boolean isTrue = false;


    //登录按钮，实现账号密码比对功能
    @FXML
    public void LoginButton(ActionEvent event) {
        tip.setText("正在尝试登录。。。");
        String loginName = userName.getText();
        String loginPwd = passwordName.getText();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
//        System.out.println(loginName +" "+ loginPwd);
        //获取连接
        try{
            conn = DBUtil.getConnection();
            String sql = "select * from user where BINARY userID = ? and BINARY password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,loginName);
            ps.setString(2,loginPwd);
//            System.out.println(ps);
            rs = ps.executeQuery();
            if(loginName == "" || loginPwd == ""){
                JOptionPane.showMessageDialog(null,"请输入账户或密码！","信息",JOptionPane.INFORMATION_MESSAGE);
            }else{
                if(rs.next()){
                    isTrue = true;
                    iname = rs.getString(3);
                    System.out.println(iname);
                    JOptionPane.showMessageDialog(null,"登录成功！","信息",JOptionPane.INFORMATION_MESSAGE);
                    Music.open();
                    main.jumpTo("../Stage/ZhuYeMian.fxml","学生管理系统");
                }else{
                    JOptionPane.showMessageDialog(null,"账户或密码错误！","信息",JOptionPane.INFORMATION_MESSAGE);
                    passwordName.setText("");
                }
            }
            System.out.println(isTrue ? "登录成功！":"登录失败...");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放资源
            DBUtil.close(conn,ps,null);
        }
    }

    //更换到注册界面
    @FXML
    public void ResgistButton(ActionEvent event) {
        main.jumpTo("../Stage/Regist.fxml","注册界面");
    }

    public static String getUserName() {
        return iname;
    }

//    public String getuserName() {
//        return userName.getText();
//    }
}

