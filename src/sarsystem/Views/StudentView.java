package sarsystem.Views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StudentView extends JFrame implements ActionListener {
    
    public StudentView(){
    // Set windows properties
        setLayout(new FlowLayout());
        setTitle("Student View");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        
    }
}
