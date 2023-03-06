package SURSE;

import java.util.ArrayList;

public class PartialCourse extends Course {

    public PartialCourse(CourseBuilder builder) {
        super(builder);
    }

    public static class PartialCourseBuilder extends CourseBuilder {
        @Override
        public Course build() {
            return new PartialCourse(this);
        }
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        for (Grade grade_iterator : getGrades()) {
            if (grade_iterator.getTotal() >= 5.0d) {
                graduatedStudents.add(grade_iterator.getStudent());
            }
        }
        return graduatedStudents;
    }
}
