package cs2030.simulator;

abstract class Event implements Comparable<Event> {
    private final double eventTime;
    private final Customer customer;
    private final Server server;
    protected static final double EPSILON = 0.000001d;

    //Event
    protected static final int ARRIVE = 0;
    protected static final int SERVE = 1;
    protected static final int WAIT = 2;
    protected static final int INTER = 3;
    protected static final int LEAVE = 4;
    protected static final int DONE = 5;
    protected static final int REST = 6;

    Event(double eventTime, Customer customer, Server server) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.server = server;
    }    

    protected double getEventTime() {
        return this.eventTime;
    }

    protected Customer getCustomer() {
        return this.customer;
    }

    protected Server getServer() {
        return this.server;
    }

    private boolean isDoubleEqual(double x, double y) {
        return Math.abs(x - y) < EPSILON;
    }

    protected abstract Event execute(double n, Server s);

    protected abstract int getType();

    @Override
    public int compareTo(Event other) {
        if (!isDoubleEqual(this.eventTime, other.eventTime)) {
            if (this.eventTime < other.eventTime) {
                return -1;
            }
            if (this.eventTime > other.eventTime) {
                return 1;            
            }
        }
        return this.customer.compareTo(other.customer);
    }

    @Override 
    public String toString() {
        return String.format("%.3f %s", eventTime, customer);
    }
}
