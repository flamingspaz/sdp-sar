package sarsystem.Views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class CourseCoordinatorView extends JFrame implements ActionListener{
    
    public CourseCoordinatorView(){
        // Set windows properties
        setLayout(new FlowLayout());
        setTitle("Student View");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(465, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    
    }
}
