package cs2030.simulator;

class Human extends Server {

    Human(int id, int maxQueueLength) {
        super(id, maxQueueLength);
    }

    Human(int id) {
        super(id, 0);
    }

    @Override
    protected boolean isHuman() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("server %d", super.getId());
    }
}
