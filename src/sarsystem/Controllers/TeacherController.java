package sarsystem.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import sarsystem.Models.PDAModel;

public class TeacherController {
    
    public boolean updateTable(String name, String surname, String IDlist[]){
        
        Connection con = null;
        boolean exists = false;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            Statement sta = con.createStatement();

            // getting the data back
            ResultSet res = sta.executeQuery("SELECT StudentID FROM Student WHERE Name='" + name + "' AND Surname='" + surname + "'"); // SQL query
            while (res.next()) {
                for (int i = 0; i < IDlist.length; i++) {
                    String IDFromList = IDlist[i];
                    String SQLID = "000"+res.getString("StudentID");
                    if (IDFromList.equals(SQLID)){
                        exists = true;
                    }
                }
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }        
        return exists;
    }
    
    public void takeRegister(String[] studentName){
        
    }
}
