package cs2030.simulator;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class EventScheduler {
    private final Shop shop;
    private final Rest resting;
    private final List<Customer> customerList;
    private final PriorityQueue<Event> eventQueue;
    private static final double DEFAULTsERVICE_TIME = 1.0;
    private static final int DEFAULT_QUEUE_LENGTH = 1;
    private static final int DEFAULT_NUM_COUNTER = 0;

    //Event
    private static final int ARRIVE = 0;
    private static final int SERVE = 1;
    private static final int WAIT = 2;
    private static final int INTER = 3;
    private static final int LEAVE = 4;
    private static final int DONE = 5;
    private static final int REST = 6;

    /** 
     * Create EventScheduler using Random Generator.
     * @param seed base seed for the RandomGenerator
     * @param numOfServers number of human servers 
     * @param numOfCounters the number of self-checkout counters
     * @param maxQueueLength maximum queue length
     * @param numOfCustomers number of customers/arrival events
     * @param arrivalRate arrival rate of customers
     * @param serveRate service rate of customers
     * @param restRate rest rate of human servers
     * @param restProb probability that the server rests
     * @param greedyProb probability that the customer is greedy 
     */
    public EventScheduler(int seed, int numOfServers, int numOfCounters, 
        int maxQueueLength, int numOfCustomers, double arrivalRate, 
        double serveRate, double restRate, double restProb, double greedyProb) {

        RandomGenerator r = new RandomGenerator(seed, arrivalRate, serveRate, restRate);
        this.shop = new Shop(numOfServers, numOfCounters, maxQueueLength);
        this.customerList = createRandomCustomers(r, numOfCustomers, greedyProb);
        this.resting = new Rest(() -> r.genRestPeriod(), () -> r.genRandomRest(), 
            numOfCustomers, restProb);
        this.eventQueue = new PriorityQueue<Event>();
    }

    /** 
     * Create EventScheduler.
     * @param numOfServers number of human servers 
     * @param numOfCounters the number of self-checkout counters
     * @param maxQueueLength maximum queue length
     * @param arrivalTimes list of arrival time of customers
     * @param serviceTimes list of service time of customers
     * @param restTimes list of rest time of human servers
     */
    public EventScheduler(int numOfServers, int numOfCounters, int maxQueueLength, 
        List<Double> arrivalTimes, List<Double> serviceTimes, 
        LinkedList<Double> restTimes) {

        this.shop = new Shop(numOfServers, numOfCounters, maxQueueLength);
        this.customerList = createNormalCustomers(arrivalTimes, serviceTimes);
        this.resting = new Rest(restTimes, arrivalTimes.size());
        this.eventQueue = new PriorityQueue<Event>();
    }

    /** 
     * Create EventScheduler.
     * Default numOfCounters = 0.
     * @param numOfServers number of human servers 
     * @param maxQueueLength maximum queue length
     * @param arrivalTimes list of arrival time of customers
     * @param serviceTimes list of service time of customers
     * @param restTimes list of rest time of human servers
     */
    public EventScheduler(int numOfServers, int maxQueueLength, List<Double> arrivalTimes, 
        List<Double> serviceTimes, LinkedList<Double> restTimes) {
        this(numOfServers, DEFAULT_NUM_COUNTER, maxQueueLength, 
            arrivalTimes, serviceTimes, restTimes);
    }

    /** 
     * Create EventScheduler.
     * Default numOfCounters = 0.
     * Default restTime = 1.0.
     * @param numOfServers number of human servers (no self-checkout counters)
     * @param maxQueueLength maximum queue length
     * @param arrivalTimes list of arrival time of customers
     * @param serviceTimes list of service time of customers
     */
    public EventScheduler(int numOfServers, int maxQueueLength, 
        List<Double> arrivalTimes, List<Double> serviceTimes) {

        this(numOfServers, DEFAULT_NUM_COUNTER, maxQueueLength, 
            arrivalTimes, serviceTimes, new LinkedList<Double>());
    }

    /** 
     * Create EventScheduler.
     * Default numOfCounters = 0.
     * Default maxQueueLength = 1.
     * Default serviceTime = 1.0.
     * Default restTime = 1.0.
     * @param numOfServers number of human servers (no self-checkout counters)
     * @param arrivalTimes list of arrival time of customers
     */
    public EventScheduler(int numOfServers, List<Double> arrivalTimes) {

        this(numOfServers, DEFAULT_NUM_COUNTER, DEFAULT_QUEUE_LENGTH, 
            arrivalTimes, new ArrayList<Double>(), new LinkedList<Double>());
    }

    private static List<Customer> createNormalCustomers(
        List<Double> arrivalTimes, List<Double> serviceTimes) {

        List<Customer> customerList = new ArrayList<Customer>();
        if (serviceTimes.isEmpty()) {
            for (int i = 0; i < arrivalTimes.size(); i++) {
                customerList.add(new Customer(i + 1, arrivalTimes.get(i), 
                    DEFAULTsERVICE_TIME));
            }  
        } else {
            for (int i = 0; i < arrivalTimes.size(); i++) {
                double serveTime = serviceTimes.get(i);
                customerList.add(new Customer(i + 1, arrivalTimes.get(i), serveTime));
            }  
        }         
        return customerList;
    }

    private static List<Customer> createRandomCustomers(
        RandomGenerator r, int numOfCustomers, double greedyProb) {

        List<Customer> customerList = new ArrayList<Customer>();
        double now = 0;
        for (int i = 1; i <= numOfCustomers; i++) {
            customerList.add(new Customer(i, now, () -> r.genServiceTime(), 
                r.genCustomerType() < greedyProb));
            now += r.genInterArrivalTime();
        }
        return customerList;
    }

    private void createArriveEvents() {
        for (int i = 0; i < this.customerList.size(); i++) {
            this.eventQueue.add(new ArriveEvent(this.customerList.get(i)));
        }
    }

    /**
     * Start the simulation and output the events in order.
     */
    public void simulate() {
        createArriveEvents();

        double t = 0.0;
        Server s = new Human(0);

        int numOfServe = 0;
        int numOfLeave = 0;
        double totalWaitingTime = 0.0;
        
        while (!eventQueue.isEmpty()) {
            Event e = eventQueue.poll();
            Event ed;

            //rest period
            if (e.getType() == DONE && e.getServer().isHuman()) {  
                t = resting.getRestPeriod();
            } else {
                t = 0;
            }

            if (e.getType() == ARRIVE) {
                if (e.getCustomer().isGreedy()) {
                    ed = e.execute(t, shop.selectServerGreedy());
                } else {
                    ed = e.execute(t, shop.selectServer());
                }
            } else if (e.getType() == WAIT && !e.getServer().isHuman()) {
                ed = e.execute(shop.getCounterNextAvailableTime(), s);                     
            } else if (e.getType() == INTER && !e.getServer().isHuman()) {                  
                ed = e.execute(shop.getCounterNextAvailableTime(), shop.selectCounter());   
            } else if (e.getType() == SERVE && !e.getServer().isHuman()) {
                ed = e.execute(t, shop.getFirstCounter());
            } else {
                ed = e.execute(t, s);
            }

            if (e.getType() == REST) {
                continue;
            }

            if (e.getType() == SERVE) {
                numOfServe++;
                totalWaitingTime += e.getEventTime() - e.getCustomer().getArrivalTime();
            }

            if (e.getType() == LEAVE) {
                numOfLeave++;
            }

            //enqueue
            if (e.getType() != LEAVE) {
                eventQueue.add(ed);
            }

            if (e.getType() == INTER) {
                continue;
            }    

            System.out.println(e);
        }
        System.out.println(String.format("[%.3f %d %d]", 
            totalWaitingTime / numOfServe, numOfServe, numOfLeave));
    }



}