package SURSE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GuiParent implements ActionListener {
    JButton button;
    JButton button1;
    ArrayList<JButton> buttons;

    String lastName;

    @Override
    public void actionPerformed(ActionEvent e) {
        for (JButton button2 : buttons) {
            if (e.getSource() == button2) {
                if (!button2.getText().equals(lastName)){
                    System.out.println("console [PARENTS] -> access denied");
                }
                else{
                    FamilyMembersNewWindow parentWindow = new FamilyMembersNewWindow(button2.getName());
                }
            }
        }
    }

    public GuiParent(String lastName) {

        this.lastName = lastName;

        JLabel infoLabel = new JLabel();
        infoLabel.setText("Press to see notifications");

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.white);
        infoPanel.setBounds(0, 0, 420, 30);
        infoPanel.add(infoLabel);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setTitle("Parent Page");
        frame.setLayout(null);
        frame.setSize(420, 420);
        frame.setBackground(Color.GRAY);
        frame.setVisible(true);

        JLabel observerLabel = new JLabel();
        observerLabel.setText("Families : ");

        JPanel oberverPanel = new JPanel();
        oberverPanel.setBackground(Color.white);
        oberverPanel.setBounds(10, 50, 400, 330);
        oberverPanel.add(observerLabel);

        ArrayList<String> names = new ArrayList<>();
        buttons = new ArrayList<>();
        for (Course course_iterator : Catalog.getInstance().getCourses()) {
            for (Student student_iterator : course_iterator.getAllStudents()) {
                if (buttons.size() < course_iterator.getAllStudents().size()) {
                    button = new JButton(student_iterator.getFather().getLastName());
                    button.setName(student_iterator.getFather().getLastName());
                    button.setFocusable(false);
                    buttons.add(button);
                    oberverPanel.add(button);
                }
            }
        }

        for (JButton button2 : buttons) {
            button2.addActionListener(this);
        }

        frame.add(oberverPanel);
        frame.add(infoPanel);
    }

    private static class FamilyMembersNewWindow implements ActionListener{
        JFrame frame = new JFrame();
        JButton button, button1;
        ArrayList<JButton> buttons;

        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton button2 : buttons) {
                if (e.getSource() == button2) {
                    NotificationsNewWindow notificationsNewWindow = new NotificationsNewWindow(button2.getName());
                }
            }
        }

        public FamilyMembersNewWindow(String name) {
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setTitle(name);
            frame.setLayout(null);
            frame.setSize(420, 420);
            frame.setBackground(Color.GRAY);
            frame.setVisible(true);

            JLabel chooseLabel = new JLabel();
            chooseLabel.setText("Choose the family member");


            JLabel membersLabel = new JLabel();
            membersLabel.setText("Members : ");

            JPanel membersPanel = new JPanel();
            membersPanel.setBackground(Color.white);
            membersPanel.setBounds(10, 50, 400, 330);
            membersPanel.add(membersLabel);

            buttons = new ArrayList<>();
            for (Course course_iterator : Catalog.getInstance().getCourses()) {
                for (Student student_iterator : course_iterator.getAllStudents()) {
                    if (student_iterator.getLastName().equals(name)) {
                        if (buttons.size() < 2){
                            button = new JButton(student_iterator.getFather().getFirstName());
                            button.setName(student_iterator.getFather().getFirstName());
                            button.setFocusable(false);
                            buttons.add(button);
                            membersPanel.add(button);

                            button1 = new JButton(student_iterator.getMother().getFirstName());
                            button1.setName(student_iterator.getMother().getFirstName());
                            button1.setFocusable(false);
                            buttons.add(button1);
                            membersPanel.add(button1);
                        }
                    }
                }
            }

            for (JButton button2 : buttons) {
                button2.addActionListener(this);
            }

            frame.add(membersPanel);

            JPanel choosePanel = new JPanel();
            choosePanel.setBackground(Color.white);
            choosePanel.setBounds(0, 0, 420, 30);
            choosePanel.add(chooseLabel);

            frame.add(choosePanel);

        }
        private static class NotificationsNewWindow {
            JFrame frame = new JFrame();
            NotificationsNewWindow(String name) {

                JLabel notificationLabel = new JLabel();
                notificationLabel.setText("Notifications : ");

                JPanel notificationPanel = new JPanel();
                notificationPanel.setBackground(Color.white);
                notificationPanel.setBounds(0, 0, 420, 30);
                notificationPanel.add(notificationLabel);

                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setTitle(name);
                frame.setLayout(null);
                frame.setSize(420, 420);
                frame.setBackground(Color.GRAY);
                frame.setVisible(true);

                StringBuilder stringBuilder = new StringBuilder();
                for (Course course_iterator : Catalog.getInstance().getCourses()) {
                    for (Student student_iterator : course_iterator.getAllStudents()) {
                        if (student_iterator.getFather().getFirstName().equals(name)) {
                            for (int i = 0; i < student_iterator.getFather().getNotifications().size(); i++) {
                                stringBuilder.append(student_iterator.getFather().getNotifications().get(i)).
                                        append("<br>");
                            }
                        }
                        if (student_iterator.getMother().getFirstName().equals(name)) {
                            for (int i = 0; i < student_iterator.getMother().getNotifications().size(); i++) {
                                stringBuilder.append(student_iterator.getMother().getNotifications().get(i)).
                                        append("<br>");
                            }
                        }
                    }
                }

                JLabel listLabel = new JLabel("<html><div style='text-align: center;'>" + stringBuilder +
                        "</div></html>");

                JPanel listPanel = new JPanel();
                listPanel.setBackground(Color.white);
                listPanel.setBounds(10, 50, 400, 330);
                listPanel.add(listLabel);

                frame.add(notificationPanel);
                frame.add(listPanel);
            }
        }
    }
}
