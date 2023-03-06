package SURSE;

import java.util.*;

public class Group extends TreeSet<Student> {
    private Assistant assistant;
    private String ID;
    public Group (String ID, Assistant assistant, Comparator<Student> comparator) {
        super(comparator);
        this.ID = ID;
        this.assistant = assistant;
    }

    public Group (String ID, Assistant assistant) {
        this(ID, assistant, null);
    }

    public void addStudent(Student student) {
        this.add(student);
    }
    public TreeSet<Student> getStudents() {
        TreeSet<Student> students = new TreeSet<>();
        students.addAll(this);
        return students;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Group group = (Group) o;
        return Objects.equals(ID, group.ID);
    }

    @Override
    public String toString() {
        return "Group{" +
                "students=" + this.getStudents()+
                "assistant=" + assistant +
                ", ID='" + ID + '\'' +
                '}';
    }
}
