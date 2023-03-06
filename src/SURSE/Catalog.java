package SURSE;

import java.util.HashSet;
import java.util.ArrayList;
public class Catalog implements Subject {
    private HashSet<Course> courses;
    private final ArrayList<Observer> observers;
    private static volatile Catalog instance;

    private Catalog() {
        courses = new HashSet<>();
        observers = new ArrayList<>();
    }

    public static Catalog getInstance() {
        Catalog result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Catalog.class) {
            if (instance == null) {
                instance = new Catalog();
            }
            return instance;
        }
    }

    public HashSet<Course> getCourses() {
        return courses;
    }

    public void setCourses(HashSet<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    @Override
    public void addObserver(Observer observer) {
            observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        for (Observer observer : observers) {
            if (observer == grade.getStudent().getFather() || observer == grade.getStudent().getMother()) {
                observer.update(new Notification("New Grade added, now total is : " + grade.getTotal()));
            }
        }
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "courses=" + courses +
                '}';
    }
}
