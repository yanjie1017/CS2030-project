package cs2030.simulator;

class LeaveEvent extends Event {

    LeaveEvent(double eventTime, Customer customer, Server server) {
        super(eventTime, customer, server);
    }

    @Override
    protected Event execute(double n, Server s) {
        return this;
    }

    @Override
    protected int getType() {
        return LEAVE;
    }

    @Override 
    public String toString() {
        return super.toString() + " leaves";
    }
}
