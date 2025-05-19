
import java.util.*;

// Class to store a study session (date, subject, hours)
class StudySession {
    String date;
    String subject;
    double hours;

    public StudySession(String date, String subject, double hours) {
        this.date = date;
        this.subject = subject;
        this.hours = hours;
    }

    public String toString() {
        return "[" + date + "] " + subject + " - " + hours + " hours";
    }
}

// Class to represent a student
class Student {
    String name;
    String id;
    List<StudySession> sessions;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.sessions = new ArrayList<>();
    }

    public void addSession(StudySession session) {
        sessions.add(session);
    }

    public void viewProgress() {
        System.out.println("Progress for " + name + " (ID: " + id + "):");
        if (sessions.isEmpty()) {
            System.out.println("  No study sessions yet.");
        } else {
            for (StudySession s : sessions) {
                System.out.println("  " + s);
            }
        }
    }

    public double totalStudyHours() {
        double total = 0;
        for (StudySession s : sessions) {
            total += s.hours;
        }
        return total;
    }
}

// Main class to manage students and the study tracker system
public class StudyTracker {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, Student> students = new HashMap<>();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n==== Student Study Tracker ====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Study Session");
            System.out.println("3. View Student Progress");
            System.out.println("4. View Total Study Hours");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> addStudySession();
                case 3 -> viewProgress();
                case 4 -> viewTotalHours();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        if (students.containsKey(id)) {
            System.out.println("Student ID already exists!");
            return;
        }

        students.put(id, new Student(name, id));
        System.out.println("✅ Student added successfully!");
    }

    static void addStudySession() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);

        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();
        System.out.print("Enter hours studied: ");
        double hours = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        student.addSession(new StudySession(date, subject, hours));
        System.out.println("✅ Study session added.");
    }

    static void viewProgress() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);

        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        student.viewProgress();
    }

    static void viewTotalHours() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);

        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.println("📊 Total hours studied by " + student.name + ": " + student.totalStudyHours() + " hours");
    }
}
