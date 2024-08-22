import java.io.*;
import java.util.Scanner;


public class EmployeePerformanceSystem {
    private static final int MAX_EMPLOYEES = 40;
    private static Employee[] employees = new Employee[MAX_EMPLOYEES];
    private static int employeeCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Employee Performance Management System ---");
            System.out.println("1. Check available number of vacancies");
            System.out.println("2. Register employee (with ID)");
            System.out.println("3. Delete employee");
            System.out.println("4. Find employee (with employee ID)");
            System.out.println("5. Store employee details into a file");
            System.out.println("6. Load employee details from the file to the system");
            System.out.println("7. View the list of employees based on their names");
            System.out.println("8. Manage employee performance");
            System.out.println("9. Generate reports");
            System.out.println("10. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    checkVacancyAvailability();
                    break;
                case 2:
                    registerEmployee(scanner);
                    break;
                case 3:
                    deleteEmployee(scanner);
                    break;
                case 4:
                    findEmployee(scanner);
                    break;
                case 5:
                    storeEmployeeDetails();
                    break;
                case 6:
                    loadEmployeeDetails();
                    break;
                case 7:
                    viewEmployeesSortedByName();
                    break;
                case 8:
                    manageEmployeePerformance(scanner);
                    break;
                case 9:
                    generateReports(scanner);
                    break;
                case 10:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void checkVacancyAvailability() {
        System.out.println("Available vacancies: " + (MAX_EMPLOYEES - employeeCount));
    }

    private static void registerEmployee(Scanner scanner) {
        if (employeeCount >= MAX_EMPLOYEES) {
            System.out.println("No more vacancies available.");
            return;
        }

        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();

        employees[employeeCount] = new Employee(id, name);
        employeeCount++;
        System.out.println("Employee registered successfully.");
    }

    private static void deleteEmployee(Scanner scanner) {
        System.out.print("Enter employee ID to delete: ");
        String id = scanner.nextLine();

        for (int i = 0; i < employeeCount; i++) {
            if (employees[i].getId().equals(id)) {
                for (int j = i; j < employeeCount - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employeeCount--;
                System.out.println("Employee deleted successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    private static void findEmployee(Scanner scanner) {
        System.out.print("Enter employee ID to find: ");
        String id = scanner.nextLine();

        for (int i = 0; i < employeeCount; i++) {
            if (employees[i].getId().equals(id)) {
                System.out.println("Employee found: " + employees[i].getName());
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    private static void storeEmployeeDetails() {
        try (PrintWriter writer = new PrintWriter(new File("employee_data.txt"))) {
            for (int i = 0; i < employeeCount; i++) {
                Employee e = employees[i];
                writer.println(e.getId() + "," + e.getName());
                if (e.getProject() != null) {
                    writer.println(e.getProject().getScore1() + "," + e.getProject().getScore2() + "," + e.getProject().getScore3());
                } else {
                    writer.println("NA,NA,NA");
                }
            }
            System.out.println("Employee details stored successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while storing employee details.");
        }
    }

    private static void loadEmployeeDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee_data.txt"))) {
            String line;
            employeeCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    employees[employeeCount] = new Employee(parts[0], parts[1]);
                    employeeCount++;
                } else if (parts.length == 3 && employeeCount > 0) {
                    int score1 = parts[0].equals("NA") ? 0 : Integer.parseInt(parts[0]);
                    int score2 = parts[1].equals("NA") ? 0 : Integer.parseInt(parts[1]);
                    int score3 = parts[2].equals("NA") ? 0 : Integer.parseInt(parts[2]);
                    employees[employeeCount - 1].setProjectScores(score1, score2, score3);
                }
            }
            System.out.println("Employee details loaded successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading employee details.");
        }
    }

    private static void viewEmployeesSortedByName() {
        for (int i = 0; i < employeeCount - 1; i++) {
            for (int j = 0; j < employeeCount - i - 1; j++) {
                if (employees[j].getName().compareTo(employees[j + 1].getName()) > 0) {
                    Employee temp = employees[j];
                    employees[j] = employees[j + 1];
                    employees[j + 1] = temp;
                }
            }
        }

        System.out.println("Employees sorted by name:");
        for (int i = 0; i < employeeCount; i++) {
            System.out.println(employees[i].getName() + " (" + employees[i].getId() + ")");
        }
    }

    private static void manageEmployeePerformance(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < employeeCount; i++) {
            if (employees[i].getId().equals(id)) {
                System.out.print("Enter score for Project 1: ");
                int score1 = scanner.nextInt();
                System.out.print("Enter score for Project 2: ");
                int score2 = scanner.nextInt();
                System.out.print("Enter score for Project 3: ");
                int score3 = scanner.nextInt();
                scanner.nextLine();  // consume newline

                employees[i].setProjectScores(score1, score2, score3);
                System.out.println("Performance data updated successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    private static void generateReports(Scanner scanner) {
        System.out.println("Choose report type:");
        System.out.println("a. Summary Report");
        System.out.println("b. Detailed Report");
        System.out.print("Select an option: ");
        char option = scanner.nextLine().charAt(0);

        switch (option) {
            case 'a':
                generateSummaryReport();
                break;
            case 'b':
                generateDetailedReport();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void generateSummaryReport() {
        int totalEmployees = employeeCount;
        int employeesAbove40InAllProjects = 0;

        for (int i = 0; i < employeeCount; i++) {
            Project project = employees[i].getProject();
            if (project != null) {
                if (project.getScore1() > 40 && project.getScore2() > 40 && project.getScore3() > 40) {
                    employeesAbove40InAllProjects++;
                }
            }
        }

        System.out.println("Summary Report:");
        System.out.println("Total employee registrations: " + totalEmployees);
        System.out.println("Total employees scoring above 40 in all projects: " + employeesAbove40InAllProjects);
    }

    private static void generateDetailedReport() {
        // Sort employees based on average scores (Bubble Sort)
        for (int i = 0; i < employeeCount - 1; i++) {
            for (int j = 0; j < employeeCount - i - 1; j++) {
                if (employees[j].getProject() != null && employees[j + 1].getProject() != null) {
                    if (employees[j].getProject().getAverageScore() < employees[j + 1].getProject().getAverageScore()) {
                        Employee temp = employees[j];
                        employees[j] = employees[j + 1];
                        employees[j + 1] = temp;
                    }
                }
            }
        }

        System.out.println("Detailed Report:");
        System.out.println("ID | Name | Project 1 | Project 2 | Project 3 | Total | Average | Grade");
        for (int i = 0; i < employeeCount; i++) {
            if (employees[i].getProject() != null) {
                employees[i].displayReport();
            } else {
                System.out.println(employees[i].getId() + " | " + employees[i].getName() + " | NA | NA | NA | NA | NA | NA");
            }
        }
    }
}
