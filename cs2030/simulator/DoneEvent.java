package cs2030.simulator;

class DoneEvent extends Event {

    DoneEvent(double eventTime, Customer customer, Server server) {
        super(eventTime, customer, server);
    }

    @Override
    protected Event execute(double restTime, Server s) {
        double eventTime = super.getEventTime();
        Customer customer = super.getCustomer();
        Server server = super.getServer();
        server.leave();
        server.takeRest(eventTime, restTime);
        return new RestEvent(eventTime + restTime, customer, server);
    }

    @Override
    protected int getType() {
        return DONE;
    }

    @Override 
    public String toString() {
        return super.toString() + String.format(" done serving by %s", 
            super.getServer());
    }
}
