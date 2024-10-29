
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

public class AttendanceManagementSystem {

    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextField nameField, deptField, semesterField;
    private JComboBox<String> attendanceBox;
    private JComboBox<String> studentListComboBox, subjectBox;
    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private HashMap<String, String> studentInfo;  // Holds student and their info
    private HashMap<String, HashMap<String, String>> attendanceRecord;  // Holds student attendance per subject

    public AttendanceManagementSystem() {
        studentInfo = new HashMap<>();
        attendanceRecord = new HashMap<>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Student Attendance Management System");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set custom icon for the window
        ImageIcon imgIcon = new ImageIcon("icon.png");  // Provide the correct path to your icon
        frame.setIconImage(imgIcon.getImage());

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Font settings for better aesthetics
        Font font = new Font("Arial", Font.PLAIN, 16);
        
        // Main Menu Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());  // Center the buttons
        
        JButton registerButton = new JButton("Register Student");
        JButton attendanceButton = new JButton("Mark Attendance");

        registerButton.setFont(font);
        attendanceButton.setFont(font);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding for buttons

        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(registerButton, gbc);

        gbc.gridy = 1;
        menuPanel.add(attendanceButton, gbc);

        mainPanel.add(menuPanel, "Menu");

        // Register Student Panel
        JPanel registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints regGbc = new GridBagConstraints();
        regGbc.insets = new Insets(10, 10, 10, 10);  // Padding for inputs
        
        JLabel nameLabel = new JLabel("Student Name:");
        JLabel deptLabel = new JLabel("Department:");
        JLabel semesterLabel = new JLabel("Semester:");

        nameLabel.setFont(font);
        deptLabel.setFont(font);
        semesterLabel.setFont(font);

        nameField = new JTextField(15);
        deptField = new JTextField(15);
        semesterField = new JTextField(15);

        JButton registerStudentButton = new JButton("Register");
        JButton backButton1 = new JButton("Back to Menu");

        registerStudentButton.setFont(font);
        backButton1.setFont(font);

        regGbc.gridx = 0;
        regGbc.gridy = 0;
        registerPanel.add(nameLabel, regGbc);
        regGbc.gridx = 1;
        registerPanel.add(nameField, regGbc);

        regGbc.gridx = 0;
        regGbc.gridy = 1;
        registerPanel.add(deptLabel, regGbc);
        regGbc.gridx = 1;
        registerPanel.add(deptField, regGbc);

        regGbc.gridx = 0;
        regGbc.gridy = 2;
        registerPanel.add(semesterLabel, regGbc);
        regGbc.gridx = 1;
        registerPanel.add(semesterField, regGbc);

        regGbc.gridx = 0;
        regGbc.gridy = 3;
        registerPanel.add(registerStudentButton, regGbc);
        regGbc.gridx = 1;
        registerPanel.add(backButton1, regGbc);

        mainPanel.add(registerPanel, "Register");

        // Attendance Panel
        JPanel attendancePanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints attGbc = new GridBagConstraints();
        attGbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel studentLabel = new JLabel("Select Student:");
        JLabel subjectLabel = new JLabel("Select Subject:");

        studentLabel.setFont(font);
        subjectLabel.setFont(font);

        studentListComboBox = new JComboBox<>();
        String[] attendanceOptions = {"Present", "Absent"};
        attendanceBox = new JComboBox<>(attendanceOptions);

        String[] subjects = {"Data Structure And Algortihm", "Object Oriented Programming", "Computer Organization"};  // Example subjects
        subjectBox = new JComboBox<>(subjects);

        studentListComboBox.setFont(font);
        attendanceBox.setFont(font);
        subjectBox.setFont(font);

        JButton markAttendanceButton = new JButton("Mark Attendance");
        JButton backButton2 = new JButton("Back to Menu");

        markAttendanceButton.setFont(font);
        backButton2.setFont(font);

        attGbc.gridx = 0;
        attGbc.gridy = 0;
        topPanel.add(studentLabel, attGbc);
        attGbc.gridx = 1;
        topPanel.add(studentListComboBox, attGbc);

        attGbc.gridx = 0;
        attGbc.gridy = 1;
        topPanel.add(subjectLabel, attGbc);
        attGbc.gridx = 1;
        topPanel.add(subjectBox, attGbc);

        attGbc.gridx = 0;
        attGbc.gridy = 2;
        topPanel.add(new JLabel("Attendance:"), attGbc);
        attGbc.gridx = 1;
        topPanel.add(attendanceBox, attGbc);

        attGbc.gridy = 3;
        topPanel.add(markAttendanceButton, attGbc);
        attGbc.gridx = 2;
        topPanel.add(backButton2, attGbc);

        // JTable for Attendance Records
        String[] columnNames = {"Student Name", "Department", "Semester", "Subject", "Attendance"};
        tableModel = new DefaultTableModel(columnNames, 0);
        attendanceTable = new JTable(tableModel);
        attendanceTable.setFont(font);
        attendanceTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(attendanceTable);

        attendancePanel.add(topPanel, BorderLayout.NORTH);
        attendancePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(attendancePanel, "Attendance");

        // Button Actions
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Register");
            }
        });

        attendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (studentInfo.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No students registered yet!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    cardLayout.show(mainPanel, "Attendance");
                    updateStudentList();
                }
            }
        });

        backButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Menu");
            }
        });

        backButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Menu");
            }
        });

        registerStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String department = deptField.getText();
                String semester = semesterField.getText();

                if (name.isEmpty() || department.isEmpty() || semester.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String studentDetails = department + ", " + semester;
                    studentInfo.put(name, studentDetails);
                    attendanceRecord.put(name, new HashMap<>());  // Initialize nested attendance map
                    JOptionPane.showMessageDialog(frame, "Student Registered Successfully!");
                    nameField.setText("");
                    deptField.setText("");
                    semesterField.setText("");
                }
            }
        });

        markAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStudent = (String) studentListComboBox.getSelectedItem();
                String selectedSubject = (String) subjectBox.getSelectedItem();
                String attendanceStatus = (String) attendanceBox.getSelectedItem();

                if (selectedStudent != null && selectedSubject != null) {
                    attendanceRecord.get(selectedStudent).put(selectedSubject, attendanceStatus);
                    updateAttendanceTable();
                } else {
                    JOptionPane.showMessageDialog(frame, "No students available for attendance", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.getContentPane().add(mainPanel);
        cardLayout.show(mainPanel, "Menu");
        frame.setVisible(true);
    }

    private void updateStudentList() {
        studentListComboBox.removeAllItems();
        for (String student : studentInfo.keySet()) {
            studentListComboBox.addItem(student);
        }
    }

    private void updateAttendanceTable() {
        tableModel.setRowCount(0);  // Clear existing table rows
        for (String student : studentInfo.keySet()) {
            String details = studentInfo.get(student);
            String[] detailsSplit = details.split(", ");
            String department = detailsSplit[0];
            String semester = detailsSplit[1];
            HashMap<String, String> subjectAttendance = attendanceRecord.get(student);
            for (String subject : subjectAttendance.keySet()) {
                String attendance = subjectAttendance.get(subject);
                tableModel.addRow(new Object[]{student, department, semester, subject, attendance});
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AttendanceManagementSystem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
