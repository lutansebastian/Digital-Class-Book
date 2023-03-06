package SURSE;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TestFunctionalitate {

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        Catalog catalog = Catalog.getInstance();

        FileWriter myWriter = new FileWriter("output.txt");

        myWriter.write("**Users**");
        User studentA = UserFactory.getUser("Student", "Marian", "Andrei");
        User studentB = UserFactory.getUser("Student", "Andra", "Ionescu");
        User studentC = UserFactory.getUser("Student", "Maria", "Popescu");
        User studentD = UserFactory.getUser("Student", "Ionut", "Constantin");

        User motherA = UserFactory.getUser("Parent", "Simona", "Andrei");
        User fatherA = UserFactory.getUser("Parent", "Marian", "Andrei");

        User motherB = UserFactory.getUser("Parent", "Caremen", "Ionescu");
        User fatherB = UserFactory.getUser("Parent", "Adrian", "Ionescu");

        User motherC = UserFactory.getUser("Parent", "Mariana", "Popescu");
        User fatherC = UserFactory.getUser("Parent", "Ionel", "Popescu");

        User motherD = UserFactory.getUser("Parent", "Roxana", "Constantin");
        User fatherD = UserFactory.getUser("Parent", "Pavel", "Constantin");

        ((Student) studentA).setMother((Parent) motherA);
        ((Student) studentA).setFather((Parent) fatherA);

        ((Student) studentB).setMother((Parent) motherB);
        ((Student) studentB).setFather((Parent) fatherB);

        ((Student) studentC).setMother((Parent) motherC);
        ((Student) studentC).setFather((Parent) fatherC);

        ((Student) studentD).setMother((Parent) motherD);
        ((Student) studentD).setFather((Parent) fatherD);

        User assistantA = UserFactory.getUser("Assistant", "Adrian", "Lutan");
        User assistantB = UserFactory.getUser("Assistant", "Eduard", "Staniloiu");
        User assistantC = UserFactory.getUser("Assistant", "Mihai", "Nan");
        User assistantD = UserFactory.getUser("Assistant", "Sara", "Nucuta");

        User teacherA = UserFactory.getUser("Teacher", "Razvan", "Deaconescu");
        User teacherB = UserFactory.getUser("Teacher", "Carmen", "Odubasteanu");

        myWriter.write("\n\nStudents\n");
        myWriter.write(studentA + ", ");
        myWriter.write(studentB + ", ");
        myWriter.write(studentC + ", ");
        myWriter.write(studentD + ", ");
        myWriter.write("\n\nTeachers\n");
        myWriter.write(teacherA + ", ");
        myWriter.write(teacherB + ", ");
        myWriter.write("\n\nAssistants\n");
        myWriter.write(assistantA + ", ");
        myWriter.write(assistantB + ", ");
        myWriter.write(assistantC + ", ");
        myWriter.write(assistantD + ", ");


        Grade gradeA = new Grade(3.4d, 6.0d, (Student) studentA, "POO");
        Grade gradeB = new Grade(3.5d, 3.2d, (Student) studentB, "POO");
        Grade gradeC = new Grade(2.2d, 2.5d, (Student) studentC, "POO");
        Grade gradeD = new Grade(1.6d, 5.2d, (Student) studentD, "SO");

        myWriter.write("\n\nGrades\n");

        myWriter.write("A : " + gradeA + ", ");
        myWriter.write("B : " + gradeB + ", ");
        myWriter.write("C : " + gradeC + ", ");
        myWriter.write("D : " + gradeD + ", ");

        myWriter.write("\n\nClone\n");

        Grade gradeA_clone = (Grade) gradeA.clone();
        myWriter.write("gradeA_Clone " + gradeA_clone + " ");

        Grade gradeB_clone = (Grade) gradeB.clone();
        myWriter.write("gradeB_clone " + gradeB_clone + " ");

        myWriter.write("\n\nCompareTo Grade\n");
        if (gradeA.compareTo(gradeB) > 0) {
            myWriter.write("gradeA > gradeB" + " ");
        }
        if (gradeC.compareTo(gradeB) < 0){
            myWriter.write("gradeC < gradeB" + " ");
        }

        myWriter.write("\n\nGroups\n");
        Group groupA = new Group("321CC", (Assistant) assistantC);
        groupA.add((Student) studentA);
        groupA.add((Student) studentB);

        myWriter.write(String.valueOf(groupA));

        Group groupB = new Group("322CC", (Assistant) assistantB);
        groupB.add((Student) studentA);
        groupB.add((Student) studentB);
        myWriter.write(String.valueOf(groupB));

        if (groupA.equals(groupB)) {
            myWriter.write("Grupurile A si B au acelasi ID");
        }
        else {
            myWriter.write("Grupurile A si B nu au acelasi ID");
        }

        myWriter.write("\n\nCourse\n\n");

        HashSet<Assistant> pooAssistants = new HashSet<>();
        pooAssistants.add((Assistant) assistantC);
        pooAssistants.add((Assistant) assistantD);

        ArrayList<Grade> pooGrades = new ArrayList<>();
        pooGrades.add(gradeA);
        pooGrades.add(gradeB);

        HashMap<String, Group> dictionary = new HashMap<>();
        dictionary.put("321CC", groupA);

        BestExamScore bestExamScoreStrategy = new BestExamScore();

        PartialCourse pooCourse = (PartialCourse) new PartialCourse.PartialCourseBuilder()
                .setName("POO").setTeacher((Teacher) teacherB).setAssistants(pooAssistants)
                .setGrades(pooGrades).setDictionary(dictionary).setCredits(5)
                .setStrategy(bestExamScoreStrategy).build();
        myWriter.write(String.valueOf(pooCourse));

        myWriter.write("\n\ngetAssistants ");
        myWriter.write(String.valueOf(pooCourse.getAssistants()));

        myWriter.write("\n\ngetGrades ");
        myWriter.write(String.valueOf(pooCourse.getGrades()));

        myWriter.write("\n\ngetGrade of Andra Vlad" );
        myWriter.write(String.valueOf(pooCourse.getGrade((Student) studentB)));

        myWriter.write("\n\naddGrade gradeC to course ");
        pooCourse.addGrade(gradeC);
        myWriter.write(String.valueOf(pooCourse.getGrades()));

        myWriter.write("\n\ngetAllStudentsGrade ");
        myWriter.write(String.valueOf(pooCourse.getAllStudentsGrades()));

        myWriter.write("\n\ngetAllStudents ");
        myWriter.write(String.valueOf(pooCourse.getAllStudents()));

        myWriter.write("\n\ngetGraduatedStudents ");
        myWriter.write(String.valueOf(pooCourse.getGraduatedStudents()));

        myWriter.write("\n\ngetBestStudent ");
        myWriter.write(String.valueOf(pooCourse.getBestStudent()));

        myWriter.write("\n\ngetDictionary ");
        myWriter.write(String.valueOf(pooCourse.getDictionary()));


        myWriter.write("\n\nUser Notifications\n");
        myWriter.write(String.valueOf(motherA));
        myWriter.write("\ndupa setNotifications : ");
        ArrayList<Notification> notifications = new ArrayList<>();
        Notification notification1 = new Notification("Fiul tau");
        Notification notification2 = new Notification("a luat nota");
        Notification notification3 = new Notification("10");
        notifications.add(notification1);
        notifications.add(notification2);
        notifications.add(notification3);
        ((Parent) motherA).setNotifications(notifications);
        myWriter.write(String.valueOf(motherA));

        ScoreVisitor scoreVisitor = new ScoreVisitor();

        catalog.addObserver((Parent) motherA);
        catalog.addObserver((Parent) fatherA);

        catalog.addObserver((Parent) motherB);
        catalog.addObserver((Parent) fatherB);

        scoreVisitor.addToExamScores((Teacher) teacherA, (Student) studentA, "SO", 1.4d);
        scoreVisitor.addToPartialScores((Assistant) assistantA, (Student) studentA, "SO", 1.5d);

        scoreVisitor.addToExamScores((Teacher) teacherA, (Student) studentB, "SO", 1.2d);
        scoreVisitor.addToPartialScores((Assistant) assistantA, (Student) studentB, "SO", 1.1d);

        HashSet<Assistant> soAssistants = new HashSet<>();
        soAssistants.add((Assistant) assistantA);
        soAssistants.add((Assistant) assistantB);
        ArrayList<Grade> soGrades = new ArrayList<>();

        FullCourse soCourse = (FullCourse) new FullCourse.FullCourseBuilder()
                .setName("SO").setTeacher((Teacher) teacherA).setAssistants(soAssistants)
                .setGrades(soGrades).setCredits(5).setDictionary(dictionary).setStrategy(new BestTotalScore()).build();

        catalog.addCourse(soCourse);

        myWriter.write("\n\nInainte visit\n");
        myWriter.write(String.valueOf(soCourse.getGrades()));
        scoreVisitor.visit((Teacher) teacherA);
        scoreVisitor.visit((Assistant) assistantA);
        myWriter.write("\nDupa visit\n");
        myWriter.write(String.valueOf(soCourse.getGrades()));

        myWriter.write("\n\nObervers\n");
        myWriter.write("Student A : ");
        myWriter.write(String.valueOf(((Student) studentA).getFather().getNotifications()));
        myWriter.write("\nStudent B : ");
        myWriter.write(String.valueOf(((Student) studentB).getFather().getNotifications()));

        myWriter.write("\n\nMemento\n");
        myWriter.write(String.valueOf(soCourse.getGrades()));
        soCourse.makeBackup();
        myWriter.write("\nModify grades\n");
        soCourse.addGrade(new Grade(3.3d, 3.3d, (Student) studentD, "SO"));
        myWriter.write(String.valueOf(soCourse.getGrades()));
        myWriter.write("\nUndo\n");
        soCourse.undo();
        myWriter.write(String.valueOf(soCourse.getGrades()));

        /**************************************************************************************************************/
        myWriter.close();

    }
}
