package cs2030.simulator;

class IntermediateEvent extends Event {

    IntermediateEvent(double eventTime, Customer customer, Server server) {
        super(eventTime, customer, server);
    }

    @Override
    protected Event execute(double next, Server ser) {
        double eventTime = super.getEventTime();
        Customer customer = super.getCustomer();
        Server server = super.getServer();
        double serveTime = server.getNextAvailableTime();
        if (!server.isHuman()) {
            server = ser;  
            serveTime = next;       
        }                       
        if (server.isAvailable()) {
            return new ServeEvent(eventTime, customer, server);
        }                      
        return new IntermediateEvent(serveTime, customer, server);
    }

    @Override
    protected int getType() {
        return INTER;
    }

    @Override  
    public String toString() {
        return super.toString() + String.format(" waits (inter) at %s", 
            super.getServer());
    }
}
