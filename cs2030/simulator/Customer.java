package cs2030.simulator;

import java.util.function.Supplier;

class Customer implements Comparable<Customer> {
    private final int id;
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;
    private final boolean isGreedy;
    private static final double EPSILON = 0.000001d;

    Customer(int id, double arrivalTime, Supplier<Double> serviceTime, boolean isGreedy) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.isGreedy = isGreedy;
    }

    Customer(int id, double arrivalTime, Double serviceTime) {
        this(id, arrivalTime, () -> serviceTime, false);
    }

    protected int getId() {
        return id;
    }

    protected double getArrivalTime() {
        return arrivalTime;
    }

    protected double getServiceTime() {
        return serviceTime.get();
    }

    protected boolean isGreedy() {
        return isGreedy;
    }

    @Override
    public int compareTo(Customer other) {
        if (Math.abs(this.arrivalTime - other.arrivalTime) > EPSILON) {
            if (this.arrivalTime < other.arrivalTime) {
                return -1;
            }
            if (this.arrivalTime > other.arrivalTime) {
                return 1;
            }
        }
        return this.id - other.id;
    }

    @Override
    public String toString() {
        if (isGreedy) {
            return String.format("%d(greedy)", this.id);
        }
        return String.format("%d", this.id);
    }
}
