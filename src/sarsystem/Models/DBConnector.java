package sarsystem.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnector {
    protected static ResultSet res = null;
    protected static Connection con = null;
    protected static Statement sta = null;
    
   public static void select(String statement){        
        // create connection
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:postgresql://pgdb.lithium.navalrp.co.uk/sdpdb?user=sdpuser&password=nzm6xDSFaoXd6zySB5NN");
            sta = con.createStatement();
            // getting the data back
            res = sta.executeQuery(statement); // SQL query
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public static void update(String statement){
        // create connection
        try {
            con = DriverManager.getConnection("jdbc:postgresql://pgdb.lithium.navalrp.co.uk/sdpdb?user=sdpuser&password=nzm6xDSFaoXd6zySB5NN");
            sta = con.createStatement(); 
            sta.executeUpdate(statement);
            res.close();
            sta.close();
            con.close();
        }        
         catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public static void insert(String statement){
        // create connection
        try {
            con = DriverManager.getConnection("jdbc:postgresql://pgdb.lithium.navalrp.co.uk/sdpdb?user=sdpuser&password=nzm6xDSFaoXd6zySB5NN");
            sta = con.createStatement(); 
            sta.executeUpdate(statement);
            res.close();
            sta.close();
            con.close();
        }        
         catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public static String selectReturn(String statement, String find){
        String result = "";
        Connection con = null;
        try {
            // establish connection
            con = DriverManager.getConnection("jdbc:postgresql://pgdb.lithium.navalrp.co.uk/sdpdb?user=sdpuser&password=nzm6xDSFaoXd6zySB5NN");
            Statement sta = con.createStatement();

            // getting the data back
            ResultSet res = sta.executeQuery(statement); // SQL query
            while (res.next()) {
                result = res.getString(find);
            }
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }  
        return result;
    }
}
