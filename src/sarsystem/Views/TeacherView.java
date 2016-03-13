package sarsystem.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import sarsystem.Models.PDAModel;
import javax.swing.table.DefaultTableModel;
import static sarsystem.Controllers.CourseCoordinatorController.getSuggestions;
import static sarsystem.Controllers.StudentController.getStatistics;
import sarsystem.Controllers.TeacherController;
import sarsystem.Models.AISModel;
import static sarsystem.Models.AISModel.studentDetails;
import static sarsystem.Models.AISModel.studentID;

public class TeacherView extends JFrame implements ActionListener {

    // JXDatePicker 
    JMenuBar menuBar = new JMenuBar();
    JMenu pdaMenu = new JMenu("PDA");
    JMenu printMenu = new JMenu("Print");
    JMenu exit = new JMenu("Exit");
    JMenuItem readFromPDA = new JMenuItem("Read From PDA", KeyEvent.VK_N);
    JMenuItem logout = new JMenuItem("Logout", KeyEvent.VK_N);
    JMenuItem printItem = new JMenuItem("Print register", KeyEvent.VK_N);
    JComboBox sessionList;
    JTable table;
    JScrollPane scrollPane;
    String[] columnNames = {"First Name", "Last Name", "Attented"};
    DefaultTableModel model;
    JButton registerBtn = new JButton("Take Register");
    JTextArea message = new JTextArea();
    JTextArea studentAttendenceArea = new JTextArea(10, 20);
    JComboBox<String> combo;

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
        menuBar.add(printMenu);
        menuBar.add(exit);
        setJMenuBar(menuBar);

        // Add sub menu to PDA
        pdaMenu.add(readFromPDA);
        readFromPDA.addActionListener(this);

        // Add sub menu to Exit
        exit.add(logout);
        logout.addActionListener(this);

        // Add sub menu to Print
        printMenu.add(printItem);
        printItem.addActionListener(this);

        // Session drop down menu
        add(new JLabel("Session:"));
        sessionList = new JComboBox(AISModel.sessionList());
        add(sessionList);
        sessionList.addActionListener(this);

        // Add register table
        // http://stackoverflow.com/questions/7391877/how-to-add-checkboxes-to-jtable-swing
        // add cheboxes to jtable
        model = new DefaultTableModel(AISModel.studentTable("COMP1632"), columnNames);
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
        registerBtn.addActionListener(this);
        add(message);
        /*table = new JTable(data, columnNames);
         add(new JScrollPane(table)); */

        // add autocomplete box
        String[] nameList = getSuggestions().toArray(new String[getSuggestions().size()]); // get name of list from DB

        combo = makeComboBox(nameList);
        combo.setEditable(true);
        combo.setSelectedIndex(-1);
        JTextField field = (JTextField) combo.getEditor().getEditorComponent();
        field.setText("");
        field.addKeyListener(new ComboKeyHandler(combo));
        JPanel p = new JPanel(new BorderLayout());
        //combo.addActionListener(this);
        p.add(combo, BorderLayout.NORTH);

        add(new JLabel("Student Name: "));
        add(p);

        // add text area
        add(studentAttendenceArea);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sessionList) {
            // check which session is selected and load students for this session
            int choice = sessionList.getSelectedIndex();
            switch (choice) {//check for a match
                case 0:
                    model = new DefaultTableModel(AISModel.studentTable("COMP1632"), columnNames);
                    table.setModel(model); // change the session
                    break;
                case 1:
                    model = new DefaultTableModel(AISModel.studentTable("COMP1549"), columnNames);
                    table.setModel(model); // change the session
                    break;
                case 2:
                    model = new DefaultTableModel(AISModel.studentTable("COMP1562"), columnNames);
                    table.setModel(model); // change the session
                    break;
                case 3:
                    model = new DefaultTableModel(AISModel.studentTable("COMP1556"), columnNames);
                    table.setModel(model); // change the session
                    break;
            }
        }

        TeacherController tc = new TeacherController();
        // Check which event has been triggered
        if (e.getSource() == logout) { // logout triggered event
            this.dispose(); // close window
            new LoginView();
        } else if (e.getSource() == readFromPDA) { // reading from PDA event
            PDAModel p = new PDAModel();

            // check if the file exists
            if (p.isPDAPluggedIn() == true) {
                String[] IDlist = p.getIDList();
                // loop through table
                for (int i = 0; i < table.getRowCount(); i++) {
                    String name = "" + model.getValueAt(i, 0);
                    String surname = "" + model.getValueAt(i, 1);
                    if (tc.updateTable(name, surname, IDlist) == true) {
                        model.setValueAt(new Boolean(true), i, 2);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "File not found! Please check PDA connection", "Warning", JOptionPane.ERROR_MESSAGE); // the file does not exist, thor an error
            }
        } else if (e.getSource() == registerBtn) {
            for (int i = 0; i < table.getRowCount(); i++) {
                try {
                    boolean attended = (boolean) model.getValueAt(i, 2);
                    if (attended == true) {
                        String name = "" + model.getValueAt(i, 0);
                        String surname = "" + model.getValueAt(i, 1);
                        String studentID = studentID(name, surname);
                        String selectedCourse = sessionList.getSelectedItem().toString();
                        String[] splitC = selectedCourse.split(","); // split course
                        String courseID = splitC[0];
                        String date = tc.getDate(courseID);
                        String Time = splitC[2];
                        String SessionType = splitC[4];
                        // format date and time '03/09/2016', '10:00'

                        final String OLD_FORMAT = "dd/MM/yyyy";
                        final String NEW_FORMAT = "MM/dd/yyyy";
                        final String OLD_TIME = "HH:mm:ss";
                        final String NEW_TIME = "HH:mm";

                        // Format Date & Time for SQL statement
                        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
                        Date d = sdf.parse(date);
                        sdf.applyPattern(NEW_FORMAT);
                        String newDateString = sdf.format(d);

                        sdf = new SimpleDateFormat(OLD_TIME);
                        d = sdf.parse(Time);
                        sdf.applyPattern(NEW_TIME);
                        String newTime = sdf.format(d);

                        tc.takeRegister(studentID, courseID, newDateString, newTime, SessionType);
                        message.setText("Register Successfully taken!");
                        Color c = new Color(0f, 0f, 0f, 0f); // make colour transparent looks nicer :)
                        message.setBackground(c);
                        message.setEditable(false);
                    }
                } catch (Exception er) {
                    // ignore it
                }
            }

        } else if (e.getSource() == printItem) {
            try {
                tc.printRegister(table);  // NOT TESTED!!!
            }
                catch (Exception p){
                System.err.print(p);
            }
            }

        }
        // AutoComplete code
        // http://java-swing-tips.blogspot.co.uk/2009/01/create-auto-suggest-jcombobox.html
    private static JComboBox<String> makeComboBox(String... model) {
        return new JComboBox<String>(model);
    }

    private static JPanel makeHelpPanel() {
        JPanel lp = new JPanel(new GridLayout(2, 1, 2, 2));
        lp.add(new JLabel("Char: show Popup"));
        lp.add(new JLabel("ESC: hide Popup"));

        JPanel rp = new JPanel(new GridLayout(2, 1, 2, 2));
        rp.add(new JLabel("RIGHT: Completion"));
        rp.add(new JLabel("ENTER: Add/Selection"));

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Help"));

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy = 0;
        c.weighty = 1d;

        c.weightx = 1d;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        p.add(lp, c);
        c.gridx = 2;
        p.add(rp, c);

        c.insets = new Insets(0, 5, 0, 5);
        c.weightx = 0d;
        c.gridx = 1;
        p.add(new JSeparator(JSeparator.VERTICAL), c);

        return p;
    }

    class ComboKeyHandler extends KeyAdapter {

        private final JComboBox<String> comboBox;
        private final List<String> list = new ArrayList<>();
        private boolean shouldHide;

        public ComboKeyHandler(JComboBox<String> combo) {
            super();
            this.comboBox = combo;
            for (int i = 0; i < comboBox.getModel().getSize(); i++) {
                list.add((String) comboBox.getItemAt(i));
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    String text = ((JTextField) e.getComponent()).getText();
                    ComboBoxModel<String> m;
                    if (text.isEmpty()) {
                        String[] array = list.toArray(new String[list.size()]);
                        m = new DefaultComboBoxModel<String>(array);
                        setSuggestionModel(comboBox, m, "");
                        comboBox.hidePopup();
                    } else {
                        m = getSuggestedModel(list, text);
                        if (m.getSize() == 0 || shouldHide) {
                            comboBox.hidePopup();
                        } else {
                            setSuggestionModel(comboBox, m, text);
                            comboBox.showPopup();
                        }
                    }
                }
            });
        }

        @Override
        public void keyPressed(KeyEvent e) {
            JTextField textField = (JTextField) e.getComponent();
            String text = textField.getText();
            shouldHide = false;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    for (String s : list) {
                        if (s.startsWith(text)) {
                            textField.setText(s);
                            return;
                        }
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    studentAttendenceArea.setText("");
                    if (!list.contains(text)) {
                        list.add(text);
                        Collections.sort(list);
                        //setSuggestionModel(comboBox, new DefaultComboBoxModel(list), text);
                        setSuggestionModel(comboBox, getSuggestedModel(list, text), text);

                    }
                    String selectedName = combo.getSelectedItem().toString();
                    String[] split = selectedName.split(" ");
                    String name = split[0];
                    String surname = split[1];
                    String studentID = studentID(name, surname);
                    try {
                        studentAttendenceArea.setText(name + " " + surname + "'s courses:\n" + studentDetails(studentID)[6] + " " + getStatistics(studentID, studentDetails(studentID)[6]) + "\n" + studentDetails(studentID)[7] + " " + getStatistics(studentID, studentDetails(studentID)[7]) + "\n" + studentDetails(studentID)[8] + " " + getStatistics(studentID, studentDetails(studentID)[8]) + "\n" + studentDetails(studentID)[9] + " " + getStatistics(studentID, studentDetails(studentID)[9]));
                    } catch (NullPointerException npe) {
                        studentAttendenceArea.setText("Error no attendence avaliable. " + npe);
                    }

                    shouldHide = true;
                    break;
                case KeyEvent.VK_ESCAPE:
                    shouldHide = true;
                    break;
                default:
                    break;
            }
        }

        private void setSuggestionModel(JComboBox<String> comboBox, ComboBoxModel<String> mdl, String str) {
            comboBox.setModel(mdl);
            comboBox.setSelectedIndex(-1);
            ((JTextField) comboBox.getEditor().getEditorComponent()).setText(str);
        }

        private ComboBoxModel<String> getSuggestedModel(List<String> list, String text) {
            DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
            for (String s : list) {
                if (s.startsWith(text)) {
                    m.addElement(s);
                }
            }
            return m;
        }
    }

}
