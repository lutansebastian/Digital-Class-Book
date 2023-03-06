package SURSE;

import java.util.ArrayList;

public interface Strategy {
    public Student getBestStudent(ArrayList<Grade> grades);
}
