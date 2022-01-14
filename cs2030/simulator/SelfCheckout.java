package cs2030.simulator;

class SelfCheckout extends Server {

    SelfCheckout(int id, int maxQueueLength) {
        super(id, maxQueueLength);
    }

    protected boolean isAvailable() {
        return isServable();
    }

    @Override
    protected boolean isHuman() {
        return false;
    }

    protected void takeRest(double currentTime, double restTime) {
    }

    @Override
    public String toString() {
        return String.format("self-check %d", super.getId());
    }
}
