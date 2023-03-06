package SURSE;

import java.text.SimpleDateFormat;
import java.util.*;
public abstract class Course {
    private String name;
    private Teacher teacher;
    private HashSet<Assistant> assistants;
    private ArrayList<Grade> grades;
    private HashMap<String, Group> dictionary;
    private final Strategy strategy;
    private final Snapshot snapshot = new Snapshot("Snapshot");
    private int credits;

    public Course(String name, Teacher teacher, HashSet<Assistant> assistants, ArrayList<Grade> grades, HashMap<String, Group> dictionary, int credits, Strategy strategy) {
        this.name = name;
        this.teacher = teacher;
        this.assistants = assistants;
        this.grades = grades;
        this.dictionary = dictionary;
        this.credits = credits;
        this.strategy = strategy;
    }

    public Course(CourseBuilder builder) {
        this.name = builder.name;
        this.teacher = builder.teacher;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.dictionary = builder.dictionary;
        this.credits = builder.credits;
        this.strategy = builder.strategy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public HashSet<Assistant> getAssistants() {
        return assistants;
    }

    public void setAssistants(HashSet<Assistant> assistants) {
        this.assistants = assistants;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades) {
        Collections.sort(grades);
        this.grades = grades;
    }

    public HashMap<String, Group> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<String, Group> dictionary) {
        this.dictionary = dictionary;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addAssistant(String ID, Assistant assistant) {
        assistants.add(assistant);
        dictionary.get(ID).setAssistant(assistant);
    }

    public void addStudent(String ID, Student student) {
        dictionary.get(ID).add(student);
    }

    public void removeStudent(String ID, Student student) {
        dictionary.get(ID).remove(student);
    }

    public void addGroup(Group group) {
        Group new_group = new Group(group.getID(), group.getAssistant());
        dictionary.put(group.getID(), new_group);
    }

    public void addGroup(String ID, Assistant assistant) {
        Group new_group = new Group(ID, assistant);
        dictionary.put(ID, new_group);
    }

    public void addGroup(String ID, Assistant assist, Comparator<Student> comp) {
        Group new_group = new Group(ID, assist, comp);
        dictionary.put(ID, new_group);
    }

    public Grade getGrade(Student student) {
        for (Grade grade_iterator : grades) {
            if (grade_iterator.getStudent().equals(student)) {
                return grade_iterator;
            }
        }
        return null;
    }

    public void addGrade (Grade grade) {
        grades.add(grade);
    }

    public ArrayList<Student> getAllStudents() {
        if (grades.isEmpty()) {
            return null;
        }
        ArrayList<Student> ret_list = new ArrayList<>();
        for (Grade grade_iterator : grades) {
            ret_list.add(grade_iterator.getStudent());
        }
        return ret_list;
    }

    public HashMap<Student, Grade> getAllStudentsGrades() {
        HashMap<Student, Grade> ret_map = new HashMap<>();
        for (Grade grade_iterator : grades ) {
            ret_map.put(grade_iterator.getStudent(), grade_iterator);
        }
        return ret_map;
    }

    public abstract ArrayList<Student> getGraduatedStudents();

    public Student getBestStudent() {
        return strategy.getBestStudent(grades);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' + '\n' +
                ", teacher=" + teacher + '\n' +
                ", assistants=" + assistants + '\n' +
                ", grades=" + grades + '\n' +
                ", dictionary=" + dictionary + '\n' +
                ", strategy=" + strategy + '\n' +
                ", credits=" + credits + '\n' +
                '}';
    }

    public abstract static class CourseBuilder {
        private String name;
        private Teacher teacher;
        private HashSet<Assistant> assistants;
        private ArrayList<Grade> grades;
        private HashMap<String, Group> dictionary;
        private Strategy strategy;
        private int credits;

        public CourseBuilder setStrategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public CourseBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder setTeacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder setAssistants(HashSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder setGrades(ArrayList<Grade> grades) {
            Collections.sort(grades);
            this.grades = grades;
            return this;
        }

        public CourseBuilder setDictionary(HashMap<String, Group> dictionary) {
            this.dictionary = dictionary;
            return this;
        }

        public CourseBuilder setCredits(int credits) {
            this.credits = credits;
            return this;
        }
        public abstract Course build();
    }

    private static class Snapshot implements Memento{

        private String snapshotName;
        ArrayList<Grade> gradesList;

        public Snapshot(String snapshotName) {
            this.snapshotName = snapshotName;
            this.gradesList = new ArrayList<>();
        }

        public void addGrade(Grade grade) {
            gradesList.add(grade);
        }

        @Override
        public String getName() {
            return this.snapshotName;
        }

        @Override
        public String getSnapshotDate() {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            return formatter.format(date);
        }
    }

    public void makeBackup() {
        for (Grade grade_iterator : this.getGrades()) {
            try {
                Grade grade_clone = (Grade) grade_iterator.clone();
                this.snapshot.addGrade(grade_clone);
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void undo () {
        this.getGrades().clear();
        for (Grade grade_iterator : snapshot.gradesList) {
            this.addGrade(grade_iterator);
        }
    }
}
