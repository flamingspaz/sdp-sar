package sarsystem.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import sarsystem.Models.AISModel;
import sarsystem.Models.DBConnector;

public class CourseCoordinatorController {
    
    public static ArrayList<String> getSuggestions(){
        ArrayList<String> stuList = new ArrayList<String>();
        Connection con = null;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            Statement sta = con.createStatement();

            // getting the data back
            ResultSet res = sta.executeQuery("SELECT Name, Surname From Student"); // SQL query
            while (res.next()) {
                String name = res.getString("Name") + " " + res.getString("Surname");
                stuList.add(name);
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } 
        return stuList;
    }
    
    public static void addToSession(String studentID, String courseID){
        // check if courses are null
        Connection con = null;
        String[] course = new String[4];
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            Statement sta = con.createStatement();

            // getting the data back
            ResultSet res = sta.executeQuery("SELECT Course1, Course2, Course3, Course4 From Student WHERE StudentID=" + studentID); // SQL query
            while (res.next()) {
                course[0] = res.getString("Course1");
                course[1] = res.getString("Course2");
                course[2] = res.getString("Course3");
                course[3] = res.getString("Course4");
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } 
        if (course[0] == null){
            DBConnector.update("UPDATE Student SET Course1='" + courseID + "' WHERE StudentID=" + studentID);
        }
        else if (course[1] == null){
            DBConnector.update("UPDATE Student SET Course2='" + courseID + "' WHERE StudentID=" + studentID);
        }
        else if (course[2] == null){
            DBConnector.update("UPDATE Student SET Course3='" + courseID + "' WHERE StudentID=" + studentID);
        }
        else if (course[3] == null){
            DBConnector.update("UPDATE Student SET Course4='" + courseID + "' WHERE StudentID=" + studentID);
        }
        
    }
}
