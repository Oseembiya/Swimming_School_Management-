package task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SwimSchool {

    private final Scanner scanner = new Scanner(System.in);
    private final List<Instructor> instructors = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<SwimLesson> classes = new ArrayList<>();
    private final List<WaitingList> waitingList = new ArrayList<>();

    public static void main(String[] args) {
        new SwimSchool().run(); 
    }

    public void run() {
        preloadData(); // Load initial data
        displayMainMenu(); // Display the main menu
    }

    // This is a displayMainMenu method for all options
    // This preloadData hold dammy data.
    private void preloadData() {
        // Instantiate two instructors
        Instructor instructorMark = new Instructor("Mark Bell");
        Instructor instructorJane = new Instructor("Jane Scott");
        instructors.addAll(Arrays.asList(instructorMark, instructorJane));

        // Instantiate lessons day and time 
        SwimLesson lessonNoviceMonday = new SwimLesson(Day.MONDAY, "17:00", Level.NOVICE, instructorMark);
        SwimLesson lessonIntermediateWednesday = new SwimLesson(Day.WEDNESDAY, "15:45", Level.IMPROVE, instructorJane);
        SwimLesson lessonAdvancedFriday = new SwimLesson(Day.FRIDAY, "20:00", Level.ADVANCED, instructorMark);
        classes.addAll(Arrays.asList(lessonNoviceMonday, lessonIntermediateWednesday, lessonAdvancedFriday));

        // those are instantiate students  enrolled in lessons
        Student studentJane = new Student("Jane Smith", Level.NOVICE);
        studentJane.enrollInLesson(lessonNoviceMonday);
        Student studentBob = new Student("Bob Brown", Level.IMPROVE);
        studentBob.enrollInLesson(lessonIntermediateWednesday);
        Student studentAlice = new Student("Alice Green", Level.ADVANCED);
        studentAlice.enrollInLesson(lessonAdvancedFriday);
        students.addAll(Arrays.asList(studentJane, studentBob, studentAlice));

        // Initialize the waiting list
        WaitingList generalWaitingList = new WaitingList();
        this.waitingList.add(generalWaitingList);
        generalWaitingList.addToWaitingList(studentJane);
    }

    private void displayMainMenu() {
        while (true) {
            System.out.println("\nWelcome to the Swimming  School Dashboard");
            System.out.println("==============================================");
            System.out.format("%-5s %-40s %s%n", "No.", "Action", "|");
            System.out.println("----------------------------------------------");
            System.out.format("%-5s %-40s %s%n", "1.", "View Swim Student Information", "|");
            System.out.format("%-5s %-40s %s%n", "2.", "View Swim Lesson Details", "|");
            System.out.format("%-5s %-40s %s%n", "3.", "View Instructor Schedule", "|");
            System.out.format("%-5s %-40s %s%n", "4.", "Add New Swim Student", "|");
            System.out.format("%-5s %-40s %s%n", "5.", "Award Swim Qualification", "|");
            System.out.format("%-5s %-40s %s%n", "6.", "moveToClassFromWaitingList", "|");
            System.out.format("%-5s %-40s %s%n", "9.", "Exit", "|");
            System.out.println("----------------------------------------------");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    viewSwimStudentInformation();
                    break;
                case 2:
                    viewSwimLessonDetails();
                    break;
                case 3:
                    viewInstructorSchedule();
                    break;
                case 4:
                    addNewSwimStudent();
                    break;
                case 5:
                    awardSwimQualification();
                    break;
                case 6:
                    moveToClassFromWaitingList();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    scanner.close(); // Close scanner when exiting
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewSwimStudentInformation() {
        // it Sorts the students in alphabetical order by name
        List<Student> sortedStudents = students.stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());

        if (sortedStudents.isEmpty()) {
            System.out.println("No swim students available.");
            return;
        }

        System.out.println("Select a swim student :");
        for (int i = 0; i < sortedStudents.size(); i++) {
            System.out.println((i + 1) + ". " + sortedStudents.get(i).getName() + " - Level: " + sortedStudents.get(i).getLevel());
        }

        int studentNumber = 0;
        while (studentNumber < 1 || studentNumber > sortedStudents.size()) {
            System.out.print("Enter the number of the student to view infomation : ");
            try {
                studentNumber = Integer.parseInt(scanner.nextLine());
                if (studentNumber < 1 || studentNumber > sortedStudents.size()) {
                    System.out.println("Invalid number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        Student selectedStudent = sortedStudents.get(studentNumber - 1);

        // Displaying student's class information and instructor
        List<SwimLesson> studentLessons = selectedStudent.getLessons();
        if (studentLessons.isEmpty()) {
            System.out.println("The student is not currently enrolled in any classes.");
        } else {
            SwimLesson studentLesson = studentLessons.get(0); // Assuming a student is only enrolled in one lesson
            System.out.println("Day and Time of class: " + studentLesson.getDay() + " at " + studentLesson.getStartTime());
            System.out.println("Instructor: " + studentLesson.getInstructor().getName());
        }
    }

    private void viewSwimLessonDetails() {
        boolean validSelection = false;

        while (!validSelection) {
            System.out.println("\nSelect the day of the swim class:");
            // Displaying the table headers
            System.out.printf("%-10s%n", "Days");
            System.out.println("----------");

            // Iterating over the enum values to display each day in a tabular format
            for (Day day : Day.values()) {
                System.out.printf("%-10s%n", day);
            }
            String dayInput = scanner.nextLine();
            Day selectedDay;
            try {
                selectedDay = Day.valueOf(dayInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid day. Please try again.");
                continue;
            }

            System.out.println("\nSelect the level of the swim class:");

            System.out.printf("%-10s%n", "Levels");
            System.out.println("---------------");

            // This Iterating over the enum values to display each level in a tabular format
            for (Level level : Level.values()) {
                System.out.printf("%-15s%n", level);
            }
            String levelInput = scanner.nextLine();
            Level selectedLevel;
            try {
                selectedLevel = Level.valueOf(levelInput.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid level. Please try again.");
                continue; // this Skip the rest of the loop iteration and prompt again
            }

            List<SwimLesson> filteredClasses = classes.stream()
                    .filter(swimClass -> swimClass.getDay() == selectedDay && swimClass.getLevel() == selectedLevel)
                    .collect(Collectors.toList());

            if (filteredClasses.isEmpty()) {
                System.out.println("No classes found for the selected day and level. Please try again.");
            } else {
                validSelection = true; // Valid selection made, break the loop
                for (SwimLesson swimClass : filteredClasses) {
                    System.out.println("Class at " + swimClass.getStartTime() + " with instructor "
                            + swimClass.getInstructor().getName());
                    System.out.println("Students in class:");
                    swimClass.getStudents().forEach(student -> System.out.println(student.getName()));
                    System.out.println("Spaces available: " + (4 - swimClass.getStudents().size()));
                }
            }
        }
    }

    private void viewInstructorSchedule() {
        // Sort instructors alphabetically by name
        List<Instructor> sortedInstructors = instructors.stream()
                .sorted(Comparator.comparing(Instructor::getName))
                .collect(Collectors.toList());

        if (sortedInstructors.isEmpty()) {
            System.out.println("No instructors available.");
            return; // it return no instructors exist
        }

        System.out.println("Select an instructor:");
        sortedInstructors.forEach(instructor -> System.out.println(instructor.getName()));

        String instructorName = scanner.nextLine();
        Instructor selectedInstructor = sortedInstructors.stream()
                .filter(instructor -> instructor.getName().equalsIgnoreCase(instructorName))
                .findFirst()
                .orElse(null);

        if (selectedInstructor == null) {
            System.out.println("Instructor not found. Please ensure you've entered the name correctly.");
            return; //  i Provid  feedback  to the user for incorrect input
        }

        System.out.println("Schedule for " + selectedInstructor.getName() + ":");
        List<SwimLesson> instructorClasses = classes.stream()
                .filter(swimLesson -> swimLesson.getInstructor().equals(selectedInstructor))
                .sorted(Comparator.comparing(SwimLesson::getDay)
                        .thenComparing(SwimLesson::getStartTime))
                .collect(Collectors.toList());

        if (instructorClasses.isEmpty()) {
            System.out.println("No classes scheduled for this instructor.");
        } else {
            instructorClasses.forEach(swimLesson -> {
                System.out.println("Day: " + swimLesson.getDay()
                        + ", Time: " + swimLesson.getStartTime()
                        + ", Level: " + swimLesson.getLevel());
                System.out.println("Students in this class:");
                if (swimLesson.getStudents().isEmpty()) {
                    System.out.println("\t- No students enrolled.");
                } else {
                    swimLesson.getStudents().forEach(student -> System.out.println("\t- " + student.getName()));
                }
            });
        }
    }

    private void addNewSwimStudent() { // this method add new student with a level novice
        System.out.println("Enter the name of the new student:");
        String studentName = scanner.nextLine();
        Student newStudent = new Student(studentName, Level.NOVICE);

        // Display all novice level classes with indication of space availability
        System.out.println("Weekly schedule of classes for the Novice level:");
        classes.stream()
                .filter(swimLesson -> swimLesson.getLevel() == Level.NOVICE)
                .forEach(swimLesson -> {
                    int spacesAvailable = 4 - swimLesson.getStudents().size();
                    System.out.println("Day: " + swimLesson.getDay()
                            + ", Time: " + swimLesson.getStartTime()
                            + ", Spaces available: " + (spacesAvailable > 0 ? spacesAvailable : "Full"));
                });

        // Filter classes for novice level with available spaces
        List<SwimLesson> availableClasses = classes.stream()
                .filter(swimLesson -> swimLesson.getLevel() == Level.NOVICE && swimLesson.getStudents().size() < 4)
                .collect(Collectors.toList());

        if (availableClasses.isEmpty()) {
            System.out.println("There are no available classes for the Novice level. Adding the student to the waiting list.");
            // Assuming we have a method addToWaitingList in the WaitingList class
            WaitingList generalWaitingList = waitingList.get(0); // Assuming there's a general waiting list for simplicity
            generalWaitingList.addToWaitingList(newStudent);
            System.out.println("Student has been added to the waiting list.");
            return;
        }

        System.out.println("Select a class for the student by specifying the day and time (e.g., MONDAY 17:00):");
        String selection = scanner.nextLine();
        SwimLesson selectedLesson = availableClasses.stream()
                .filter(swimLesson -> (swimLesson.getDay() + " " + swimLesson.getStartTime()).equalsIgnoreCase(selection))
                .findFirst()
                .orElse(null);

        if (selectedLesson != null) {
            selectedLesson.getStudents().add(newStudent);
            System.out.println("Student " + newStudent.getName() + " has been added to the class on "
                    + selectedLesson.getDay() + " at " + selectedLesson.getStartTime() + ".");
        } else {
            System.out.println("Invalid selection. The student has not been added to a class.");
            // Optional: Add to waiting list if the selected class is not valid
            WaitingList generalWaitingList = waitingList.get(0); // Assuming there's a general waiting list
            generalWaitingList.addToWaitingList(newStudent);
            System.out.println("Student has been added to the waiting list.");
        }
    }

    private void awardSwimQualification() {
        System.out.println("Select an instructor:");
        instructors.stream().sorted(Comparator.comparing(Instructor::getName))
                .forEach(instructor -> System.out.println(instructor.getName()));

        String instructorName = scanner.nextLine();
        Instructor selectedInstructor = instructors.stream()
                .filter(instructor -> instructor.getName().equalsIgnoreCase(instructorName))
                .findFirst().orElse(null);

        if (selectedInstructor == null) {
            System.out.println("Instructor not found.");
            return;
        }

        System.out.println("Select a swim student:");
        students.stream().sorted(Comparator.comparing(Student::getName))
                .forEach(student -> System.out.println(student.getName() + " - Level: " + student.getLevel()));

        String studentName = scanner.nextLine();
        Student selectedStudent = students.stream()
                .filter(student -> student.getName().equalsIgnoreCase(studentName))
                .findFirst().orElse(null);

        if (selectedStudent == null) {
            System.out.println("Student not found.");
            return;
        }

        // This call the unified awardQualification method with the selected student and instructor
        awardQualification(selectedStudent, selectedInstructor);
    }

    private void awardQualification(Student student, Instructor instructor) {
        System.out.println("Select the type of qualification to award:");
        System.out.println("1. Distance");
        System.out.println("2. Personal Survival");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1": // Distance
                System.out.println("Enter the distance for the qualification (e.g., 20 for 20 metres):");
                int distance = Integer.parseInt(scanner.nextLine());
                String qualificationName = distance + " metres";

                // It Check if the student already has this distance qualification
                if (!student.hasQualification(qualificationName)) {
                    DistanceSwim qualification = new DistanceSwim(distance, qualificationName, instructor);
                    student.addQualification(qualification);
                    System.out.println("Distance qualification of " + qualificationName + " awarded to " + student.getName() + " by " + instructor.getName() + ".");

                    // Automatically update student's level based on specific conditions
                    updateStudentLevelAndWaitingListIfNeeded(student, distance);
                } else {
                    System.out.println("This distance qualification has already been awarded to the student.");
                }
                break;

            case "2": // Personal Survival
                System.out.println("Select the level for Personal Survival qualification:");
                System.out.println("1. Level 1");
                System.out.println("2. Level 2");
                String levelChoice = scanner.nextLine().trim();
                String psQualificationName = "Personal Survival Level " + levelChoice;

                // It Check if the student already has this personal survival qualification
                if (!student.hasQualification(psQualificationName)) {
                    PersonalSurvival psQualification = new PersonalSurvival(Level.ADVANCED, psQualificationName, instructor);
                    student.addQualification(psQualification);
                    System.out.println("Personal Survival qualification " + psQualificationName + " awarded to " + student.getName() + " by " + instructor.getName() + ".");
                } else {
                    System.out.println("This personal survival qualification has already been awarded to the student.");
                }
                break;

            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void updateStudentLevelAndWaitingListIfNeeded(Student student, int distance) {
        boolean levelChanged = false;

        // It Check criteria for level update based on the distance qualification
        if (student.getLevel() == Level.NOVICE && distance == 20) {
            student.setLevel(Level.IMPROVE);
            levelChanged = true;
        } else if (student.getLevel() == Level.IMPROVE && distance == 400) {
            student.setLevel(Level.ADVANCED);
            levelChanged = true;
        }

        // If the student's level has changed, 
        // it add them to the waiting list for the next level's classes
        if (levelChanged) {
            System.out.println(student.getName() + "'s level updated to " + student.getLevel() + ".");

            WaitingList generalWaitingList = new WaitingList();
            generalWaitingList.addToWaitingList(student);
            System.out.println(student.getName() + " added to the waiting list for " + student.getLevel() + " level classes.");
        }
    }

    public void moveToClassFromWaitingList() {
        if (waitingList.isEmpty()) {
            System.out.println("The waiting list is currently empty.");
            return;
        }

        List<Student> waitingStudents = waitingList.get(0).getStudents(); //  single waiting list for simplicity
        if (waitingStudents.isEmpty()) {
            System.out.println("There are no students on the waiting list.");
            return;
        }

        System.out.println("Students on the waiting list:");
        waitingStudents.forEach(student -> System.out.println(student.getName() + " - Level: " + student.getLevel()));

        System.out.print("Enter the name of the student to move: ");
        String selectedStudentName = scanner.nextLine();
        Student selectedStudent = waitingStudents.stream()
                .filter(s -> s.getName().equalsIgnoreCase(selectedStudentName))
                .findFirst()
                .orElse(null);

        if (selectedStudent == null) {
            System.out.println("Student not found on the waiting list.");
            return;
        }

        List<SwimLesson> availableClasses = classes.stream()
                .filter(c -> c.getLevel() == selectedStudent.getLevel() && c.getStudents().size() < 4) // max of 4 students per class
                .collect(Collectors.toList());

        if (availableClasses.isEmpty()) {
            System.out.println("There are no available classes for this level.");
            return;
        }
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-10s %-10s %-15s %-20s%n", "Day", "Time", "Level", "Spaces Available" + "|");
        System.out.println("-------------------------------------------------------");
        availableClasses.forEach(c -> System.out.printf("%-10s %-10s %-15s %-20s%n",
                c.getDay(),
                c.getStartTime(),
                c.getLevel(),
                (4 - c.getStudents().size()) + " spaces"));

        System.out.print("Select a class by day and time (e.g., MONDAY 17:00): ");
        String classSelection = scanner.nextLine();
        SwimLesson selectedClass = availableClasses.stream()
                .filter(c -> (c.getDay().toString() + " " + c.getStartTime()).equalsIgnoreCase(classSelection))
                .findFirst()
                .orElse(null);

        if (selectedClass != null) {
            selectedClass.addStudent(selectedStudent);
            waitingList.get(0).removeFromWaitingList(selectedStudent);
            System.out.println(selectedStudent.getName() + " has been enrolled in " + selectedClass.getDay() + " at " + selectedClass.getStartTime());
        } else {
            System.out.println("No available space in selected class or class not found.");
        }
    }
}
