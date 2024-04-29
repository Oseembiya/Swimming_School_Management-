package task2;

import java.util.ArrayList;
import java.util.List;

public class SwimLesson {
    private final Day day;
    private final String startTime;
    private final Level level;
    private final Instructor instructor;
    private final List<Student> students;

    public SwimLesson(Day day, String startTime, Level level, Instructor instructor) {
        this.day = day;
        this.startTime = startTime;
        this.level = level;
        this.instructor = instructor;
        this.students = new ArrayList<>();
    }

    // Method to add a student to the lesson
    public void addStudent(Student student) {
        if (!hasSpace()) {
            System.out.println("No space available for this class.");
            return;
        }
        this.students.add(student);
    }

    // Checks if the class can accept more students
    public boolean hasSpace() {
        return this.students.size() < 4; // maximum of 4 students per class
    }

  
    public List<Student> getStudents() {
        return this.students;
    }

    public Day getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public Level getLevel() {
        return level;
    }

    public Instructor getInstructor() {
        return instructor;
    }


    public String getDayAndTime() {
        return day + " " + startTime;
    }
}
