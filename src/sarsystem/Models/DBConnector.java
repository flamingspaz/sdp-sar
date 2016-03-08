package sarsystem.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnector {
    protected static ResultSet res = null;
    protected static Connection con = null;
    protected static Statement sta = null;
    
    static void select(String statement){        
        // create connection
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/AIS");
            sta = con.createStatement();
            // getting the data back
            res = sta.executeQuery(statement); // SQL query
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
