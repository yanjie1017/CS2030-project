package cs2030.simulator;

class ServeEvent extends Event {

    ServeEvent(double eventTime, Customer customer, Server server) {
        super(eventTime, customer, server);
    }

    @Override
    protected Event execute(double n, Server ser) {
        double eventTime = super.getEventTime();
        Customer customer = super.getCustomer();
        Server server = super.getServer();
        double serveTime = customer.getServiceTime();
        if (!server.isHuman()) {
            ser.leaveQueue(customer.getId());            
        } else {
            server.leaveQueue(customer.getId());
        }
        server.serve(eventTime, serveTime);
        return new DoneEvent(eventTime + serveTime, customer, server);
    }

    @Override
    protected int getType() {
        return SERVE;
    }

    @Override 
    public String toString() {
        return super.toString() + String.format(" serves by %s", 
                                                super.getServer());
    }
}
