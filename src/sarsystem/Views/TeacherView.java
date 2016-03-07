package sarsystem.Views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import sarsystem.Models.PDAModel;
import javax.swing.table.DefaultTableModel;
import sarsystem.Controllers.TeacherController;
import sarsystem.Models.AISModel;

public class TeacherView extends JFrame implements ActionListener {

    JMenuBar menuBar = new JMenuBar();
    JMenu pdaMenu = new JMenu("PDA");
    JMenu exit = new JMenu("Exit");
    JMenuItem readFromPDA = new JMenuItem("Read From PDA", KeyEvent.VK_N);
    JMenuItem logout = new JMenuItem("Logout", KeyEvent.VK_N);
    JComboBox sessionList;
    JTable table;    
    JScrollPane scrollPane;
    String[] columnNames = {"First Name", "Last Name", "Attented"};
    DefaultTableModel model;
    JButton registerBtn = new JButton("Take Register");

    public TeacherView() {
        // Set windows properties
        setLayout(new FlowLayout());
        setTitle("Teacher View");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        // Add main menu
        pdaMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(pdaMenu);
        menuBar.add(exit);
        setJMenuBar(menuBar);

        // Add sub menu to PDA
        pdaMenu.add(readFromPDA);
        readFromPDA.addActionListener(this);

        // Add sub menu to Exit
        exit.add(logout);
        logout.addActionListener(this);

        // Session drop down menu
        add(new JLabel("Session:"));
        sessionList = new JComboBox(AISModel.sessionList());
        add(sessionList);
        sessionList.addActionListener(this);

        // Add register table
        // http://stackoverflow.com/questions/7391877/how-to-add-checkboxes-to-jtable-swing
        // add cheboxes to jtable

        model = new DefaultTableModel(AISModel.studentTable("COMP1549"), columnNames);
        table = new JTable(model) {
            private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
        table.getTableHeader().setReorderingAllowed(false);  // http://stackoverflow.com/questions/17641123/jtable-disable-user-column-dragging

        add(new JLabel("                                                                                                       "));
        add(registerBtn);
        /*table = new JTable(data, columnNames);
         add(new JScrollPane(table)); */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
         // check which session is selected and load students for this session
        int choice = sessionList.getSelectedIndex();
        switch (choice) {//check for a match
            case 0:
                model = new DefaultTableModel(AISModel.studentTable("COMP1549"), columnNames);
                table.setModel(model); // change the session
                break;
            case 1:
                model = new DefaultTableModel(AISModel.studentTable("COMP1556"), columnNames);
                table.setModel(model); // change the session
                break;
            case 2:
                model = new DefaultTableModel(AISModel.studentTable("COMP1562"), columnNames);
                table.setModel(model); // change the session
                break;
            case 3:
                model = new DefaultTableModel(AISModel.studentTable("COMP1632"), columnNames);
                table.setModel(model); // change the session
                break;
        }
        
        
        // Check which event has been triggered
        if (e.getSource() == logout) { // logout triggered event
            this.dispose(); // close window
            new LoginView();
        } else if (e.getSource() == readFromPDA) { // reading from PDA event
            PDAModel p = new PDAModel();
            TeacherController tc = new TeacherController();
            // check if the file exists
            if (p.isPDAPluggedIn() == true) {                  
            String [] IDlist = p.getIDList();
                // loop through table
                for (int i = 0; i < table.getRowCount(); i++) {
                        String name = ""+model.getValueAt(i, 0);
                        String surname = ""+model.getValueAt(i, 1);
                        if (tc.updateTable(name, surname, IDlist) == true){
                            model.setValueAt(new Boolean(true), i, 2);
                        }
                }
            } else {
                JOptionPane.showMessageDialog(null, "File not found! Please check PDA connection", "Warning", JOptionPane.ERROR_MESSAGE); // the file does not exist, thor an error
            }
        }
       
    }

}
