package cs2030.simulator;

class WaitEvent extends Event {

    WaitEvent(double eventTime, Customer customer, Server server) {
        super(eventTime, customer, server);
    }

    @Override
    protected Event execute(double next, Server s) {
        Customer customer = super.getCustomer();
        Server server = super.getServer();
        if (!server.isHuman()) {  
            return new IntermediateEvent(next, customer, server); 
        }
        double serveTime = server.getNextAvailableTime();
        return new IntermediateEvent(serveTime, customer, server);
    }

    @Override
    protected int getType() {
        return WAIT;
    }

    @Override  
    public String toString() {
        return super.toString() + String.format(" waits at %s", super.getServer());
    }
}
