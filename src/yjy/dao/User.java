package yjy.dao;

import yjy.Controller.LoginController;
import yjy.Utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    public String a = "";
//    public String getusername(){
//
//    }

    public static void main(String[] args) {
        LoginController l =new LoginController();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBUtil.getConnection();
            String sql = "select username from user where userID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,l.iname);
            System.out.println(l.iname);
            rs = ps.executeQuery();
            if(rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){

        }
    }
}
