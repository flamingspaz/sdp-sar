package sarsystem.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AISModel {
    
    
    // get session List
    public static Object[] sessionList() {
        // create table of content
        ArrayList list = new ArrayList();
        // create connetion
        Connection con = null;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            Statement sta = con.createStatement();
            // getting the data back
            ResultSet res = sta.executeQuery("SELECT * FROM Course"); // SQL query
            while (res.next()) {
                String course = res.getString("CourseID") + ", " + res.getString("CourseName") + ", " + res.getString("Time") + ", " + teacherName(res.getString("TeacherID")) + ", " + res.getString("SessionType");
                list.add(course);
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return list.toArray();
    }
    
    // get teacher name using their teacherID
    static String teacherName(String TeacherID) {
        String name = "";
        // create connetion
        Connection con = null;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            Statement sta = con.createStatement();

            // getting the data back
            ResultSet res = sta.executeQuery("SELECT * FROM Teacher WHERE TeacherID='" + TeacherID+"'"); // SQL query
            while (res.next()) {
                name = res.getString("Name") + " " + res.getString("Surname");
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return name;
    }
    
    // create a table of students for a session using courseID
    public static Object[][] studentTable(String CourseID){        
        Object data[][] = new Object[4][3];
        int i = 0;
        // create connetion
        Connection con = null;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            Statement sta = con.createStatement();

            // getting the data back
            ResultSet res = sta.executeQuery("SELECT Name, Surname FROM Student WHERE Course1='" + CourseID + "' OR Course2='" + CourseID + "' OR Course3='" + CourseID + "' OR Course4='" + CourseID + "'"); // SQL query
            while (res.next()) {
                data[i][0] = res.getString("Name");
                data[i][1] = res.getString("Surname");
                data[i][2] = new Boolean(false);
                i++;
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return data;
    }
    
    // Identify student name using student ID
    public static String studentName (String studentID){
        String name = "";
        Connection con = null;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            Statement sta = con.createStatement();

            // getting the data back
            ResultSet res = sta.executeQuery("SELECT Name, Surname FROM Student WHERE StudentID=" + studentID + ""); // SQL query
            while (res.next()) {
                name = res.getString("Name") + " " + res.getString("Surname");
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }        
        return name;
    }
}
