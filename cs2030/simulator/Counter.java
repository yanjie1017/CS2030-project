package cs2030.simulator;

class Counter extends Server {

    Counter(int id, int maxQueueLength) {
        super(id, maxQueueLength);
    }

    Counter(int id) {
        super(id, 0);
    }

    @Override
    protected boolean isHuman() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("self-check %d", super.getId());
    }
}
