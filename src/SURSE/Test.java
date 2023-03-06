package SURSE;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {

        /*****************************Parsarea fisierului json si extragerea datelor din acesta************************/

        Catalog catalog = Catalog.getInstance();

        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader("input.json");
        Object obj = jsonParser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;

        ArrayList<Course> courseArrayList = new ArrayList<>();
        ArrayList<Student> studentsList = new ArrayList<>();
        ArrayList<Group> groupsList = new ArrayList<>();
        ArrayList<Grade> gradesList = new ArrayList<>();
        HashMap<String, Group> dictionary1 = new HashMap<>();

        JSONArray courses = (JSONArray) jsonObject.get("courses");
        for (int i = 0; i < courses.size(); i++) {
            JSONObject course = (JSONObject) courses.get(i);
            String courseType = (String) course.get("type");
            String strategy = (String) course.get("strategy");
            String name = (String) course.get("name");

            JSONObject teacherNames = (JSONObject) course.get("teacher");
            String teacherFirstName = (String) teacherNames.get("firstName");
            String teacherLastName = (String) teacherNames.get("lastName");
            User teacher = UserFactory.getUser("Teacher", teacherFirstName, teacherLastName);

            HashSet<Assistant> assistantsList = new HashSet<>();
            JSONArray assistants = (JSONArray) course.get("assistants");
            for (Object o : assistants) {
                JSONObject assistant = (JSONObject) o;
                String assistantFirstName = (String) assistant.get("firstName");
                String assistantLastName = (String) assistant.get("lastName");
                User assistant1 = UserFactory.getUser("Assistant", assistantFirstName, assistantLastName);
                assistantsList.add((Assistant) assistant1);
            }

            JSONArray groups = (JSONArray) course.get("groups");
            for (Object o : groups) {
                JSONObject group = (JSONObject) o;
                String id = (String) group.get("ID");

                JSONObject assistantNames = (JSONObject) group.get("assistant");
                String assistantFirstName = (String) assistantNames.get("firstName");
                String assistantLastName = (String) assistantNames.get("lastName");

                JSONArray students = (JSONArray) group.get("students");
                for (int l = 0; l < students.size(); l++) {
                    JSONObject student = (JSONObject) students.get(l);

                    String studentFirstName = (String) student.get("firstName");
                    String studentLastName = (String) student.get("lastName");

                    JSONObject mother = (JSONObject) student.get("mother");
                    String motherFirstName = (String) mother.get("firstName");
                    String motherLastName = (String) mother.get("lastName");

                    JSONObject father = (JSONObject) student.get("father");
                    String fatherFirstName = (String) father.get("firstName");
                    String fatherLastName = (String) father.get("lastName");

                    User student1 = UserFactory.getUser("Student", studentFirstName, studentLastName);

                    User mother1 = UserFactory.getUser("Parent", motherFirstName, motherLastName);
                    User father1 = UserFactory.getUser("Parent", fatherFirstName, fatherLastName);

                    ((Student) student1).setFather((Parent) father1);
                    ((Student) student1).setMother((Parent) mother1);

                    if (catalog.getObservers().size() < 4) {
                        catalog.addObserver((Parent) mother1);
                        catalog.addObserver((Parent) father1);
                    }

                    if (gradesList.size() == 4) {
                        groupsList.clear();
                    } else {
                        Grade studentGrade = new Grade(0.0d, 0.0d, (Student) student1, name);
                        gradesList.add(studentGrade);
                    }

                    studentsList.add((Student) student1);
                    User assistant1 = UserFactory.getUser("Assistant", assistantFirstName, assistantLastName);
                    Group group1 = new Group(id, (Assistant) assistant1);
                    group1.addAll(studentsList);
                    groupsList.add(group1);
                    for (Group group_iterator : groupsList) {
                        dictionary1.put(group_iterator.getID(), group_iterator);
                    }
                    if (studentsList.size() == students.size()) {
                        studentsList.clear();
                    }
                }
            }
            if (courseType.equals("FullCourse")) {
                switch (strategy) {
                    case "BestPartialScore" -> {
                        BestPartialScore bestPartialScore = new BestPartialScore();
                        FullCourse course1 = (FullCourse) new FullCourse.FullCourseBuilder().setStrategy(bestPartialScore).
                                setGrades(gradesList).build();
                        courseArrayList.add(course1);
                    }
                    case "BestExamScore" -> {
                        BestExamScore bestExamScore = new BestExamScore();
                        FullCourse course1 = (FullCourse) new FullCourse.FullCourseBuilder().setStrategy(bestExamScore)
                                .setGrades(gradesList).build();
                        courseArrayList.add(course1);
                    }
                    case "BestTotalScore" -> {
                        BestTotalScore bestTotalScore = new BestTotalScore();
                        FullCourse course1 = (FullCourse) new FullCourse.FullCourseBuilder().setStrategy(bestTotalScore)
                                .setGrades(gradesList).build();
                        courseArrayList.add(course1);
                    }
                }
            } else if (courseType.equals("PartialScore")) {
                switch (strategy) {
                    case "BestPartialScore" -> {
                        BestPartialScore bestPartialScore = new BestPartialScore();
                        PartialCourse course1 = (PartialCourse) new PartialCourse.PartialCourseBuilder()
                                .setStrategy(bestPartialScore).setGrades(gradesList).build();
                        courseArrayList.add(course1);
                    }
                    case "BestExamScore" -> {
                        BestExamScore bestExamScore = new BestExamScore();
                        PartialCourse course1 = (PartialCourse) new PartialCourse.PartialCourseBuilder()
                                .setStrategy(bestExamScore).setGrades(gradesList).build();
                        courseArrayList.add(course1);
                    }
                    case "BestTotalScore" -> {
                        BestTotalScore bestTotalScore = new BestTotalScore();
                        PartialCourse course1 = (PartialCourse) new PartialCourse.PartialCourseBuilder()
                                .setStrategy(bestTotalScore).setGrades(gradesList).build();
                        courseArrayList.add(course1);
                    }
                }
            }
            courseArrayList.get(i).setName(name);
            courseArrayList.get(i).setTeacher((Teacher) teacher);
            courseArrayList.get(i).setAssistants(assistantsList);
            courseArrayList.get(i).setCredits(5);
            courseArrayList.get(i).setDictionary(dictionary1);
            courseArrayList.get(i).setGrades(gradesList);
        }

        JSONArray examScores = (JSONArray) jsonObject.get("examScores");
        for (Object score : examScores) {

            JSONObject examScore = (JSONObject) score;

            JSONObject student = (JSONObject) examScore.get("student");
            String studentFirstName = (String) student.get("firstName");
            String studentLastName = (String) student.get("lastName");

            String courseName = (String) examScore.get("course");
            Double grade = (Double) examScore.get("grade");

            for (Grade grade_iterator : courseArrayList.get(0).getGrades()) {
                if (grade_iterator.getStudent().getLastName().equals(studentLastName) &&
                        grade_iterator.getStudent().getFirstName().equals(studentFirstName)) {
                    grade_iterator.setExamScore(grade);
                }
            }

            for (Grade grade_iterator : courseArrayList.get(1).getGrades()) {
                if (grade_iterator.getStudent().getLastName().equals(studentLastName) &&
                        grade_iterator.getStudent().getFirstName().equals(studentFirstName)) {
                    grade_iterator.setExamScore(grade);
                }
            }
        }

        JSONArray partialScores = (JSONArray) jsonObject.get("partialScores");
        for (Object score : partialScores) {

            JSONObject partialScore = (JSONObject) score;

            JSONObject student = (JSONObject) partialScore.get("student");
            String studentFirstName = (String) student.get("firstName");
            String studentLastName = (String) student.get("lastName");

            String courseName = (String) partialScore.get("Course");
            Double grade = (Double) partialScore.get("grade");

            for (Grade grade_iterator : courseArrayList.get(0).getGrades()) {
                if (grade_iterator.getStudent().getLastName().equals(studentLastName) &&
                        grade_iterator.getStudent().getFirstName().equals(studentFirstName)) {
                    grade_iterator.setPartialScore(grade);
                }
            }

            for (Grade grade_iterator : courseArrayList.get(1).getGrades()) {
                if (grade_iterator.getStudent().getLastName().equals(studentLastName) &&
                        grade_iterator.getStudent().getFirstName().equals(studentFirstName)) {
                    grade_iterator.setPartialScore(grade);
                }
            }
        }

        FileWriter myWriter = new FileWriter("output.txt");
        myWriter.write("**Informatiile extrase din fisierul JSON**\n\n");
        for (Course course : courseArrayList) {
            myWriter.write(course + "\n\n");
        }
        /**************************************************************************************************************/

        catalog.getObservers().clear();
        catalog.getCourses().clear();

        /*********************************************Interfata grafica************************************************/

        //am luat asistentii separati pentru ca in implementarea mea am folosit HashSet pentru a stoca asistentii,
        //si HashSet nu are metoda de "get index".

        User assistantA = UserFactory.getUser("Assistant", "Adrian", "Lutan");
        User assistantB = UserFactory.getUser("Assistant", "Eduard", "Staniloiu");
        User assistantC = UserFactory.getUser("Assistant", "Mihai", "Nan");
        User assistantD = UserFactory.getUser("Assistant", "Sara", "Nucuta");

        HashSet<Assistant> soAssistants = new HashSet<>();
        soAssistants.add((Assistant) assistantA);
        soAssistants.add((Assistant) assistantB);

        ArrayList<Grade> soGrades = new ArrayList<>();
        soGrades.add(new Grade(0.0d,0.0d, courseArrayList.get(1).getAllStudents().get(0),
                "SO"));
        soGrades.add(new Grade(0.0d,0.0d, courseArrayList.get(1).getAllStudents().get(1),
                "SO"));

        Group groupA321 = new Group("321CC", (Assistant) assistantB);
        groupA321.add(courseArrayList.get(1).getAllStudents().get(0));
        groupA321.add(courseArrayList.get(1).getAllStudents().get(1));


        Group groupB321 = new Group("321CC", (Assistant) assistantD);
        groupB321.add(courseArrayList.get(1).getAllStudents().get(0));
        groupB321.add(courseArrayList.get(1).getAllStudents().get(1));

        HashMap<String, Group> pooDictionary = new HashMap<>();
        pooDictionary.put("321CC", groupB321);

        HashMap<String, Group> soDictionary = new HashMap<>();
        soDictionary.put("321CC", groupA321);

        FullCourse soCourse = (FullCourse) new FullCourse.FullCourseBuilder()
            .setName("SO").setTeacher(courseArrayList.get(1).getTeacher()).setAssistants(soAssistants)
            .setGrades(soGrades).setCredits(5).setDictionary(soDictionary).setStrategy(new BestPartialScore()).build();

        HashSet<Assistant> pooAssistants = new HashSet<>();
        pooAssistants.add((Assistant) assistantC);
        pooAssistants.add((Assistant) assistantD);

        ArrayList<Grade> pooGrades = new ArrayList<>();

        pooGrades.add(new Grade(0.0d, 0.0d,
                courseArrayList.get(0).getAllStudents().get(0), "POO"));

        pooGrades.add(new Grade(0.0d, 0.0d,
                courseArrayList.get(0).getAllStudents().get(1), "POO"));

        FullCourse pooCourse = (FullCourse) new FullCourse.FullCourseBuilder()
                .setName("POO").setTeacher(courseArrayList.get(0).getTeacher()).setAssistants(pooAssistants)
                .setGrades(pooGrades).setCredits(5).setDictionary(pooDictionary).setStrategy(new BestExamScore())
                .build();

        catalog.addCourse(pooCourse);
        catalog.addCourse(soCourse);

        ScoreVisitor scoreVisitor = new ScoreVisitor();
        catalog.addObserver(courseArrayList.get(0).getAllStudents().get(0).getMother());
        catalog.addObserver(courseArrayList.get(0).getAllStudents().get(0).getFather());

        catalog.addObserver(courseArrayList.get(0).getAllStudents().get(1).getMother());
        catalog.addObserver(courseArrayList.get(0).getAllStudents().get(1).getMother());

        scoreVisitor.addToExamScores(courseArrayList.get(1).getTeacher(),
                courseArrayList.get(1).getAllStudents().get(0), "SO", 1.4d);

        scoreVisitor.addToPartialScores((Assistant) assistantB,
                courseArrayList.get(1).getAllStudents().get(0), "SO", 1.5d);

        scoreVisitor.addToExamScores(courseArrayList.get(1).getTeacher(),
                courseArrayList.get(1).getAllStudents().get(1), "SO", 1.2d);

        scoreVisitor.addToPartialScores((Assistant) assistantB,
                courseArrayList.get(1).getAllStudents().get(1), "SO", 1.1d);

        scoreVisitor.addToExamScores(courseArrayList.get(0).getTeacher(),
                courseArrayList.get(0).getAllStudents().get(0), "POO", 3.2d);

        scoreVisitor.addToPartialScores((Assistant) assistantD,
                courseArrayList.get(0).getAllStudents().get(0), "POO", 1.8d);

        scoreVisitor.addToExamScores(courseArrayList.get(0).getTeacher(),
                courseArrayList.get(0).getAllStudents().get(1), "POO", 2.3d);

        scoreVisitor.addToPartialScores((Assistant) assistantD,
                courseArrayList.get(0).getAllStudents().get(1), "POO", 0.3d);

//        GuiLogin guiLogin = new GuiLogin(scoreVisitor);
        GuiCourse guiCourseSo = new GuiCourse(soCourse);
//        GuiCourse guiCoursePoo = new GuiCourse(pooCourse);
        /**************************************************************************************************************/
    }
}
