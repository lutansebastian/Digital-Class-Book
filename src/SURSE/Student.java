package SURSE;

public class Student extends User implements Comparable<Student>{
    private Parent mother, father;
    private final String firstName;
    private final String lastName;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getMother() {
        return mother;
    }

    public Parent getFather() {
        return father;
    }

    @Override
    public int compareTo(Student o) {
        return this.lastName.compareTo(o.lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
