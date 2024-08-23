import java.io.*;
import java.util.Scanner;

public class EmployeePerformanceSystem {
    private static final int MAX_EMPLOYEES = 40;
    private static Employee[] employees = new Employee[MAX_EMPLOYEES];
    private static int employeeCount = 0;
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n\n\t** WELCOME TO EMPLOYEE PERFORMANCE MANAGEMENT SYSTEM **\n");

        while (true) { // Loop to keep showing the menu until the user exits
            System.out.println("\t\t\t******************************");
            System.out.println("\t\t\t\t\t\tMAIN MENU\t\t\t\t\t\t");
            System.out.println("\t\t\t******************************");
            System.out.println("1.Check available vacancies");
            System.out.println("2.Register an employee (with ID)");
            System.out.println("3.Delete an employee");
            System.out.println("4.Find an employee (with ID)");
            System.out.println("5.Store employee details into a file");
            System.out.println("6.Load employee details from the file to the system");
            System.out.println("7.View the list of employees based on their names");
            System.out.println("8.Manage employee performance");
            System.out.println("9.Generate reports");
            System.out.println("10.Exit");

            try {
                System.out.print("\nSelect an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        checkVacancyAvailability();
                        break;
                    case 2:
                        registerEmployee(sc);
                        break;
                    case 3:
                        deleteEmployee(sc);
                        break;
                    case 4:
                        findEmployee(sc);
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
                        manageEmployeePerformance(sc);
                        break;
                    case 9:
                        generateReports(sc);
                        break;
                    case 10:
                        System.out.println("Exiting...");
                        return; // Exit the program
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid option!");
                sc.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void checkVacancyAvailability() {
        System.out.println("\nAvailable workers vacancies: " + (MAX_EMPLOYEES - employeeCount));
    }

    private static void registerEmployee(Scanner scanner) {
        if (employeeCount >= MAX_EMPLOYEES) {
            System.out.println("No more vacancies available.");
            return;
        }

        String id;
        String name;

        // Validate ID
        while (true) {
            System.out.print("Enter employee ID (7 characters long and starts with 'S'): ");
            id = scanner.nextLine().trim();


            if (id == null || id.isEmpty() || id.length() != 7 || id.charAt(0) != 'S') {
                System.out.println("Invalid ID. ID must be 7 characters long and start with 'S'. Please try again.");
                continue;
            }


            boolean idExists = false;
            for (int i = 0; i < employeeCount; i++) {
                if (employees[i].getId().equals(id)) {
                    idExists = true;
                    break;
                }
            }

            if (idExists) {
                System.out.println("This ID is already registered. Please enter a unique ID.");
            } else {
                break;
            }
        }


        while (true) {
            System.out.print("Enter employee name: ");
            name = scanner.nextLine().trim();

            // Check if name is null or empty
            if (name == null || name.isEmpty()) {
                System.out.println("Employee name cannot be null or empty. Please try again.");
            } else {
                break;
            }
        }


        employees[employeeCount] = new Employee(id, name);
        employeeCount++;
        System.out.println("Employee registered successfully.");
    }

    private static void deleteEmployee(Scanner scanner) {
        System.out.print("Enter employee ID to delete (7 characters long and starts with 'S'): ");
        String id = scanner.nextLine().trim();

        // Validate the ID format
        if (id == null || id.isEmpty() || id.length() != 7 || id.charAt(0) != 'S') {
            System.out.println("Invalid ID. ID must be 7 characters long and start with 'S'.");
            return;
        }

        // Search for the employee
        for (int i = 0; i < employeeCount; i++) {
            if (employees[i].getId().equals(id)) {
                // Display employee details
                System.out.println("Employee found:");
                System.out.println("ID: " + employees[i].getId());
                System.out.println("Name: " + employees[i].getName());

                // Ask for confirmation
                System.out.print("Do you want to delete this employee? (yes/no): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("yes")) {
                    // Delete the employee
                    for (int j = i; j < employeeCount - 1; j++) {
                        employees[j] = employees[j + 1];
                    }
                    employeeCount--;
                    System.out.println("Employee deleted successfully.");
                } else {
                    System.out.println("Deletion cancelled.");
                }
                return;
            }
        }
        System.out.println("Employee not found.");
    }


    private static void findEmployee(Scanner scanner) {
        System.out.print("Enter employee ID to find (7 characters long and starts with 'S'): ");
        String id = scanner.nextLine().trim();

        // Validate the ID format
        if (id == null || id.isEmpty() || id.length() != 7 || id.charAt(0) != 'S') {
            System.out.println("Invalid ID. ID must be 7 characters long and start with 'S'.");
            return;
        }

        // Search for the employee
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
        String id;
        int score1 = -1, score2 = -1, score3 = -1;

        // Validate and get employee ID
        while (true) {
            System.out.print("Enter employee ID (7 characters long and starts with 'S'): ");
            id = scanner.nextLine().trim();

            if (id.length() == 7 && id.charAt(0) == 'S') {
                boolean found = false;
                for (int i = 0; i < employeeCount; i++) {
                    if (employees[i].getId().equals(id)) {
                        found = true;
                        // Get and validate project scores
                        while (score1 < 0 || score1 > 100) {
                            System.out.print("Enter score for Project 1 (0-100): ");
                            if (scanner.hasNextInt()) {
                                score1 = scanner.nextInt();
                                if (score1 < 0 || score1 > 100) {
                                    System.out.println("Invalid score. Please enter a value between 0 and 100.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid integer.");
                                scanner.next(); // Consume invalid input
                            }
                        }
                        while (score2 < 0 || score2 > 100) {
                            System.out.print("Enter score for Project 2 (0-100): ");
                            if (scanner.hasNextInt()) {
                                score2 = scanner.nextInt();
                                if (score2 < 0 || score2 > 100) {
                                    System.out.println("Invalid score. Please enter a value between 0 and 100.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid integer.");
                                scanner.next(); // Consume invalid input
                            }
                        }
                        while (score3 < 0 || score3 > 100) {
                            System.out.print("Enter score for Project 3 (0-100): ");
                            if (scanner.hasNextInt()) {
                                score3 = scanner.nextInt();
                                if (score3 < 0 || score3 > 100) {
                                    System.out.println("Invalid score. Please enter a value between 0 and 100.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid integer.");
                                scanner.next(); // Consume invalid input
                            }
                        }

                        scanner.nextLine(); // Consume newline after integer input
                        employees[i].setProjectScores(score1, score2, score3);
                        System.out.println("Performance data updated successfully.");
                        return;
                    }
                }
                if (found) break;
                System.out.println("Employee not found. Please try again.");
            } else {
                System.out.println("Invalid ID format. ID must be 7 characters long and start with 'S'.");
            }
        }
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
        System.out.println("Employee Name\tAverage Score\tPerformance");

        for (int i = 0; i < employeeCount; i++) {
            Project project = employees[i].getProject();
            if (project != null) {
                double averageScore = project.getAverageScore();
                String performance;

                if (averageScore >= 80) {
                    performance = "Excellent";
                } else if (averageScore >= 60) {
                    performance = "Good";
                } else if (averageScore >= 40) {
                    performance = "Average";
                } else {
                    performance = "Poor";
                }

                System.out.println(employees[i].getName() + "\t" + averageScore + "\t\t" + performance);
            }
        }
    }
}
