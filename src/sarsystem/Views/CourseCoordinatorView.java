package sarsystem.Views;

import java.awt.BorderLayout;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.*;
import static sarsystem.Controllers.CourseCoordinatorController.addToSession;
import static sarsystem.Controllers.CourseCoordinatorController.getSuggestions;
import static sarsystem.Controllers.StudentController.getStatistics;
import sarsystem.Models.AISModel;
import static sarsystem.Models.AISModel.studentDetails;
import static sarsystem.Models.AISModel.studentID;

public class CourseCoordinatorView extends JFrame implements ActionListener {

    JMenuBar menuBar = new JMenuBar();
    JMenu exit = new JMenu("Exit");
    JMenu add = new JMenu("Add");
    JMenuItem logout = new JMenuItem("Logout", KeyEvent.VK_N);
    JMenuItem addStudent = new JMenuItem("Add Student", KeyEvent.VK_N);
    JTextArea studentAttendenceArea = new JTextArea(10, 20);
    JCheckBox regStatus = new JCheckBox();
    JComboBox<String> combo;

    public CourseCoordinatorView() {
        // Set windows properties
        setLayout(new FlowLayout());
        setTitle("Course Coordinator View");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(465, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        menuBar.add(add);
        menuBar.add(exit);
        setJMenuBar(menuBar);

        // Add sub menu to Exit
        exit.add(logout);
        logout.addActionListener(this);

        // Add sub menu to add
        add.add(addStudent);
        addStudent.addActionListener(this);

        // AUTO SUGGESTION BOX
        // http://java-swing-tips.blogspot.co.uk/2009/01/create-auto-suggest-jcombobox.html
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

        add(new JLabel("Permament Registration: "));
        add(regStatus);

        add(studentAttendenceArea);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logout) { // logout triggered event
            this.dispose(); // close window
            new LoginView();
        } else if (e.getSource() == addStudent) {
            if (regStatus.isSelected()) {
                JOptionPane.showMessageDialog(null, "User already on register", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                JComboBox list = new JComboBox(AISModel.sessionList());
                JOptionPane.showMessageDialog(null, list, "Pick session", JOptionPane.INFORMATION_MESSAGE);
                
                String selectedCourse = list.getSelectedItem().toString();
                String selectedName = combo.getSelectedItem().toString();
                
                String[] split = selectedName.split(" ");
                String name = split[0];
                String surname = split[1];
                
                String studentID = studentID(name, surname);
                
                String[] splitC = selectedCourse.split(",");
                String courseID = splitC[0];

                addToSession(studentID, courseID);
            }
        }
    }

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
                    }
                    catch (NullPointerException npe){
                        studentAttendenceArea.setText("Error no attendence avaliable. " + npe);
                    }
                    boolean permReg = Boolean.parseBoolean(studentDetails(studentID)[3]);
                    if (permReg == true) {
                        regStatus.setSelected(true);
                        regStatus.setEnabled(false);
                    } else {
                        regStatus.setSelected(false);
                        regStatus.setEnabled(true);
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
