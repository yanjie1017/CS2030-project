package cs2030.simulator;

import java.util.LinkedList;
import java.util.function.Supplier;

class Rest {
    private final LinkedList<Double> restPeriod;
    private final LinkedList<Double> restProbs;
    private final double threshold;
    private static final double DEFAULT_REST_TIME = 0.0;
    private static final double DEFAULT_REST_PROB = 0.0;

    Rest(LinkedList<Double> restPeriod, int numOfCustomer) {
        
        this.restPeriod = new LinkedList<Double>(restPeriod);
        this.restProbs = new LinkedList<Double>();
        while (this.restPeriod.size() < numOfCustomer) {
            this.restPeriod.add(DEFAULT_REST_TIME);
        }
        while (restProbs.size() < numOfCustomer) {
            this.restProbs.add(DEFAULT_REST_PROB);
        }
        this.threshold = 1;
    }

    Rest(Supplier<Double> genRestPeriod, Supplier<Double> genRandomRest, 
        int numOfCustomer, double threshold) {

        this.restPeriod = new LinkedList<Double>();
        this.restProbs = new LinkedList<Double>();
        while (this.restPeriod.size() < numOfCustomer) {
            this.restPeriod.add(genRestPeriod.get());
            this.restProbs.add(genRandomRest.get());
        }
        this.threshold = threshold;
    }

    protected double getRestPeriod() {
        if (haveToRest()) {
            return restPeriod.poll();
        } 
        return 0.0;
    }
    
    protected boolean haveToRest() {
        return restProbs.poll() < threshold;
    }

}
