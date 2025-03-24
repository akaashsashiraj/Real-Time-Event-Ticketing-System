import java.math.BigDecimal;

public class Ticket {

    private int ticketId;
    private  String eventName;
    private BigDecimal ticketPrice;

    public Ticket(int ticketId, String eventName, BigDecimal ticketPrice){
        this.ticketId = ticketId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getEventName() {
        return eventName;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString(){
        return"Ticket[Ticket Id= " + ticketId + " ,Event Name= "+ eventName + " ,Ticket Price= "+ ticketPrice+ "]";
    }
}
