package cs2030.simulator;

class ArriveEvent extends Event {

    ArriveEvent(Customer customer) {
        super(customer.getArrivalTime(), customer, new Human(0));
    }

    @Override
    protected Event execute(double n, Server server) {
        double eventTime = super.getEventTime();
        Customer customer = super.getCustomer();
        if (server.isAvailable()) {
            return new ServeEvent(eventTime, customer, server);
        }
        if (server.isQueable()) {
            server.joinQueue(customer.getId());
            return new WaitEvent(eventTime, customer, server);
        }
        return new LeaveEvent(eventTime, customer, server);      
    } 

    @Override
    protected int getType() {
        return ARRIVE;
    }

    @Override 
    public String toString() {
        return super.toString() + " arrives";
    }
}