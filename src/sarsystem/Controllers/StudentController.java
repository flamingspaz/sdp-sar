/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sarsystem.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import sarsystem.Models.AISModel;

/**
 *
 * @author bt402
 */
public class StudentController {
    public static String getStatistics(String studentID, String CourseID){
        int numOfAttended = 0;
        Connection con = null;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:postgresql://pgdb.lithium.navalrp.co.uk/sdpdb?user=sdpuser&password=nzm6xDSFaoXd6zySB5NN");
            Statement sta = con.createStatement();

            // getting the data back
            ResultSet res = sta.executeQuery("SELECT * FROM Attendence WHERE StudentID=" + studentID + " AND CourseID='" + CourseID + "'"); // SQL query
            while (res.next()) {
                numOfAttended++;
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }  
        String startDate = AISModel.getCourseStartDate(CourseID);
        String att = " attented " + (calulateStats(numOfAttended, startDate, CourseID)*100) + "%";
        return att;
    }
    
    private static double calulateStats(int attented, String startDate, String CourseID){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String thisWeek = "";
        //System.out.println(sdf.format(c.getTime())); //this weeks date
        int noOfSessions = 0;
        double stat = 0;
        if (CourseID == null){
            CourseID = "N/A";
        }
        
        
        switch (CourseID){
            case "COMP1632":
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                thisWeek = sdf.format(c.getTime());
                try {
                c.setTime(sdf.parse(startDate));
                while (!thisWeek.equals(startDate)){
                     noOfSessions++;
                     c.add(Calendar.DATE, 7);
                     startDate = sdf.format(c.getTime());
                }                
                }
                catch (Exception e){
                    System.out.println(e);
                }
                stat = (double)attented/(double)noOfSessions;
            break;
            case "COMP1562":
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                thisWeek = sdf.format(c.getTime());
                try {
                c.setTime(sdf.parse(startDate));
                while (!thisWeek.equals(startDate)){
                     noOfSessions++;
                     c.add(Calendar.DATE, 7);
                     startDate = sdf.format(c.getTime());
                }                
                }
                catch (Exception e){
                    System.out.println(e);
                }
                stat = (double)attented/(double)noOfSessions;
            break;
            case "COMP1549":
                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                thisWeek = sdf.format(c.getTime());
                try {
                c.setTime(sdf.parse(startDate));
                while (!thisWeek.equals(startDate)){
                     noOfSessions++;
                     c.add(Calendar.DATE, 7);
                     startDate = sdf.format(c.getTime());
                }                
                }
                catch (Exception e){
                    System.out.println(e);
                }
                stat = (double)attented/(double)noOfSessions;
            break;
            case "COMP1556":
                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                thisWeek = sdf.format(c.getTime());
                try {
                c.setTime(sdf.parse(startDate));
                while (!thisWeek.equals(startDate)){
                     noOfSessions++;
                     c.add(Calendar.DATE, 7);
                     startDate = sdf.format(c.getTime());
                }                
                }
                catch (Exception e){
                    System.out.println(e);
                }
                stat = (double)attented/(double)noOfSessions;
            break;
            case "N/A":
                stat = 0.0;
            default:
                stat = 0.0;
        }
        String df =  String.format("%.2f", stat);
        return Double.parseDouble(df);
    }
}
