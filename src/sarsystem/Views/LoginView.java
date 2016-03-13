package sarsystem.Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import sarsystem.Models.LoginModel;

public class LoginView extends JFrame implements ActionListener {

    JTextField loginTxt = new JTextField(20);
    JPasswordField passwordTxt = new JPasswordField(18);
    JButton loginBtn = new JButton("Login");

    public static void main(String[] args) {
        new LoginView();
    }

    public LoginView() {
        // Set windows properties
        setLayout(new FlowLayout());
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // Login box
        add(new JLabel("Login: "));
        add(loginTxt);
        loginTxt.addActionListener(this); // possible to try login there, may have mistyped password

        // Password box
        add(new JLabel("Password: "));
        add(passwordTxt);
        passwordTxt.addActionListener(this); // if user wishes to press return key to log in

        // Login Button
        add(loginBtn);
        loginBtn.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // object for login model        
        LoginModel login = new LoginModel();
        
        /*
        Check who is the user that will log in
        All login methods from the login model should return boolean (True, False)
        */
        
        // login teacher
        if (login.loginTeacher(loginTxt.getText(), passwordTxt.getText()) == true){
            if (loginTxt.getText().equals("admin")) {
                new CourseCoordinatorView();
                this.dispose(); // close window
            }
            else {
            new TeacherView();
            this.dispose(); // close window
            }
        }
        else if (!login.loginStudent(loginTxt.getText(), passwordTxt.getText()).equals("")){
            String sID = login.loginStudent(loginTxt.getText(), passwordTxt.getText());
            new StudentView(sID);
            this.dispose(); // close window
        }
        else {
            JOptionPane.showMessageDialog(null, "The Username or Password incorrect", "Warning", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }

     // login course coordinator
     // login student
}
