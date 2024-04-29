
package task2;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private Level level; 
    private final List<SwimLesson> lessons; // List to hold enrolled lessons
    private final List<Qualification> qualifications; // List to hold awarded qualifications

    public Student(String name, Level level) {
        this.name = name;
        this.level = level;
        this.lessons = new ArrayList<>();
        this.qualifications = new ArrayList<>(); // Initialize the qualifications list
    }

    // Method to enroll in a lesson
    public void enrollInLesson(SwimLesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
            lesson.addStudent(this); // Ensuring bi-directional relationship
        }
    }

    // Method to check if a qualification already exists
    public boolean hasQualification(String qualificationName) {
        return qualifications.stream().anyMatch(q -> q.getName().equals(qualificationName));
    }

    // Method to add a qualification if it doesn't already exist
    public void addQualification(Qualification qualification) {
        if (!hasQualification(qualification.getName())) {
            qualifications.add(qualification);
            System.out.println("Qualification '" + qualification.getName() + "' added to student " + name);
        } else {
            System.out.println("Student " + name + " already has the qualification '" + qualification.getName() + "'.");
        }
    }

   
    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) { // Setter for level to update student's level
        this.level = level;
    }

    public List<SwimLesson> getLessons() {
        return lessons;
    }

    public List<Qualification> getQualifications() { // Getter for qualifications
        return qualifications;
    }

}
