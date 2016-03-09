package sarsystem.Views;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import sarsystem.Models.AISModel;
import static sarsystem.Models.AISModel.photoPath;
import static sarsystem.Models.AISModel.studentDetails;

public class StudentView extends JFrame implements ActionListener {

    JTextField nameField = new JTextField(10);
    JTextField surnameField = new JTextField(10);
    JTextField usernameField = new JTextField(10);
    JPasswordField passwordField = new JPasswordField(10);

    JButton editBtn = new JButton("Edit");
    JButton hideUnhide = new JButton("Unhide");

    public StudentView(String studentID) {
        // Set windows properties
        setLayout(new FlowLayout());
        setTitle("Student View");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(465, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        String studentName = AISModel.studentName(studentID);
        String[] nameSurname = studentName.split(" ");

        add(new JLabel("Name: "));
        add(nameField);
        nameField.setText(nameSurname[0]);
        nameField.setEditable(false);

        add(new JLabel("Surname: "));
        add(surnameField);
        surnameField.setText(nameSurname[1]);
        surnameField.setEditable(false);

        add(editBtn);
        editBtn.addActionListener(this);

        add(new JLabel("Username:"));
        add(usernameField);
        usernameField.setText(studentDetails(studentID)[10]);
        usernameField.setEditable(false);

        add(new JLabel("Password:"));
        add(passwordField);
        passwordField.setText(studentDetails(studentID)[11]);
        passwordField.setEditable(false);
        add(hideUnhide);
        hideUnhide.addActionListener(this);

        add(new JLabel("<html>Your Courses:<br>" + studentDetails(studentID)[6] + "<br>" + studentDetails(studentID)[7] + "<br>" + studentDetails(studentID)[8] + "<br>" + studentDetails(studentID)[9] + "</html>", SwingConstants.CENTER));
        ImageIcon photo;
        if (photoPath(studentID) != null) {
            // add Image
            photo = new ImageIcon(photoPath(studentID));
            Image img = photo.getImage();
            Image newimg = img.getScaledInstance(100, 120, java.awt.Image.SCALE_SMOOTH);
            photo = new ImageIcon(newimg);
        }
        else {
           // default Image
            photo = new ImageIcon("images/blank.jpg");
            Image img = photo.getImage();
            Image newimg = img.getScaledInstance(100, 120, java.awt.Image.SCALE_SMOOTH);
            photo = new ImageIcon(newimg); 
        }        
            add(new JLabel(photo));

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editBtn) {
            nameField.setEditable(true);
            surnameField.setEditable(true);
            passwordField.setEditable(true);
        } else if (e.getSource() == hideUnhide) {
            if (hideUnhide.getText().equals("Unhide")) {
                passwordField.setEchoChar((char) 0);  // unhide password
                hideUnhide.setText("Hide");
            } else {
                passwordField.setEchoChar('•'); // hide password
                hideUnhide.setText("Unhide");
            }
        }
    }
}
