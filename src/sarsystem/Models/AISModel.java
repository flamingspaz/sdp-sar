package sarsystem.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AISModel extends DBConnector {

    // get session List
    public static Object[] sessionList() {
        // create table of content
        ArrayList list = new ArrayList();        
        try {
            select("SELECT * FROM Course");
            while (res.next()) {
                String courseID = res.getString("CourseID");
                String courseName = res.getString("CourseName");
                String time = res.getString("Time");
                String teacherID = res.getString("TeacherID");
                String sessionType = res.getString("SessionType");
                String course = courseID + ", " + courseName + ", " + time + ", " + teacherName(teacherID) + ", " + sessionType;
                list.add(course);
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return list.toArray();
    }

    // get teacher name using their teacherID
    static String teacherName(String TeacherID) {
        String name = "";
        try {            
            // getting the data back
            select("SELECT * FROM Teacher WHERE TeacherID=" + TeacherID + "");
            while (res.next()) {
                name = res.getString("Name") + " " + res.getString("Surname");
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    // create a table of students for a session using courseID
    public static Object[][] studentTable(String CourseID) {
        Object data[][] = new Object[4][3];
        int i = 0;
        
        try {
            select("SELECT Name, Surname FROM Student WHERE Course1='" + CourseID + "' OR Course2='" + CourseID + "' OR Course3='" + CourseID + "' OR Course4='" + CourseID + "'"); // SQL query
            while (res.next()) {
                data[i][0] = res.getString("Name");
                data[i][1] = res.getString("Surname");
                data[i][2] = new Boolean(false);
                i++;
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    // Identify student name using student ID
    public static String studentName(String studentID) {
        String name = "";
        Connection con = null;
        try {
            select("SELECT Name, Surname FROM Student WHERE StudentID=" + studentID + "");
            while (res.next()) {
                name = res.getString("Name") + " " + res.getString("Surname");
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }
}
