package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


class Shop {
    private final List<Server> serverList;
    private final int numOfServers;
    private final int numOfCounters;

    Shop(int numOfServers, int numOfCounters, int maxQueueLength) {
        this.numOfServers = numOfServers;
        this.numOfCounters = numOfCounters;
        this.serverList = createServers(numOfServers, numOfCounters, maxQueueLength);
    }

    protected static List<Server> createServers(
        int numOfServers, int numOfCounters, int maxQueueLength) {
            
        List<Server> serverList = new ArrayList<Server>();
        for (int i = 1; i <= numOfServers; i++) {
            serverList.add(new Human(i, maxQueueLength));
        }
        if (numOfCounters > 0) {
            serverList.add(new SelfCheckout(++numOfServers, maxQueueLength));
            numOfCounters--;
        }
        while (numOfCounters > 0) {
            serverList.add(new SelfCheckout(++numOfServers, 0));
            numOfCounters--;
        }
        return serverList;
    }

    protected Server selectServer() {
        return Collections.min(serverList);
    }

    protected Server selectServerGreedy() {
        int numOfCustomer = Integer.MAX_VALUE;
        Server server = serverList.get(0);
        for (Server counter:serverList) {
            if (counter.isAvailable()) {
                return counter;
            }
            if (counter.isQueable() && counter.getNumOfQueue() < numOfCustomer) {
                server = counter;
                numOfCustomer = counter.getNumOfQueue();
            }
        }
        return server;
    }

    protected Server selectCounter() {
        double minTime = Double.MAX_VALUE;
        Server server = serverList.get(0);
        for (int i = numOfServers; i < numOfServers + numOfCounters; i++) {
            if (serverList.get(i).getNextAvailableTime() < minTime) {
                minTime = serverList.get(i).getNextAvailableTime();
                server = serverList.get(i);
            }
        }
        return server;
    }

    protected double getCounterNextAvailableTime() {
        double next = Double.MAX_VALUE;  
        for (int i = numOfServers; i < numOfServers + numOfCounters; i++) {
            if (serverList.get(i).getNextAvailableTime() < next) {
                next = serverList.get(i).getNextAvailableTime();
            }
        }
        return next;
    }

    protected Server getFirstCounter() {
        return serverList.get(numOfServers);
    }
    
}
