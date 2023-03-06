package SURSE;

import java.util.*;

public class ScoreVisitor implements Visitor{
    HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores;
    HashMap<Assistant, ArrayList<Tuple<Student, String , Double>>> partialScores;

    public ScoreVisitor() {
        this.examScores = new HashMap<>();
        this.partialScores = new HashMap<>();
    }

    public HashMap<Student, ArrayList<Double>> examScoresToValidate(Teacher teacher, String courseName) {
        HashMap<Student, ArrayList<Double>> hashToReturn= new HashMap<>();

        ArrayList<Double> list;

        for (int i = 0; i < examScores.get(teacher).size(); i++){
            if (hashToReturn.get(examScores.get(teacher).get(i).getStudent()) == null) {
                list = new ArrayList<>();
                list.add(examScores.get(teacher).get(i).getValue());
                hashToReturn.put(examScores.get(teacher).get(i).getStudent(), list);
            }
            else{
                hashToReturn.get(examScores.get(teacher).get(i).getStudent())
                        .add(examScores.get(teacher).get(i).getValue());
            }
        }

        return hashToReturn;
    }

    public HashMap<Student, ArrayList<Double>> partialScoresToValidate(Assistant assistant, String courseName) {
        HashMap<Student, ArrayList<Double>> hashToReturn = new HashMap<>();

        ArrayList<Double> list;

        for (int i = 0; i < partialScores.get(assistant).size(); i++) {
            if (hashToReturn.get(partialScores.get(assistant).get(i).getStudent()) == null) {
                list = new ArrayList<>();
                list.add(partialScores.get(assistant).get(i).getValue());
                hashToReturn.put(partialScores.get(assistant).get(i).getStudent(), list);
            }
            else {
                hashToReturn.get(partialScores.get(assistant).get(i).getStudent())
                        .add(partialScores.get(assistant).get(i).getValue());
            }
        }

        return hashToReturn;
    }


    public void addToExamScores(Teacher teacher, Student student, String courseName, Double grade) {
        if (examScores.get(teacher) == null) {
            ArrayList<Tuple<Student, String , Double>> list = new ArrayList<>();
            list.add(new Tuple<>(student, courseName, grade));
            examScores.put(teacher, list);
        }
        else {
            examScores.get(teacher).add(new Tuple<>(student, courseName, grade));
        }
    }

    public void addToPartialScores(Assistant assistant, Student student, String courseName, Double grade) {
        if (partialScores.get(assistant) == null) {
            ArrayList<Tuple<Student, String , Double>> list = new ArrayList<>();
            list.add(new Tuple<>(student, courseName, grade));
            partialScores.put(assistant, list);
        }
        else {
            partialScores.get(assistant).add(new Tuple<>(student, courseName, grade));
        }
    }

    public HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> getExamScores() {
        return examScores;
    }

    public HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> getPartialScores() {
        return partialScores;
    }


    @Override
    public void visit(Assistant assistant) {

        if (partialScores.containsKey(assistant)) {
            Course course_with_this_assistant = null;
            for (Course course_iterator : Catalog.getInstance().getCourses()) {
                for (Assistant assistant_iterator : course_iterator.getAssistants()) {
                    if (assistant_iterator.equals(assistant)) {
                        course_with_this_assistant = course_iterator;
                        break;
                    }
                }
            }
            if (course_with_this_assistant != null) {
                for (Tuple<Student, String, Double> tuple_iterator : partialScores.get(assistant)) {
                    if (tuple_iterator.getType().equals(course_with_this_assistant.getName())) {
                        if (course_with_this_assistant.getGrade(tuple_iterator.getStudent()) != null) {
                            course_with_this_assistant.getGrade(tuple_iterator.getStudent()).
                                    setPartialScore(tuple_iterator.getValue());
                        }
                        else {
                            course_with_this_assistant.addGrade(new Grade(tuple_iterator.getValue(), 0.0d,
                                    tuple_iterator.getStudent(), tuple_iterator.getType()));
                        }
                        System.out.println("console [ASSISTANTS] -> Grade successfully validated by " +
                                assistant.getLastName() + " " + assistant.getFirstName());
                        Catalog.getInstance().notifyObservers(course_with_this_assistant.
                                getGrade(tuple_iterator.getStudent()));
                    }
                }
            }
        }
        else {
            System.out.println("Assistant doesen't exist in HashMap");
        }
    }

    @Override
    public void visit(Teacher teacher) {

        if (examScores.containsKey(teacher)) {
            Course course_with_this_teacher = null;
            for (Course course_iterator : Catalog.getInstance().getCourses()) {
                if (course_iterator.getTeacher().equals(teacher)) {
                    course_with_this_teacher = course_iterator;
                    break;
                }
            }
            if (course_with_this_teacher != null) {
                for (Tuple<Student, String, Double> tuple_iterator : examScores.get(teacher)) {
                    if (tuple_iterator.getType().equals(course_with_this_teacher.getName())) {
                        if (course_with_this_teacher.getGrade(tuple_iterator.getStudent()) != null) {
                            course_with_this_teacher.getGrade(tuple_iterator.getStudent()).
                                    setExamScore(tuple_iterator.getValue());
                        }
                        else {
                            course_with_this_teacher.addGrade(new Grade(0.0d, tuple_iterator.getValue(),
                                    tuple_iterator.getStudent(), tuple_iterator.getType()));
                        }
                        System.out.println("console [TEACHERS] -> Grade successfully validated by " +
                                teacher.getLastName() + " " + teacher.getFirstName());

                        Catalog.getInstance().notifyObservers(course_with_this_teacher.
                                getGrade(tuple_iterator.getStudent()));
                    }
                }
            }
        }
        else {
            System.out.println("Teacher doesen't exist in HashMap!");
        }
    }

    private static class Tuple<E, T, V> {
        private final E student;
        private final T type;
        private final V value;

        public Tuple (E student, T type, V value) {
            this.student = student;
            this.type = type;
            this.value = value;
        }

        public E getStudent() {
            return student;
        }

        public T getType() {
            return type;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "student=" + student +
                    ", type=" + type +
                    ", value=" + value +
                    '}';
        }
    }
}
