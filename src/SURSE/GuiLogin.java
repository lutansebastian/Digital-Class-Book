package SURSE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiLogin implements ActionListener {

    JTextField textFieldFirst;
    JTextField textFieldLast;
    JButton button;

    JLabel statusLabel;
    ScoreVisitor scoreVisitor;

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean status = false;

        if (e.getSource() == button) {
            for (Course course_iterator : Catalog.getInstance().getCourses()) {
                for (Student student_iterator : course_iterator.getAllStudents()) {
                    if (student_iterator.getFirstName().equals(textFieldFirst.getText()) &&
                        student_iterator.getLastName().equals(textFieldLast.getText())) {
                        status = true;
                        GuiStudent guiStudent = new GuiStudent(student_iterator);
                    }
                    if (student_iterator.getFather().getFirstName().equals(textFieldFirst.getText()) &&
                        student_iterator.getFather().getLastName().equals(textFieldLast.getText())) {
                        status = true;
                        GuiParent guiParent = new GuiParent(student_iterator.getFather().getLastName());
                    }
                    if (student_iterator.getMother().getFirstName().equals(textFieldFirst.getText()) &&
                        student_iterator.getMother().getLastName().equals(textFieldLast.getText())) {
                        status = true;
                        GuiParent guiParent = new GuiParent(student_iterator.getMother().getLastName());
                    }
                }
                if (course_iterator.getTeacher().getFirstName().equals(textFieldFirst.getText()) &&
                    course_iterator.getTeacher().getLastName().equals(textFieldLast.getText())) {
                    status = true;
                    GuiTeacher guiTeacher = new GuiTeacher(course_iterator.getTeacher(), scoreVisitor);
                }
                for (Assistant assistant_iterator : course_iterator.getAssistants()) {
                    if (assistant_iterator.getFirstName().equals(textFieldFirst.getText()) &&
                        assistant_iterator.getLastName().equals(textFieldLast.getText())) {
                        status = true;
                        GuiAssistant guiAssistant = new GuiAssistant(assistant_iterator, scoreVisitor);
                    }
                }
                if (!status){
                    statusLabel.setText("User not found!");
                }
                else {
                    statusLabel.setText(" ");
                }
            }
        }
    }

    public GuiLogin(ScoreVisitor scoreVisitor){

        this.scoreVisitor = scoreVisitor;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login Page");
        frame.setLayout(null);
        frame.setSize(420, 360);
        frame.setBackground(Color.GRAY);
        frame.setVisible(true);

        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First name");
        firstNameLabel.setBounds(60, 110, 100, 30);

        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Last name");
        lastNameLabel.setBounds(60, 145, 100, 30);

        textFieldFirst = new JTextField();
        textFieldFirst.setBounds(140, 110, 150, 30);
        textFieldFirst.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        textFieldLast = new JTextField();
        textFieldLast.setBounds(140, 145, 150, 30);
        textFieldLast.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        statusLabel = new JLabel();
        statusLabel.setBounds(100, 180, 300, 30);

        button = new JButton();
        button.setName("Login");
        button.setFocusable(false);
        button.setBounds(300, 125, 60, 30);
        button.setText("Login");
        button.addActionListener(this);

        frame.add(statusLabel);
        frame.add(button);
        frame.add(lastNameLabel);
        frame.add(firstNameLabel);
        frame.add(textFieldFirst);
        frame.add(textFieldLast);
    }
}
