package SURSE;

import java.util.ArrayList;

public class BestExamScore implements Strategy {
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        Student bestStudent = grades.get(0).getStudent();
        Double maxGrade = grades.get(0).getExamScore();

        for (Grade grade_iterator : grades) {
            if (grade_iterator.getExamScore() > maxGrade) {
                maxGrade = grade_iterator.getExamScore();
                bestStudent = grade_iterator.getStudent();
            }
        }
        return bestStudent;
    }
}
