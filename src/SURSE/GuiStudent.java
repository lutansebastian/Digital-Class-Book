package SURSE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GuiStudent implements ActionListener {
    JButton button;
    ArrayList<JButton> buttons;
    Student student;

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button1 : buttons) {
            if (e.getSource() == button1) {
                NewWindow courseWindow = new NewWindow(button1.getName(), student);
            }
        }
    }

    public GuiStudent(Student student) {

        this.student = student;
        JLabel nameLabel = new JLabel();
        nameLabel.setText(student.getLastName() + " " + student.getFirstName());


        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        namePanel.setBounds(0, 0, 420, 30);
        namePanel.add(nameLabel);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setTitle("Student Page");
        frame.setLayout(null);
        frame.setSize(420, 420);
        frame.setBackground(Color.GRAY);
        frame.setVisible(true);
        frame.add(namePanel);

        JLabel coursesLabel = new JLabel();
        coursesLabel.setText("Courses : ");


        JPanel coursesPanel = new JPanel();
        coursesPanel.setBackground(Color.white);
        coursesPanel.setBounds(10, 50, 400, 330);
        coursesPanel.add(coursesLabel);

        buttons = new ArrayList<>();
        for (Course course_iterator : Catalog.getInstance().getCourses()) {
            if (course_iterator.getAllStudents().contains(student)) {
                button = new JButton(course_iterator.getName());
                button.setName(course_iterator.getName());
                button.setFocusable(false);
                buttons.add(button);
                coursesPanel.add(button);
            }
        }

        for (JButton button1 : buttons) {
            button1.addActionListener(this);
        }

        frame.add(coursesPanel);
    }

    private static class NewWindow {
        JFrame frame = new JFrame();
        JPanel infoPanel;
        JLabel infoLabel;
        NewWindow(String name, Student student) {

            infoLabel = new JLabel("Course Info");

            infoPanel = new JPanel();
            infoPanel.setBackground(Color.white);
            infoPanel.setBounds(0, 0, 420, 30);
            infoPanel.add(infoLabel);

            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setTitle(name);
            frame.setLayout(null);
            frame.setSize(420, 420);
            frame.setBackground(Color.GRAY);
            frame.setVisible(true);
            frame.add(infoPanel);

            JPanel profPanel = new JPanel();
            profPanel.setBackground(Color.white);
            profPanel.setBounds(10, 50, 400, 30);


            JLabel profLabel = new JLabel();
            for (Course course_iterator : Catalog.getInstance().getCourses()) {
                if (course_iterator.getName().equals(name)) {
                    profLabel.setText("Teacher : " + course_iterator.getTeacher().getFirstName() + " " +
                            course_iterator.getTeacher().getLastName());
                }
            }

            profPanel.add(profLabel);


            JPanel assistantsPanel = new JPanel();
            assistantsPanel.setBackground(Color.white);
            assistantsPanel.setBounds(10, 85, 400, 30);

            JLabel assistantsLabel = new JLabel();

            for (Course course_iterator : Catalog.getInstance().getCourses()) {
                if (course_iterator.getName().equals(name)) {
                    assistantsLabel.setText("Assistants : " + course_iterator.getAssistants());
                }
            }

            assistantsPanel.add(assistantsLabel);


            JPanel gradesPanel = new JPanel();
            gradesPanel.setBackground(Color.white);
            gradesPanel.setBounds(10, 120, 400, 30);

            JLabel gradesLabel = new JLabel();

            for (Course course_iterator : Catalog.getInstance().getCourses()) {
                if (course_iterator.getName().equals(name)) {
                    for (Grade grade_iterator : course_iterator.getGrades()) {
                        if (grade_iterator.getStudent().equals(student)) {
                            gradesLabel.setText(grade_iterator.toString());
                        }
                    }
                }
            }
            gradesPanel.add(gradesLabel);


            JPanel assistantPanel = new JPanel();
            assistantPanel.setBackground(Color.white);
            assistantPanel.setBounds(10, 155, 400, 30);

            JLabel assistantLabel = new JLabel();

            for (Course course_iterator : Catalog.getInstance().getCourses()) {
                if (course_iterator.getName().equals(name)) {
                    for (Group group_iterator : course_iterator.getDictionary().values()){
                        if (group_iterator.getStudents().contains(student)) {
                            assistantLabel.setText(student.getFirstName() + " " +
                                    student.getLastName() + "'s assistant : " + group_iterator.getAssistant());
                        }
                    }
                }
            }
            assistantPanel.add(assistantLabel);

            frame.add(assistantPanel);
            frame.add(gradesPanel);
            frame.add(assistantsPanel);
            frame.add(profPanel);

        }
    }
}
