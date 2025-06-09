import java.io.*;
import java.util.*;

public class GPACalculator {
    static Scanner scanner = new Scanner(System.in);
    static List<Course> courses = new ArrayList<>();
    static Map<String, Double> gradeScale = Map.ofEntries(
        Map.entry("A", 4.0), Map.entry("A-", 3.7),
        Map.entry("B+", 3.3), Map.entry("B", 3.0), Map.entry("B-", 2.7),
        Map.entry("C+", 2.3), Map.entry("C", 2.0), Map.entry("C-", 1.7),
        Map.entry("D+", 1.3), Map.entry("D", 1.0), Map.entry("F", 0.0)
    );

    public static void main(String[] args) {
        System.out.print("Load previous courses from file? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) loadCourses();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add course");
            System.out.println("2. Edit course");
            System.out.println("3. Remove course");
            System.out.println("4. Display courses");
            System.out.println("5. Calculate GPA");
            System.out.println("6. Save and Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addCourse();
                case "2" -> editCourse();
                case "3" -> removeCourse();
                case "4" -> displayCourses();
                case "5" -> calculateGPA();
                case "6" -> {
                    saveCourses();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addCourse() {
        System.out.print("Course name: ");
        String name = scanner.nextLine();
        System.out.print("Grade (A to F): ");
        String grade = scanner.nextLine().toUpperCase();
        if (!gradeScale.containsKey(grade)) {
            System.out.println("Invalid grade.");
            return;
        }
        System.out.print("Credits: ");
        try {
            double credits = Double.parseDouble(scanner.nextLine());
            if (credits <= 0) throw new NumberFormatException();
            courses.add(new Course(name, grade, credits));
            System.out.println("✅ Course added.");
        } catch (Exception e) {
            System.out.println("Invalid credit value.");
        }
    }

    static void editCourse() {
        displayCourses();
        System.out.print("Course ID to edit: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (id < 0 || id >= courses.size()) {
            System.out.println("Invalid ID.");
            return;
        }
        Course c = courses.get(id);
        System.out.print("New name (or leave blank): ");
        String name = scanner.nextLine();
        System.out.print("New grade (or leave blank): ");
        String grade = scanner.nextLine().toUpperCase();
        System.out.print("New credits (or leave blank): ");
        String creditInput = scanner.nextLine();

        if (!name.isBlank()) c.name = name;
        if (!grade.isBlank() && gradeScale.containsKey(grade)) c.grade = grade;
        if (!creditInput.isBlank()) {
            try {
                double cr = Double.parseDouble(creditInput);
                if (cr > 0) c.credits = cr;
            } catch (Exception ignored) {}
        }
        System.out.println("✅ Course updated.");
    }

    static void removeCourse() {
        displayCourses();
        System.out.print("Course ID to remove: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (id >= 0 && id < courses.size()) {
            courses.remove(id);
            System.out.println("✅ Course removed.");
        } else {
            System.out.println("Invalid ID.");
        }
    }

    static void displayCourses() {
        System.out.printf("\n%-4s%-25s%-10s%-10s\n", "ID", "Course", "Grade", "Credits");
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            System.out.printf("%-4d%-25s%-10s%-10.1f\n", i, c.name, c.grade, c.credits);
        }
    }

    static void calculateGPA() {
        double totalPoints = 0, totalCredits = 0;
        for (Course c : courses) {
            totalPoints += gradeScale.get(c.grade) * c.credits;
            totalCredits += c.credits;
        }
        double gpa = totalCredits == 0 ? 0 : totalPoints / totalCredits;
        System.out.printf("🎯 GPA: %.2f\n", gpa);
        try (PrintWriter out = new PrintWriter("gpa_report.txt")) {
            out.println("Student GPA Report");
            out.println("=".repeat(40));
            out.printf("%-25s%-10s%-10s\n", "Course", "Grade", "Credits");
            out.println("-".repeat(40));
            for (Course c : courses)
                out.printf("%-25s%-10s%-10.1f\n", c.name, c.grade, c.credits);
            out.println("=".repeat(40));
            out.printf("Final GPA: %.2f\n", gpa);
        } catch (Exception e) {
            System.out.println("❌ Error exporting report.");
        }
    }

    static void saveCourses() {
        try (PrintWriter writer = new PrintWriter("courses.csv")) {
            for (Course c : courses) {
                writer.printf("%s,%s,%.1f\n", c.name, c.grade, c.credits);
            }
            System.out.println("📦 Courses saved to courses.csv");
        } catch (IOException e) {
            System.out.println("❌ Could not save file.");
        }
    }

    static void loadCourses() {
        try (Scanner fileScanner = new Scanner(new File("courses.csv"))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                if (data.length == 3) {
                    String name = data[0];
                    String grade = data[1].toUpperCase();
                    double credits = Double.parseDouble(data[2]);
                    if (gradeScale.containsKey(grade)) {
                        courses.add(new Course(name, grade, credits));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Could not load file.");
        }
    }

    static class Course {
        String name;
        String grade;
        double credits;

        Course(String name, String grade, double credits) {
            this.name = name;
            this.grade = grade;
            this.credits = credits;
        }
    }
}
