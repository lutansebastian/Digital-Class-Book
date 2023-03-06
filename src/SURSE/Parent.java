package SURSE;

import java.util.ArrayList;

public class Parent extends User implements Observer {

    private ArrayList<Notification> notifications;
    private String firstName, lastName;
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
        notifications = new ArrayList<>();
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void update(Notification notification) {
        notifications.add(notification);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "notifications=" + notifications +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
