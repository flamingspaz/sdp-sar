package sarsystem.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AISModel extends DBConnector {
    
    // get studentID with name
    
    public static String studentID(String name, String surname){
        String sID = "";
        try {
            // getting the data back
            select("SELECT StudentID FROM Student WHERE Name='" + name + "' AND Surname='" + surname + "'");
            while (res.next()) {
                sID = res.getString("StudentID");
            }
//            res.close();
//            sta.close();
//            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return sID;
    }
    
    

    // get session List
    public static Object[] sessionList() {

        ResultSet res = null;
        Connection con = null;
        Statement sta = null;
        // create table of content
        ArrayList list = new ArrayList();
        try {
            //select("SELECT * FROM Course");

            // establish connection
            con = DriverManager.getConnection("jdbc:postgresql://pgdb.lithium.navalrp.co.uk/sdpdb?user=sdpuser&password=nzm6xDSFaoXd6zySB5NN");
            sta = con.createStatement();
            // getting the data back
            res = sta.executeQuery("SELECT courses.name, courses.id, programmes.name AS programme_name, users.name AS programme_leader FROM programmes, courses, users"); // SQL query

            while (res.next()) {
                String courseID = res.getString("id");
                String courseName = res.getString("name");
                String teacherID = res.getString("leader_id");
                String course = courseID + ", " + courseName + ", " + teacherName(teacherID);
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
            select("SELECT users.name FROM users WHERE id =" + TeacherID + "");
            while (res.next()) {
                name = res.getString("Name") + " " + res.getString("Surname");
            }
//            res.close();
//            sta.close();
//            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }

    // create a table of students for a session using courseID
    public static Object[][] studentTable(String CourseID) {
        Object data[][] = new Object[5][3];
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
        System.out.println(studentID);
        try {
            select("SELECT users.name, users.surname FROM users WHERE id = " + studentID + "");
            while (res.next()) {
                name = res.getString("name") + " " + res.getString("surname");
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

    public static String[] studentDetails(String studentID) {
        String[] info = new String[12];
        try {
            select("SELECT users.id, users.dob, users.tempreg, users.permreg, users.photograph, programmes.name AS programme_name, users.username, users.password_hash FROM programmes, users WHERE users.id = " + studentID);
            while (res.next()) {
                info[0] = res.getString("id");
                info[1] = res.getString("dob");
                info[2] = res.getString("tempreg");
                info[3] = res.getString("permreg");
                info[4] = res.getString("photograph");
                info[5] = res.getString("programme_name");
                info[6] = res.getString("username");
                info[7] = res.getString("password_hash");
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return info;
    }

       public static String[] studentCourses(String studentID) {
        String[] info = new String[12];
        try {
            select("SELECT users.id, users.dob, users.tempreg, users.permreg, users.photograph, programmes.name AS programme_name, users.username, users.password_hash FROM programmes, users WHERE users.id = " + studentID);
            while (res.next()) {
                info[0] = res.getString("id");
                info[1] = res.getString("dob");
                info[2] = res.getString("tempreg");
                info[3] = res.getString("permreg");
                info[4] = res.getString("photograph");
                info[5] = res.getString("programme_name");
                info[6] = res.getString("username");
                info[7] = res.getString("password_hash");
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return info;
    }
    
    
    public static String photoPath(String studentID) {
        String path = "";
        try {
            select("SELECT Photograph FROM Student WHERE StudentID=" + studentID);
            while (res.next()) {
                path = res.getString("Photograph");
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return path;
    }

    public static String getCourseStartDate(String CourseID) {
        String dateString = "";
        String formattedDate = "";
        try {
            select("SELECT StartDate FROM Course WHERE CourseID='" + CourseID + "'");
            while (res.next()) {
                dateString = res.getString("StartDate");
            }
            // close DB connection
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
        Date date = originalFormat.parse(dateString);
        formattedDate = targetFormat.format(date);  // 20120821
        }
        catch (Exception e ){
            System.out.println(e);
        }

        return formattedDate;
    }
}
