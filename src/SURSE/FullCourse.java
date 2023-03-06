package SURSE;

import java.util.ArrayList;

public class FullCourse extends Course {

    public FullCourse(CourseBuilder builder) {
        super(builder);
    }

    public static class FullCourseBuilder extends CourseBuilder {
        @Override
        public Course build() {
            return new FullCourse(this);
        }
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        for (Grade grade_iterator : getGrades()) {
            if (grade_iterator.getPartialScore() >= 3.0d && grade_iterator.getExamScore()>= 2.0d) {
                graduatedStudents.add(grade_iterator.getStudent());
            }
        }
        return graduatedStudents;
    }

}
