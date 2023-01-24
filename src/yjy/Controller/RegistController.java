package yjy.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import yjy.Utils.DBUtil;
import yjy.Stage.main;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistController {

    @FXML
    private TextField RegistPassword;

    @FXML
    private TextField RegistID;

    @FXML
    private Button Regist_btn;

    @FXML
    private Button Return_btn;

    @FXML
    private TextField RegistUsername;

    @FXML
    private TextField ConfirmPassword;

    @FXML
    void Regist_userID(ActionEvent event) {
    }

    @FXML
    void Regist_password(ActionEvent event) {
    }

    @FXML
    void Regist_username(ActionEvent event) {
    }

    //实现注册功能(将用户填写的信息添加到数据库库表id中，增)
    @FXML
    void RegistButton(ActionEvent event) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            String sql1 = "select * from user where userID = ?";
            ps = conn.prepareStatement(sql1);
            ps.setString(1,RegistID.getText());
            rs = ps.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null,"该账户已存在！","信息",JOptionPane.INFORMATION_MESSAGE);
            }else{
                if(RegistID.getText() == "" || RegistPassword.getText() == "" || ConfirmPassword.getText() == "" || RegistUsername.getText() == ""){
                    JOptionPane.showMessageDialog(null,"请输入注册信息！","信息",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    if(!RegistPassword.getText().equals(ConfirmPassword.getText())){
                        System.out.println("密码需一致！");
                        JOptionPane.showMessageDialog(null,"密码需一致！","信息",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        stmt = conn.createStatement();
                        String sql = "insert into user values(" + "'"+RegistID.getText()+"'" + "," + "'"+RegistPassword.getText()+"'" + ","+ "'"+RegistUsername.getText()+"'" + ")";
                        int c1 = stmt.executeUpdate(sql);
                        if(c1>0){
                            RegistID.setText("");
                            RegistPassword.setText("");
                            ConfirmPassword.setText("");
                            RegistUsername.setText("");
                            JOptionPane.showMessageDialog(null,"注册成功！","信息",JOptionPane.INFORMATION_MESSAGE);
                            main.jumpTo("../Stage/Login.fxml","登录");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBUtil.close(conn,ps,rs);
        }
    }

    @FXML
    void Conferm_password(ActionEvent event) {
    }

    @FXML
    void ReturnLogin(ActionEvent event){
        main.jumpTo("../Stage/Login.fxml","登录");
    }

}
