import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());
    private int maxTickets;
    private int ticketReleaseRate;
    private TicketPool ticketPool;
    private volatile boolean running = true; // Flag to control thread execution

    public Vendor(int maxTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.maxTickets = maxTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;

        // Configure the logger to log to a file
        LogManager.configureLogger(logger, "ticket_system.log");
    }

    // Stop method to set the running flag to false
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        int ticketReleased = 0;

        while (running && ticketReleased < maxTickets) {
            Ticket newTicket = new Ticket(ticketReleased + 1, "Special Event", BigDecimal.valueOf(500));

            if (ticketPool.addTicket(newTicket)) {
                String message = "Vendor added ticket: " + newTicket;
                logger.info(message); // Log to file
                System.out.println(new java.util.Date() + " INFO: " + message); // Print to terminal
                ticketReleased++;
            } else {
                System.out.println(new java.util.Date() + " WARNING: Vendor could not add ticket: pool is full");
            }

            try {
                Thread.sleep(ticketReleaseRate * 100); // Simulate ticket release delay
            } catch (InterruptedException e) {
                System.out.println(new java.util.Date() + " WARNING: Vendor thread was interrupted during release");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(new java.util.Date() + " INFO: Vendor has stopped releasing tickets.");
    }

}
