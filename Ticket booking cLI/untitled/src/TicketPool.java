import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private Queue<Ticket> tickets;
    private int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.tickets = new LinkedList<>();
        this.maxCapacity = maxCapacity;
    }

    public synchronized boolean addTicket(Ticket ticket) {
        while (tickets.size() >= maxCapacity) {
            System.out.println("Ticket pool is full. Vendor is waiting...");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Vendor thread interrupted while waiting");
                Thread.currentThread().interrupt();
                return false;
            }
        }
        tickets.add(ticket);
        System.out.println("Tickets added " + ticket);
        notify();
        return true;
    }

    public synchronized Ticket buyTicket() {
        while (tickets.isEmpty()) {
            System.out.println("No ticket is available. Customer is waiting...\n System started. Vendor and Customer threads are running.");
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Customer thread interrupted while waiting");
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Ticket ticket = tickets.poll();
        System.out.println("Ticket purchased: " + ticket);
        notify();
        return ticket;
    }

    // Method to get the number of available tickets
    public synchronized int getAvailableTickets() {
        return tickets.size();
    }
}
