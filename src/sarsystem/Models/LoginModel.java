package sarsystem.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static sarsystem.Models.AISModel.teacherName;


public class LoginModel {
    
    
    public static boolean loginTeacher(String username, String password){
        String name = "";
        boolean exists = false;
        // create connetion
        Connection con = null;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            Statement sta = con.createStatement();
            // getting the data back
            ResultSet res = sta.executeQuery("SELECT Name, Surname FROM Teacher WHERE Username='" + username + "' AND Password='" + password + "'"); // SQL query
            while (res.next()) {
                name = res.getString("Name") + " " + res.getString("Surname");
                exists = true;
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exists;
    }
}
