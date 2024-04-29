
package task2;

import java.util.ArrayList;
import java.util.List;


class WaitingList {

    private final List<Student> students;

    public WaitingList() {
        this.students = new ArrayList<>();
    }

    // Method to add a student to the waiting list
    public void addToWaitingList(Student student) {
        students.add(student);
    }

    // Method to remove a student from the waiting list
    public boolean removeFromWaitingList(Student student) {
        return students.remove(student);
    }

    // the list of students
    public List<Student> getStudents() {
        return students;
    }

    }
