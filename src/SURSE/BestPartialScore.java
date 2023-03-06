package SURSE;

import java.util.ArrayList;

public class BestPartialScore implements Strategy {
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        Student bestStudent = grades.get(0).getStudent();
        Double maxGrade = grades.get(0).getPartialScore();

        for (Grade grade_iterator : grades) {
            if (grade_iterator.getPartialScore() > maxGrade) {
                maxGrade = grade_iterator.getPartialScore();
                bestStudent = grade_iterator.getStudent();
            }
        }
        return bestStudent;
    }
}
