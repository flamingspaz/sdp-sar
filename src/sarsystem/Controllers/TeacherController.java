package sarsystem.Controllers;

import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JTable;
import sarsystem.Models.DBConnector;

public class TeacherController {

    public boolean updateTable(String name, String surname, String IDlist[]) {

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
                    String SQLID = "000" + res.getString("StudentID");
                    if (IDFromList.equals(SQLID)) {
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

    public void takeRegister(String StudentID, String CourseID, String Date, String Time, String SessionType) {
        int lastAttendenceID = Integer.parseInt(DBConnector.selectReturn("SELECT MAX(AttendenceID) FROM Attendence", "1"));
        lastAttendenceID++;
        DBConnector.insert("INSERT INTO Attendence (AttendenceID, StudentID, CourseID, Date, Time, SessionType) VALUES (" + lastAttendenceID + ", " + StudentID + ", '" + CourseID + "', '" + Date + "', '" + Time + "', '" + SessionType + "')");
    }

    public String getDate(String CourseID) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String thisWeek = "";

        switch (CourseID) {
            case "COMP1632":
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                thisWeek = sdf.format(c.getTime());
                break;
            case "COMP1562":
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                thisWeek = sdf.format(c.getTime());
                break;
            case "COMP1549":
                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                thisWeek = sdf.format(c.getTime());
                break;
            case "COMP1556":
                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                thisWeek = sdf.format(c.getTime());
                break;
        }
        return thisWeek;
    }

    public void printRegister(JTable table) {
        // http://stackoverflow.com/questions/14544013/how-to-print-a-jtable-object-in-the-java-application
        try {
            table.print(JTable.PrintMode.FIT_WIDTH, null, null); // Opens dialog where we choose printer and handles printing :)
        } catch (PrinterException ex) {
            System.err.print(ex);
        }

    }
}
