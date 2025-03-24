import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        configureLogging(); // Configure logging to write to a single file

        Scanner scanner = new Scanner(System.in);

        int maxCapacity = getValidatedInput(scanner, "Enter the max ticket pool capacity:");
        TicketPool ticketPool = new TicketPool(maxCapacity);

        int vendorTickets = getValidatedInput(scanner, "Enter the number of tickets the vendor can release:");
        int vendorRate = getValidatedInput(scanner, "Enter vendor ticket release rate (seconds):");

        int customerTickets = getValidatedInput(scanner, "Enter the number of tickets the customer wants to buy:");
        int customerRate = getValidatedInput(scanner, "Enter customer retrieval rate (seconds):");

        Vendor vendor = new Vendor(vendorTickets, vendorRate, ticketPool);
        Customer customer = new Customer(ticketPool, customerRate, customerTickets);

        Thread vendorThread = null;
        Thread customerThread = null;

        boolean isRunning = false;

        while (true) {
            try {
                System.out.println("\nMenu");
                System.out.println("1. Start system");
                System.out.println("2. Stop system");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        if (!isRunning) {
                            vendorThread = new Thread(vendor, "Vendor-1");
                            customerThread = new Thread(customer, "Customer-1");
                            vendorThread.start();
                            customerThread.start();
                            isRunning = true;
                            logger.info("System started. Vendor and Customer threads are running.");
                        } else {
                            System.out.println("System is already running.");
                        }
                        break;
                    case 2:
                        if (isRunning) {
                            vendor.stop();
                            customer.stop();
                            try {
                                if (vendorThread != null) vendorThread.join();
                                if (customerThread != null) customerThread.join();
                            } catch (InterruptedException e) {
                                logger.log(Level.SEVERE, "Error while stopping threads.", e);
                                Thread.currentThread().interrupt();
                            }
                            isRunning = false;
                            logger.info("System stopped. Vendor and Customer threads terminated.");
                        } else {
                            System.out.println("System is not running.");
                        }
                        break;
                    case 3:
                        logger.info("Exiting the program.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter a valid option (1-3).");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An error occurred.", e);
                scanner.nextLine();
            }
        }
    }

    private static void configureLogging() {
        try {
            FileHandler fileHandler = new FileHandler("ticket_system.log", false); // Single log file, overwrite on restart
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Prevent logs from appearing in the console
        } catch (IOException e) {
            System.err.println("Failed to configure logging: " + e.getMessage());
        }
    }

    private static int getValidatedInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");
                int input = scanner.nextInt();
                if (input > 0) {
                    return input;
                } else {
                    System.out.println("Error: Only positive integers are allowed.");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input.");
                scanner.nextLine();
            }
        }
    }
}
