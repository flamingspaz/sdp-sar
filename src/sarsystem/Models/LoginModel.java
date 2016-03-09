package sarsystem.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static sarsystem.Models.AISModel.teacherName;

public class LoginModel extends DBConnector {

    public static boolean loginTeacher(String username, String password) {
        String name = "";
        boolean exists = false;
        select("SELECT Name, Surname FROM Teacher WHERE Username='" + username + "' AND Password='" + password + "'");
        try {
            while (res.next()) {
                name = res.getString("Name") + " " + res.getString("Surname");
                exists = true;
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception er) {
            System.out.println(er);
        }
        return exists;
    }
    
    public static String loginStudent(String username, String password) {
        String sID = "";
        select("SELECT StudentID FROM Student WHERE Username='" + username + "' AND Password='" + password + "'");
        try {
            while (res.next()) {
                sID = res.getString("StudentID");
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception er) {
            System.out.println(er);
        }
        return sID;
    }
}
