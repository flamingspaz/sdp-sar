package sarsystem.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;
import static sarsystem.Models.AISModel.teacherName;

public class LoginModel extends DBConnector {

    public static boolean loginUser(String username, String password) {
        String name = "";
        boolean exists = false;
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        System.out.println(pw_hash);
        select("SELECT users.name, users.surname, users.password_hash FROM users WHERE username='" + username + "'");
        try {
            while (res.next()) {
                if (BCrypt.checkpw(password, res.getString("password_hash"))) {
                name = res.getString("name") + " " + res.getString("surname");
                exists = true;   
                }
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
    
    public static boolean userIsTeacher(String username, String password) {
        String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        select("SELECT users.teacher, users.password_hash FROM users WHERE username='" + username + "'");
        try {
            while (res.next()) {
                if (res.getBoolean("teacher") && BCrypt.checkpw(password, res.getString("password_hash"))) {
                    return true;
                }
            }

        }
        catch (Exception er) {
            System.out.println(er);
        }
        
        return false;
    }
    
       public static int getUserID(String username, String password) {
        select("SELECT users.id, users.password_hash FROM users WHERE username='" + username + "'");
        try {
            while (res.next()) {
               return res.getInt("id");
            }
        }
        catch (Exception er) {
            System.out.println(er);
        }
        
        return 0;
    }

}
