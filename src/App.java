import java.util.List;
import java.util.Scanner;

public class App {

    // ANSI escape codes for color
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        // Initialize Database
        Database.initializeDatabase();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Menu loop
        while (!exit) {
            System.out.println(CYAN + "\n--- UAS Flight Log Menu ---" + RESET);
            System.out.println("1. Create Operator");
            System.out.println("2. Display All Operators");
            System.out.println("3. Display Operator by ID");
            System.out.println("4. Log a Flight (Update Operator)");
            System.out.println("5. Delete Operator");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createOperator(scanner);
                    break;
                case 2:
                    allOperators();
                    break;
                case 3:
                    displayOperatorById(scanner);
                    break;
                case 4:
                    logFlight(scanner);
                    break;
                case 5:
                    deleteOperator(scanner);
                    break;
                case 6:
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
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Airframe/Aircraft: ");
        String airframeName = scanner.nextLine();

        // Automatically handle ID generation by SQLite (using autoincrement)
        Operator operator = new Operator(0, name, numOfFlights, hours);
        operator.setAirframeName(airframeName);
        Database.addOperator(operator); // Save to database
        System.out.println(GREEN + "\nOperator created: " + operator + RESET);
    }

    // Method to delete an operator
    private static void deleteOperator(Scanner scanner) {
        System.out.print("Enter operator ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (Database.deleteOperatorById(id)) {
            System.out.println(GREEN + "Operator deleted successfully." + RESET);
        } else {
            System.out.println(RED + "Failed to delete operator. Operator not found." + RESET);
        }
    }

    // Method to display all Operators
    private static void allOperators() {
        List<Operator> operators = Database.getAllOperators(); // Fetch all from database
        if (operators.isEmpty()) {
            System.out.println(RED + "\nNo operators found." + RESET);
        } else {
            System.out.println(CYAN + "\n--- List of Operators ---" + RESET);
            for (Operator operator : operators) {
                System.out.println(operator);
            }
        }
    }

    // Method to display an Operator by ID, including hours and airframe
    private static void displayOperatorById(Scanner scanner) {
        System.out.print("Enter operator ID: ");
        int id = scanner.nextInt();

        Operator operator = Database.findOperatorById(id); // Query from database
        if (operator != null) {
            System.out.println(CYAN + "\n--- Operator Details ---" + RESET);
            System.out.println(operator);
        } else {
            System.out.println(RED + "Operator not found." + RESET);
        }
    }

    // Method to log a flight for an operator
    private static void logFlight(Scanner scanner) {
        System.out.print("Enter operator ID to log a flight: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Operator operator = Database.findOperatorById(id);
        if (operator != null) {
            System.out.print("Enter additional flight hours: ");
            double additionalHours = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter airframe used for this flight: ");
            String airframe = scanner.nextLine();

            operator.setHours(operator.getHours() + additionalHours); // Add to existing hours
            operator.setAirframeName(airframe); // Update airframe

            Database.updateOperator(operator); // Save updated info to database
            System.out.println(GREEN + "\nFlight logged successfully!" + RESET);
        } else {
            System.out.println(RED + "Operator not found." + RESET);
        }
    }
}
