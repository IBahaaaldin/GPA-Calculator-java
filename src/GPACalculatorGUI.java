import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GPACalculatorGUI extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private java.util.List<Course> courses = new ArrayList<>();
    private final Map<String, Double> gradeScale = Map.ofEntries(
            Map.entry("A", 4.0), Map.entry("A-", 3.7), Map.entry("B+", 3.3),
            Map.entry("B", 3.0), Map.entry("B-", 2.7), Map.entry("C+", 2.3),
            Map.entry("C", 2.0), Map.entry("C-", 1.7), Map.entry("D+", 1.3),
            Map.entry("D", 1.0), Map.entry("F", 0.0));

    public GPACalculatorGUI() {
        setTitle("GPA Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[] { "Course", "Grade", "Credits" }, 0);
        table = new JTable(model);

        JPanel panel = new JPanel(new GridLayout(2, 4));
        JTextField nameField = new JTextField();
        JTextField gradeField = new JTextField();
        JTextField creditField = new JTextField();

        JButton addBtn = new JButton("Add");
        JButton loadBtn = new JButton("Load");
        JButton gpaBtn = new JButton("Calculate GPA");

        panel.add(new JLabel("Course Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Grade:"));
        panel.add(gradeField);
        panel.add(new JLabel("Credits:"));
        panel.add(creditField);
        panel.add(addBtn);
        panel.add(loadBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);
        add(gpaBtn, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String grade = gradeField.getText().toUpperCase();
            String creditStr = creditField.getText();
            if (name.isEmpty() || !gradeScale.containsKey(grade)) {
                JOptionPane.showMessageDialog(this, "Invalid input");
                return;
            }
            try {
                double credits = Double.parseDouble(creditStr);
                courses.add(new Course(name, grade, credits));
                model.addRow(new Object[] { name, grade, credits });
                nameField.setText("");
                gradeField.setText("");
                creditField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid credit value");
            }
        });

        loadBtn.addActionListener(e -> {
            try (Scanner fileScanner = new Scanner(new File("courses.csv"))) {
                model.setRowCount(0);
                courses.clear();
                while (fileScanner.hasNextLine()) {
                    String[] row = fileScanner.nextLine().split(",");
                    if (row.length == 3 && gradeScale.containsKey(row[1])) {
                        double credits = Double.parseDouble(row[2]);
                        courses.add(new Course(row[0], row[1], credits));
                        model.addRow(new Object[] { row[0], row[1], credits });
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading file");
            }
        });

        gpaBtn.addActionListener(e -> {
            double totalPoints = 0, totalCredits = 0;
            for (Course c : courses) {
                totalPoints += gradeScale.get(c.grade) * c.credits;
                totalCredits += c.credits;
            }
            double gpa = totalCredits == 0 ? 0 : totalPoints / totalCredits;
            try (PrintWriter writer = new PrintWriter("gpa_gui_report.txt")) {
                writer.println("Student GPA Report (GUI)");
                writer.println("=".repeat(40));
                writer.printf("%-25s%-10s%-10s\n", "Course", "Grade", "Credits");
                writer.println("-".repeat(40));
                for (Course c : courses) {
                    writer.printf("%-25s%-10s%-10.1f\n", c.name, c.grade, c.credits);
                }
                writer.println("=".repeat(40));
                writer.printf("Final GPA: %.2f\n", gpa);
                JOptionPane.showMessageDialog(this, "GPA: " + String.format("%.2f", gpa));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving report");
            }
        });

        setVisible(true);
    }

    static class Course {
        String name, grade;
        double credits;

        Course(String name, String grade, double credits) {
            this.name = name;
            this.grade = grade;
            this.credits = credits;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GPACalculatorGUI::new);
    }
}
