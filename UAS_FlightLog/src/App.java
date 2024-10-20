import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static List<Operator> operatorList = new ArrayList<>();
    private static int nextId = 1;

    // ANSI escape codes for color
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Menu loop
        while (!exit) {
            System.out.println(CYAN + "\n--- UAS Flight Log Menu ---" + RESET);
            System.out.println("1. Create Operator");
            System.out.println("2. Create Hours");
            System.out.println("3. Create Airframe");
            System.out.println("4. Display All Operators");
            System.out.println("5. Display Operator by ID");
            System.out.println("6. Log a Flight (Update Operator)");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createOperator(scanner);
                    break;
                case 2:
                    createHours(scanner);
                    break;
                case 3:
                    createAirframe(scanner);
                    break;
                case 4:
                    allOperators();
                    break;
                case 5:
                    displayOperatorById(scanner);
                    break;
                case 6:
                    logFlight(scanner);
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println(RED + "Invalid option, please try again." + RESET);
            }
        }

        scanner.close();
    }

    // Method to create an Operator
    private static void createOperator(Scanner scanner) {
        System.out.print("Enter operator name: ");
        String name = scanner.nextLine();

        System.out.print("Enter number of flights: ");
        int numOfFlights = scanner.nextInt();

        System.out.print("Enter total hours: ");
        double hours = scanner.nextDouble();

        Operator operator = new Operator(nextId++, name, numOfFlights, hours);
        operatorList.add(operator);
        System.out.println(GREEN + "\nOperator created: " + operator + RESET);
    }

    // Method to create Hours for an operator
    private static void createHours(Scanner scanner) {
        System.out.print("Enter operator ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Operator operator = findOperatorById(id);
        if (operator != null) {
            System.out.print("Enter total flight hours: ");
            double hours = scanner.nextDouble();

            operator.setHours(hours);
            System.out.println(GREEN + "\nHours updated: " + operator + RESET);
        } else {
            System.out.println(RED + "Operator not found." + RESET);
        }
    }

    // Method to create an Airframe for an operator
    private static void createAirframe(Scanner scanner) {
        System.out.print("Enter operator ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Operator operator = findOperatorById(id);
        if (operator != null) {
            System.out.print("Enter airframe name: ");
            String airframeName = scanner.nextLine();

            operator.setAirframeName(airframeName); // Set the airframe for this operator
            System.out.println(GREEN + "\nAirframe updated: " + operator + RESET);
        } else {
            System.out.println(RED + "Operator not found." + RESET);
        }
    }

    // Method to display all Operators
    private static void allOperators() {
        if (operatorList.isEmpty()) {
            System.out.println(RED + "\nNo operators found." + RESET);
        } else {
            System.out.println(CYAN + "\n--- List of Operators ---" + RESET);
            for (Operator operator : operatorList) {
                System.out.println(operator);
            }
        }
    }

    // Method to display an Operator by ID, including hours and airframe
    private static void displayOperatorById(Scanner scanner) {
        System.out.print("Enter operator ID: ");
        int id = scanner.nextInt();

        Operator operator = findOperatorById(id);
        if (operator != null) {
            System.out.println(CYAN + "\n--- Operator Details ---" + RESET);
            System.out.println("Operator ID: " + operator.getId());
            System.out.println("Name: " + operator.getName());
            System.out.println("Number of Flights: " + operator.getNumOfFlights());
            System.out.println("Total Hours: " + (operator.getHours() != null ? operator.getHours() : "Not set"));
            System.out.println(
                    "Airframe: " + (operator.getAirframeName() != null ? operator.getAirframeName() : "Not set"));
        } else {
            System.out.println(RED + "Operator not found." + RESET);
        }
    }

    // Method to log a flight for an operator
    private static void logFlight(Scanner scanner) {
        System.out.print("Enter operator ID to log a flight: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Operator operator = findOperatorById(id);
        if (operator != null) {
            System.out.print("Enter additional flight hours: ");
            double additionalHours = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter airframe used for this flight: ");
            String airframe = scanner.nextLine();

            if (operator.getHours() == null) {
                operator.setHours(additionalHours);
            } else {
                operator.setHours(operator.getHours() + additionalHours); // Add to existing hours
            }

            // Update airframe
            operator.setAirframeName(airframe);

            System.out.println(GREEN + "\nFlight logged successfully!" + RESET);
            findOperatorById(operator.getId()); // Show updated profile
        } else {
            System.out.println(RED + "Operator not found." + RESET);
        }
    }

    // Method to find an operator by ID
    private static Operator findOperatorById(int id) {
        for (Operator operator : operatorList) {
            if (operator.getId() == id) {
                return operator;
            }
        }
        return null;
    }
}
