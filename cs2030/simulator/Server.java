package cs2030.simulator;

import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;

abstract class Server implements Comparable<Server> {
    private final int id;
    private final int maxQueueLength;
    private final Map<String, Double> status;
    private final Queue<Integer> queue;
    protected static final double EPSILON = 0.000001d;

    Server(int id, int maxQueueLength) {
        this.id = id;
        this.maxQueueLength = maxQueueLength; 
        this.queue = new LinkedList<Integer>();
        this.status = new HashMap<String, Double>();
        this.status.put("isServable", 1.0); 
        this.status.put("isQueable", 1.0);  
        this.status.put("isResting", 0.0);    
        this.status.put("nextAvailableTime", 0.0); 
    }

    protected boolean isServable() {
        return status.get("isServable").intValue() == 1;
    }

    protected boolean isQueable() {
        return queue.size() < maxQueueLength;
    }
    
    protected boolean isResting() {
        return status.get("isResting").intValue() == 1;
    }

    protected boolean isAvailable() {
        return isServable() && !isResting();
    }

    protected int getId() {
        return id;
    }

    protected double getNextAvailableTime() {
        return status.get("nextAvailableTime");
    }

    protected int getNumOfQueue() { 
        return queue.size();
    }

    protected void serve(double eventTime, double serviceTime) {
        status.replace("isServable", 0.0);
        status.replace("nextAvailableTime", eventTime + serviceTime);
    }

    protected void joinQueue(int id) {
        queue.add(id);
    }

    protected void leaveQueue(int id) {
        if (!queue.isEmpty() && queue.peek() == id) {
            queue.poll();
        }
    }

    protected void leave() {
        status.replace("isServable", 1.0);
    }

    protected void takeRest(double currentTime, double restTime) {
        if (Math.abs(restTime) > EPSILON) {
            status.replace("isResting", 1.0);
            status.replace("nextAvailableTime", currentTime + restTime);
        }
    }

    protected void doneRest() {
        status.replace("isResting", 0.0);
    }

    protected abstract boolean isHuman();

    @Override 
    public int compareTo(Server other) {   
        if (this.isAvailable() != other.isAvailable()) {
            if (this.isAvailable()) {
                return -1;
            }
            return 1;
        }
        if (this.isQueable() != other.isQueable()) {
            if (this.isQueable()) {
                return -1;
            }
            return 1;
        } 
        return this.getId() - other.getId();     
    }
}
