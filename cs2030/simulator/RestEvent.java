package cs2030.simulator;

class RestEvent extends Event {

    RestEvent(double eventTime, Customer customer, Server server) {
        super(eventTime, customer, server);
    }

    @Override
    protected Event execute(double n, Server s) {
        super.getServer().doneRest();
        return this;
    }

    @Override
    protected int getType() {
        return REST;
    }

    @Override 
    public String toString() {
        return super.toString() + String.format(" done rest by %s", 
            super.getServer());
    }
}