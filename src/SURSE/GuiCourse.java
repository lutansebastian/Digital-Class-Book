package SURSE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GuiCourse implements ActionListener {
    Course course;
    JFrame mainFrame;
    JLabel assistantsLabel;
    JLabel studentsLabel;
    JLabel bestStudentLabel;
    JLabel graduatedStudentsLabel;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("students")) {
            ModifyStudentsWindow modifyStudentsWindow = new ModifyStudentsWindow(course, studentsLabel, bestStudentLabel,
                    graduatedStudentsLabel);
        }
        else if (e.getActionCommand().equals("assistants")) {
            ModifyAssistantsWindow modifyAssistantsWindow = new ModifyAssistantsWindow(course, assistantsLabel);
        }
        else if (e.getActionCommand().equals("grades")) {
            ModifyGradesWindow modifyGradesWindow = new ModifyGradesWindow(course, studentsLabel, bestStudentLabel,
                    graduatedStudentsLabel);
        }
    }

    public GuiCourse(Course course) {
        this.course = course;

        JLabel nameLabel = new JLabel();
        nameLabel.setText(course.getName());

        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        namePanel.setBounds(0, 0, 420, 30);
        namePanel.add(nameLabel);

        JLabel teacherLabel = new JLabel();
        teacherLabel.setText("Teacher : " + course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName());

        JPanel teacherPanel = new JPanel();
        teacherPanel.setBackground(Color.white);
        teacherPanel.setBounds(10, 50, 400, 30);
        teacherPanel.add(teacherLabel);

        assistantsLabel = new JLabel();
        String stringBuilder = "Assistants : " +
                course.getAssistants();
        assistantsLabel.setText(stringBuilder);

        JPanel assistantsPanel = new JPanel();
        assistantsPanel.setBackground(Color.white);
        assistantsPanel.setBounds(10, 85, 400, 30);
        assistantsPanel.add(assistantsLabel);

        JButton assistantsButton = new JButton();
        assistantsButton.setBounds(160, 118, 100, 30);
        assistantsButton.setText("Modifiy");
        assistantsButton.setName("assistants");
        assistantsButton.setActionCommand("assistants");

        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Students" + "<br>");
        studentsLabel = new JLabel();
        for (Grade grade_iterator : course.getGrades()) {
            stringBuilder1.append(grade_iterator.getStudent().getFirstName()).append(" ").
                    append(grade_iterator.getStudent().getLastName()).append(" : ").
                    append(grade_iterator.getExamScore()).append(" ").
                    append(grade_iterator.getPartialScore()).
                    append("<br>");
        }

        studentsLabel.setText("<html><div style='text-align: center;'>" + stringBuilder1 + "</div></html>");

        JPanel studentsPanel = new JPanel();
        studentsPanel.setBackground(Color.white);
        studentsPanel.setBounds(10, 150, 400, 100);
        studentsPanel.add(studentsLabel);

        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Course page");
        mainFrame.setLayout(null);
        mainFrame.setSize(420, 420);
        mainFrame.setBackground(Color.GRAY);
        mainFrame.setVisible(true);

        JButton studentsButton = new JButton();
        studentsButton.setBounds(30, 250, 180, 30);
        studentsButton.setText("Modify students");
        studentsButton.setName("students");
        studentsButton.setActionCommand("students");

        JButton gradesButton = new JButton();
        gradesButton.setBounds(200, 250, 180, 30);
        gradesButton.setText("Modify grades");
        gradesButton.setName("grades");
        gradesButton.setActionCommand("grades");

        JLabel creditsLabel = new JLabel();
        creditsLabel.setText("Credits : " + course.getCredits());

        JPanel creditsPanel = new JPanel();
        creditsPanel.setBackground(Color.white);
        creditsPanel.setBounds(10, 280, 400, 30);
        creditsPanel.add(creditsLabel);

        bestStudentLabel = new JLabel();
        bestStudentLabel.setText("Best Student : " + course.getBestStudent());

        JPanel bestStudentPanel = new JPanel();
        bestStudentPanel.setBackground(Color.white);
        bestStudentPanel.setBounds(10, 315, 400, 30);
        bestStudentPanel.add(bestStudentLabel);


        graduatedStudentsLabel = new JLabel();
        graduatedStudentsLabel.setText("Graduated Students : " + course.getGraduatedStudents());

        JPanel graduatedStudentsPanel = new JPanel();
        graduatedStudentsPanel.setBackground(Color.white);
        graduatedStudentsPanel.setBounds(10, 350, 400, 30);
        graduatedStudentsPanel.add(graduatedStudentsLabel);

        studentsButton.addActionListener(this);
        gradesButton.addActionListener(this);
        assistantsButton.addActionListener(this);

        mainFrame.add(graduatedStudentsPanel);
        mainFrame.add(bestStudentPanel);
        mainFrame.add(creditsPanel);
        mainFrame.add(gradesButton);
        mainFrame.add(studentsButton);
        mainFrame.add(studentsPanel);
        mainFrame.add(assistantsButton);
        mainFrame.add(assistantsPanel);
        mainFrame.add(teacherPanel);
        mainFrame.add(namePanel);
    }

    private static class ModifyAssistantsWindow implements ActionListener {

        JTextField firstNameField;
        JTextField lastNameField;
        JButton addButton, removeButton;
        Course course;
        JLabel assistantsLabel;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("add assistant")) {
                addButton.setEnabled(false);

                User new_assistant = UserFactory.getUser("Assistant", firstNameField.getText(),
                        lastNameField.getText());
                course.getAssistants().add((Assistant) new_assistant);

                String stringBuilder = "Assistants : " +
                        course.getAssistants();
                assistantsLabel.setText(stringBuilder);

                System.out.println("console [ASSISTANTS] -> Assistant " + firstNameField.getText() + " " +
                        lastNameField.getText() + " was added to course " + course.getName());
            }
            else if (e.getActionCommand().equals("remove assistant")) {
                int flag = -1;
                for (Assistant assistant_iterator : course.getAssistants()) {
                    if (assistant_iterator.getFirstName().equals(firstNameField.getText()) &&
                        assistant_iterator.getLastName().equals(lastNameField.getText())) {
                        course.getAssistants().remove(assistant_iterator);
                        flag = 1;
                    }
                }
                if (flag == 1){
                    System.out.println("console [ASSISTANTS] -> Assistant " + firstNameField.getText() + " " +
                            lastNameField.getText() + " was removed from course " + course.getName());
                    removeButton.setEnabled(false);

                    String stringBuilder = "Assistants : " +
                            course.getAssistants();
                    assistantsLabel.setText(stringBuilder);
                }
                else {
                    System.out.println("console [ASSISTANTS] -> Assistant " + firstNameField.getText() + " " +
                            lastNameField.getText() + " was not found in course " + course.getName());
                }
            }
        }
        private ModifyAssistantsWindow(Course course, JLabel assistantsLabel) {
            this.assistantsLabel = assistantsLabel;
            this.course = course;
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setTitle("Modify Assistants");
            frame.setLayout(null);
            frame.setSize(420, 220);
            frame.setBackground(Color.GRAY);
            frame.setVisible(true);

            JLabel label = new JLabel();
            label.setText("Enter the name of the assistant you want to add/remove");

            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            panel.setBounds(10, 20, 400, 30);
            panel.add(label);

            firstNameField = new JTextField();
            lastNameField = new JTextField();

            JLabel firstNameLabel = new JLabel();
            firstNameLabel.setText("Fist name");
            firstNameLabel.setBounds(50, 70, 100, 30);

            JLabel lastNameLabel = new JLabel();
            lastNameLabel.setText("Last name");
            lastNameLabel.setBounds(50, 110, 100, 30);

            lastNameField.setBounds(130, 110, 160, 30);
            lastNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            firstNameField.setBounds(130, 70,160, 30);
            firstNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            addButton = new JButton();
            addButton.setFocusable(false);
            addButton.setBounds(300, 70, 100, 30);
            addButton.setText("Add");
            addButton.setActionCommand("add assistant");

            addButton.addActionListener(this);

            removeButton = new JButton();
            removeButton.setFocusable(false);
            removeButton.setBounds(300, 110, 100, 30);
            removeButton.setText("Remove");
            removeButton.setActionCommand("remove assistant");

            removeButton.addActionListener(this);

            frame.add(removeButton);
            frame.add(addButton);
            frame.add(lastNameLabel);
            frame.add(firstNameLabel);
            frame.add(lastNameField);
            frame.add(firstNameField);
            frame.add(panel);
        }
    }

    private static class ModifyGradesWindow implements ActionListener {
        ArrayList<JButton> buttons;
        Course course;
        JLabel studentsLabel;
        JLabel bestStudentLabel;
        JLabel graduatedStudentsLabel;

        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton button : buttons) {
                if (e.getSource() == button) {
                    ModifyGradesSecondWindow modifyGradesSecondWindow = new ModifyGradesSecondWindow(course,
                            button.getName(), studentsLabel, bestStudentLabel, graduatedStudentsLabel);
                }
            }
        }

        private ModifyGradesWindow(Course course, JLabel studentsLabel, JLabel bestStudentLabel,
                                   JLabel graduatedStudentsLabel) {
            this.course = course;
            this.studentsLabel = studentsLabel;
            this.bestStudentLabel = bestStudentLabel;
            this.graduatedStudentsLabel = graduatedStudentsLabel;
            JLabel label = new JLabel();
            label.setText("Whose grade do you want to modify?");

            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            panel.setBounds(10, 20, 400, 30);
            panel.add(label);

            JPanel namesPanel = new JPanel();
            namesPanel.setBackground(Color.white);
            namesPanel.setBounds(10, 60, 400, 120);

            buttons = new ArrayList<>();
            JButton button = new JButton();
            for (Student student_iterator : course.getAllStudents()) {
                button = new JButton(student_iterator.getFirstName() + " " + student_iterator.getLastName());
                button.setName(student_iterator.getLastName());
                button.setFocusable(false);
                buttons.add(button);
                namesPanel.add(button);
            }
            for (JButton button1 : buttons) {
                button1.addActionListener(this);
            }

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setTitle("Modify Grades");
            frame.setLayout(null);
            frame.setSize(420, 220);
            frame.setBackground(Color.GRAY);
            frame.setVisible(true);

            frame.add(namesPanel);
            frame.add(panel);
        }
        private static class ModifyGradesSecondWindow implements ActionListener {

            JCheckBox checkBoxPartial;
            JCheckBox checkBoxExam;
            JTextField textFieldGrade;
            Course course;
            String studentLastName;
            JButton removeButton;
            JButton addButton;

            JLabel studentsLabel;
            JLabel bestStudentLabel;
            JLabel graduatedStudentsLabel;
            String name;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("remove grade")) {
                    if (!checkBoxExam.isSelected() && !checkBoxPartial.isSelected()) {
                        System.out.println("console [GRADES] -> please select the score you want to modify!");
                    } else {
                        removeButton.setEnabled(false);

                        if (checkBoxPartial.isSelected() && checkBoxExam.isSelected()) {
                            for (Grade grade_iterator : course.getGrades()) {
                                if (grade_iterator.getStudent().getLastName().equals(studentLastName)) {
                                    grade_iterator.setExamScore(0.0d);
                                    grade_iterator.setPartialScore(0.0d);
                                }
                            }
                            System.out.println("console [GRADES] -> Exam and Partial scores successfully deleted from "
                                    + name);
                        } else if (checkBoxPartial.isSelected()) {
                            for (Grade grade_iterator : course.getGrades()) {
                                if (grade_iterator.getStudent().getLastName().equals(studentLastName)) {
                                    grade_iterator.setPartialScore(0.0d);
                                }
                            }
                            System.out.println("console [GRADES] -> Partial score successfully deleted from " + name);
                        } else if (checkBoxExam.isSelected()) {
                            for (Grade grade_iterator : course.getGrades()) {
                                if (grade_iterator.getStudent().getLastName().equals(studentLastName)) {
                                    grade_iterator.setExamScore(0.0d);
                                }
                            }
                            System.out.println("console [GRADES] -> Exam score successfully deleted from " + name);
                        }

                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append("Students" + "<br>");
                        for (Grade grade_iterator : course.getGrades()) {
                            stringBuilder1.append(grade_iterator.getStudent().getFirstName()).
                                    append(" ").append(grade_iterator.getStudent().getLastName()).append(" : ").
                                    append(grade_iterator.getExamScore()).append(" ").
                                    append(grade_iterator.getPartialScore()).
                                    append("<br>");
                        }
                        studentsLabel.setText("<html><div style='text-align: center;'>" + stringBuilder1 + "</div></html>");

                        bestStudentLabel.setText("Best Student : " + course.getBestStudent());
                        graduatedStudentsLabel.setText("Graduated Students : " + course.getGraduatedStudents());

                    }
                }
                if (e.getActionCommand().equals("add grade")) {
                    if (!checkBoxExam.isSelected() && !checkBoxPartial.isSelected()) {
                        System.out.println("console [GRADES] -> ERROR, please select the score you want to modify!");
                    } else if (textFieldGrade.getText().isEmpty()) {
                        System.out.println("console [GRADES] -> please enter a score");
                    } else {
                        addButton.setEnabled(false);

                        if (checkBoxExam.isSelected() && checkBoxPartial.isSelected()) {
                            for (Grade grade_iterator : course.getGrades()) {
                                if (grade_iterator.getStudent().getLastName().equals(studentLastName)) {
                                    grade_iterator.setPartialScore(Double.parseDouble(textFieldGrade.getText()));
                                    grade_iterator.setExamScore(Double.parseDouble(textFieldGrade.getText()));
                                }
                            }
                        } else if (checkBoxExam.isSelected()) {
                            for (Grade grade_iterator : course.getGrades()) {
                                if (grade_iterator.getStudent().getLastName().equals(studentLastName)) {
                                    grade_iterator.setExamScore(Double.parseDouble(textFieldGrade.getText()));
                                }
                            }
                        } else if (checkBoxPartial.isSelected()) {
                            for (Grade grade_iterator : course.getGrades()) {
                                if (grade_iterator.getStudent().getLastName().equals(studentLastName)) {
                                    grade_iterator.setPartialScore(Double.parseDouble(textFieldGrade.getText()));
                                }
                            }
                        }

                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append("Students" + "<br>");
                        for (Grade grade_iterator : course.getGrades()) {
                            stringBuilder1.append(grade_iterator.getStudent().getFirstName()).
                                    append(" ").append(grade_iterator.getStudent().getLastName()).append(" : ").
                                    append(grade_iterator.getExamScore()).append(" ").
                                    append(grade_iterator.getPartialScore()).
                                    append("<br>");
                        }
                        studentsLabel.setText("<html><div style='text-align: center;'>" + stringBuilder1 + "</div></html>");

                        bestStudentLabel.setText("Best Student : " + course.getBestStudent());
                        graduatedStudentsLabel.setText("Graduated Students : " + course.getGraduatedStudents());

                    }
                }
            }
            private ModifyGradesSecondWindow(Course course, String lastName, JLabel studentsLabel,
                                             JLabel bestStudentLabel, JLabel graduatedStudentsLabel){
                this.studentLastName = lastName;
                this.course = course;
                this.studentsLabel = studentsLabel;
                this.bestStudentLabel = bestStudentLabel;
                this.graduatedStudentsLabel = graduatedStudentsLabel;
                this.name = lastName;

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setTitle(lastName);
                frame.setLayout(null);
                frame.setSize(420, 220);
                frame.setBackground(Color.GRAY);
                frame.setVisible(true);

                JLabel chooseLabel = new JLabel();
                chooseLabel.setText("Choose the score you want to modify");

                JPanel choosePanel = new JPanel();
                choosePanel.setBackground(Color.white);
                choosePanel.setBounds(10, 10, 400, 30);
                choosePanel.add(chooseLabel);

                checkBoxPartial = new JCheckBox("Partial Score");
                checkBoxPartial.setBounds(100, 40, 200, 30);

                checkBoxExam = new JCheckBox("Exam Score");
                checkBoxExam.setBounds(220, 40, 200, 30);

                removeButton = new JButton();
                removeButton.setName("remove");
                removeButton.setFocusable(false);
                removeButton.setText("Remove");
                removeButton.setBounds(160, 70, 100, 30);

                removeButton.setActionCommand("remove grade");
                removeButton.addActionListener(this);

                textFieldGrade = new JTextField();
                textFieldGrade.setBounds(170, 100, 80, 30);
                textFieldGrade.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                addButton = new JButton();
                addButton.setName("add");
                addButton.setFocusable(false);
                addButton.setText("Add/Modify");
                addButton.setBounds(150, 130, 120, 30);

                addButton.setActionCommand("add grade");
                addButton.addActionListener(this);

                frame.add(addButton);
                frame.add(textFieldGrade);
                frame.add(removeButton);
                frame.add(checkBoxExam);
                frame.add(checkBoxPartial);
                frame.add(choosePanel);
            }
        }
    }

    private static class ModifyStudentsWindow implements ActionListener {

        JTextField firstNameField;
        JTextField lastNameField;
        JTextField groupField;
        JButton addButton, removeButton;
        Course course;

        JLabel studentsLabel, bestStudentLabel, graduatedStudentsLabel;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("add student")){
                addButton.setEnabled(false);

                if (groupField.getText().equals("321CC") || groupField.getText().equals("322CC") ||
                        groupField.getText().equals("323CC") || groupField.getText().equals("324CC")) {

                    User new_student = UserFactory.getUser("Student", firstNameField.getText(),
                            lastNameField.getText());

                    Grade new_student_grade = new Grade(0.0d, 0.0d, (Student) new_student,
                            course.getName());

                    if (!course.getGrades().contains(new_student)) {
                        course.getGrades().add(new_student_grade);
                        course.addStudent(groupField.getText(), (Student) new_student);

                        StringBuilder stringBuilder1 = new StringBuilder();
                        stringBuilder1.append("Students" + "<br>");
                        for (Grade grade_iterator : course.getGrades()) {
                            stringBuilder1.append(grade_iterator.getStudent().getFirstName()).
                                    append(" ").append(grade_iterator.getStudent().getLastName()).append(" : ").
                                    append(grade_iterator.getExamScore()).append(" ").
                                    append(grade_iterator.getPartialScore()).
                                    append("<br>");
                        }
                        studentsLabel.setText("<html><div style='text-align: center;'>" + stringBuilder1 + "</div></html>");

                        bestStudentLabel.setText("Best Student : " + course.getBestStudent());
                        graduatedStudentsLabel.setText("Graduated Students : " + course.getGraduatedStudents());

                        System.out.println("console [STUDENTS] -> Student " + firstNameField.getText() + " " +
                                lastNameField.getText() + " was added to course " + course.getName());
                    }
                    else {
                        System.out.println("console [STUDENTS] -> Student " + firstNameField.getText() + " " +
                                lastNameField.getText() + " already exists in " + course.getName());
                    }
                } else {
                    System.out.println("console [STUDENTS] -> The group doesen't exist");
                }
            }
            else if (e.getActionCommand().equals("remove student")){
                removeButton.setEnabled(false);

                if (groupField.getText().equals("321CC") || groupField.getText().equals("322CC") ||
                        groupField.getText().equals("323CC") || groupField.getText().equals("324CC")) {

                    boolean flag = false;
                    for (Student student : course.getAllStudents()) {
                        if (student.getLastName().equals(lastNameField.getText()) && student.getFirstName().equals(firstNameField.getText())) {
                            course.getGrades().removeIf(grade_iterator -> grade_iterator.getStudent().equals(student));
                            course.removeStudent(groupField.getText(), student);

                            StringBuilder stringBuilder1 = new StringBuilder();
                            stringBuilder1.append("Students" + "<br>");
                            for (Grade grade_iterator : course.getGrades()) {
                                stringBuilder1.append(grade_iterator.getStudent().getFirstName()).
                                        append(" ").append(grade_iterator.getStudent().getLastName()).append(" : ").
                                        append(grade_iterator.getExamScore()).append(" ").
                                        append(grade_iterator.getPartialScore()).
                                        append("<br>");
                            }
                            studentsLabel.setText("<html><div style='text-align: center;'>" + stringBuilder1 + "</div></html>");

                            bestStudentLabel.setText("Best Student : " + course.getBestStudent());
                            graduatedStudentsLabel.setText("Graduated Students : " + course.getGraduatedStudents());

                            System.out.println("console [STUDENTS] -> Student " + firstNameField.getText() + " " +
                                    lastNameField.getText() + " was removed from course " + course.getName());

                            flag = true;
                        }
                    }
                    if (!flag) {
                        System.out.println("console [STUDENTS] -> Student " + firstNameField.getText() + " " +
                                lastNameField.getText() + " was not fond in " + course.getName());
                    }
                } else {
                    System.out.println("console [STUDENTS] -> The group doesen't exist");
                }
            }
        }

        private ModifyStudentsWindow(Course course, JLabel studentsLabel,
                                     JLabel bestStudentLabel, JLabel graduatedStudentsLabel) {
            this.studentsLabel = studentsLabel;
            this.bestStudentLabel = bestStudentLabel;
            this.graduatedStudentsLabel = graduatedStudentsLabel;
            this.course = course;
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setTitle("Modify Students");
            frame.setLayout(null);
            frame.setSize(420, 220);
            frame.setBackground(Color.GRAY);
            frame.setVisible(true);

            JLabel label = new JLabel();
            label.setText("Enter the name of the student you want to add/remove");

            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            panel.setBounds(10, 20, 400, 30);
            panel.add(label);

            firstNameField = new JTextField();
            lastNameField = new JTextField();

            JLabel firstNameLabel = new JLabel();
            firstNameLabel.setText("Fist name");
            firstNameLabel.setBounds(50, 70, 100, 30);

            JLabel lastNameLabel = new JLabel();
            lastNameLabel.setText("Last name");
            lastNameLabel.setBounds(50, 110, 100, 30);

            lastNameField.setBounds(130, 110, 160, 30);
            lastNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            firstNameField.setBounds(130, 70,160, 30);
            firstNameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            addButton = new JButton();
            addButton.setName("Add");
            addButton.setFocusable(false);
            addButton.setBounds(300, 70, 100, 30);
            addButton.setText("Add");
            addButton.setActionCommand("add student");

            removeButton = new JButton();
            removeButton.setName("Remove");
            removeButton.setFocusable(false);
            removeButton.setBounds(300, 110, 100, 30);
            removeButton.setText("Remove");
            removeButton.setActionCommand("remove student");

            addButton.addActionListener(this);
            removeButton.addActionListener(this);

            JLabel groupLabel = new JLabel();
            groupLabel.setText("If you want to add a new student, enter the group : ");
            groupLabel.setBounds(10, 140, 350, 30);

            groupField = new JTextField();
            groupField.setBounds(330, 140, 70, 30);

            frame.add(groupField);
            frame.add(groupLabel);
            frame.add(removeButton);
            frame.add(addButton);
            frame.add(lastNameLabel);
            frame.add(firstNameLabel);
            frame.add(lastNameField);
            frame.add(firstNameField);
            frame.add(panel);
        }
    }
}
