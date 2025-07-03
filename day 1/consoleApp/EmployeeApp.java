import java.io.*;
import java.util.*;

public class EmployeeApp {
    static final String FILE_NAME = "employees.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Employee> employees = loadFromFile();

        while (true) {
            System.out.println("\n1. Create\n2. Display\n3. Raise Salary\n4. Exit");
            System.out.print("Enter choice: ");
            int choice = getValidIntInput(scanner);

            switch (choice) {
                case 1:
                    createEmployee(scanner, employees);
                    break;
                case 2:
                    displayEmployees(employees);
                    break;
                case 3:
                    raiseSalary(scanner, employees);
                    break;
                case 4:
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void createEmployee(Scanner scanner, List<Employee> employees) {
        while (true) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = getValidIntInput(scanner);

            System.out.print("Enter designation (Programmer/Manager/Tester): ");
            Designation designation = Designation.fromString(scanner.nextLine());

            if (designation == null) {
                System.out.println("Invalid designation. Skipping entry.");
            } else {
                employees.add(new Employee(name, age, designation));
                saveToFile(employees);
                System.out.println("Employee added.");
            }

            System.out.print("Add another employee? (y/n): ");
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("y"))
                break;
        }
    }

    private static void displayEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
        } else {
            System.out.println("\nEmployee List:");
            for (Employee emp : employees) {
                emp.display();
            }
        }
    }

    private static void raiseSalary(Scanner scanner, List<Employee> employees) {
        System.out.print("Enter employee name to raise salary: ");
        String name = scanner.nextLine();
        boolean found = false;

        for (Employee emp : employees) {
            if (emp.name.equalsIgnoreCase(name)) {
                System.out.print("Enter raise percent: ");
                int percent = getValidIntInput(scanner);
                emp.raiseSalary(percent);
                saveToFile(employees);
                System.out.println("Salary updated.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Employee not found.");
        }
    }

    private static int getValidIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }

    private static void saveToFile(List<Employee> employees) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                out.println(emp.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    private static List<Employee> loadFromFile() {
        List<Employee> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1]);
                    Designation desig = Designation.fromString(parts[2]);
                    int salary = Integer.parseInt(parts[3]);
                    list.add(new Employee(name, age, desig, salary));
                }
            }
        } catch (FileNotFoundException e) {
            // First run: no file yet, ignore
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }
}
