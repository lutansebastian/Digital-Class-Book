package SURSE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiTeacher implements ActionListener {
    JButton button;
    JFrame frame = new JFrame();
    Teacher teacher;

    ScoreVisitor scoreVisitor;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            scoreVisitor.visit(teacher);
            button.setEnabled(false);
        }
    }

    public GuiTeacher(Teacher teacher, ScoreVisitor scoreVisitor) {
        this.teacher = teacher;
        this.scoreVisitor = scoreVisitor;

        JLabel nameLabel = new JLabel();
        nameLabel.setText(teacher.getLastName() + " " + teacher.getFirstName());

        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        namePanel.setBounds(0, 0, 420, 30);
        namePanel.add(nameLabel);

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setTitle("Teacher Page");
        frame.setLayout(null);
        frame.setSize(420, 420);
        frame.setBackground(Color.GRAY);
        frame.setVisible(true);

        StringBuilder courses = new StringBuilder();
        for (Course course_iterator : Catalog.getInstance().getCourses()) {
            if (course_iterator.getTeacher().getFirstName().equals(teacher.getFirstName())) {
                courses.append(course_iterator.getName()).append(" ");
            }
        }

        JLabel coursesLabel = new JLabel();
        coursesLabel.setText("Courses : " + courses);

        JPanel coursesPanel = new JPanel();
        coursesPanel.setBackground(Color.white);
        coursesPanel.setBounds(10, 50, 400, 30);
        coursesPanel.add(coursesLabel);

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Grades to Validate" + "<br>" + "<br>");

        for (Course course_iterator : Catalog.getInstance().getCourses()) {
            if (course_iterator.getTeacher().getFirstName().equals(teacher.getFirstName())) {
                HashMap<Student, ArrayList<Double>> hashMap =
                        scoreVisitor.examScoresToValidate(teacher, course_iterator.getName());
                for (Map.Entry<Student, ArrayList<Double>> entry : hashMap.entrySet()) {
                    stringBuilder.append(entry.getKey().getFirstName()).append(" ").
                            append(entry.getKey().getLastName()).append(" -> ").
                            append(entry.getValue()).append("<br>");
                }
            }
        }

        JLabel toValidateGradesLabel = new JLabel();
        toValidateGradesLabel.setText("<html><div style='text-align: center;'>" + stringBuilder + "</div></html>");

        JPanel toValidateGradesPanel = new JPanel();
        toValidateGradesPanel.setBackground(Color.white);
        toValidateGradesPanel.setBounds(10, 85, 400, 140);
        toValidateGradesPanel.add(toValidateGradesLabel);


        button = new JButton("Validate Grades");
        button.setName("Buton magic");
        button.setFocusable(false);

        JPanel validateButtonPanel = new JPanel();
        validateButtonPanel.setBackground(Color.white);
        validateButtonPanel.setBounds(10, 230, 400, 40);
        validateButtonPanel.add(button);
        button.addActionListener(this);

        frame.add(validateButtonPanel);
        frame.add(toValidateGradesPanel);
        frame.add(coursesPanel);
        frame.add(namePanel);
    }
}
