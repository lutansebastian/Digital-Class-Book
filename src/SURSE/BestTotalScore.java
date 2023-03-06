package SURSE;

import java.util.ArrayList;

public class BestTotalScore implements Strategy {
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        Student bestStudent = grades.get(0).getStudent();
        Double maxGrade = grades.get(0).getTotal();

        for (Grade grade_iterator : grades) {
            if (grade_iterator.getTotal() > maxGrade) {
                maxGrade = grade_iterator.getTotal();
                bestStudent = grade_iterator.getStudent();
            }
        }
        return bestStudent;
    }
}
