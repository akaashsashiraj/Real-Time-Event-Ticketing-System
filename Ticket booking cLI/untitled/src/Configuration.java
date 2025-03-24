public class Configuration {
    private int maxCapacity;
    private int vendorTickets;
    private int vendorRate;
    private int customerTickets;
    private int customerRate;

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getVendorTickets() {
        return vendorTickets;
    }

    public void setVendorTickets(int vendorTickets) {
        this.vendorTickets = vendorTickets;
    }

    public int getVendorRate() {
        return vendorRate;
    }

    public void setVendorRate(int vendorRate) {
        this.vendorRate = vendorRate;
    }

    public int getCustomerTickets() {
        return customerTickets;
    }

    public void setCustomerTickets(int customerTickets) {
        this.customerTickets = customerTickets;
    }

    public int getCustomerRate() {
        return customerRate;
    }

    public void setCustomerRate(int customerRate) {
        this.customerRate = customerRate;
    }
}
