package SURSE;

public class UserFactory {

    public static User getUser(String type, String firstName, String lastName) {
        return switch (type) {
            case "Student" -> new Student(firstName, lastName);
            case "Parent" -> new Parent(firstName, lastName);
            case "Assistant" -> new Assistant(firstName, lastName);
            case "Teacher" -> new Teacher(firstName, lastName);
            default -> null;
        };
    }
}
