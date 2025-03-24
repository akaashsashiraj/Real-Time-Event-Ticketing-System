import java.util.logging.Logger;

public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    private final TicketPool ticketpool;
    private final int customerRetrievalRate; // In milliseconds
    private final int ticketsToBuy;
    private volatile boolean running = true; // Thread-safe flag for execution control

    public Customer(TicketPool ticketpool, int customerRetrievalRate, int ticketsToBuy) {
        this.ticketpool = ticketpool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.ticketsToBuy = ticketsToBuy;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        int purchasedTickets = 0;

        while (running && purchasedTickets < ticketsToBuy) {
            System.out.println(new java.util.Date() + " INFO: Customer run");

            Ticket ticket = ticketpool.buyTicket();
            if (ticket != null) {
                purchasedTickets++;
                String message = Thread.currentThread().getName() + " purchased " + ticket;
                logger.info(message); // Log to file
                System.out.println(new java.util.Date() + " INFO: " + message); // Print to terminal
            } else {
                String warning = Thread.currentThread().getName() + " could not find a ticket.";
                logger.warning(warning); // Log to file
                System.out.println(new java.util.Date() + " WARNING: " + warning); // Print to terminal
            }

            try {
                Thread.sleep(customerRetrievalRate); // Delay between ticket purchases
            } catch (InterruptedException e) {
                String interruptedMessage = Thread.currentThread().getName() + " was interrupted during purchase.";
                logger.warning(interruptedMessage); // Log to file
                System.out.println(new java.util.Date() + " WARNING: " + interruptedMessage); // Print to terminal
                Thread.currentThread().interrupt();
                break;
            }
        }

        String finishedMessage = Thread.currentThread().getName() + " has finished purchasing tickets.";
        logger.info(finishedMessage); // Log to file
        System.out.println(new java.util.Date() + " INFO: " + finishedMessage); // Print to terminal
    }

}
