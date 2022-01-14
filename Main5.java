import cs2030.simulator.EventScheduler;

import java.util.Scanner;

class Main5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int seed = sc.nextInt();
        int numOfServers = sc.nextInt();
        int numOfCounters = sc.nextInt();
        int maxQueueLength = sc.nextInt();
        int numOfCustomer = sc.nextInt();
        double arrivalRate = sc.nextDouble();
        double serveRate = sc.nextDouble();
        double restRate = sc.nextDouble();
        double restProb = sc.nextDouble();
        double greedyProb = sc.nextDouble();
        EventScheduler eventScheduler = new EventScheduler(
            seed, numOfServers, numOfCounters, 
            maxQueueLength, numOfCustomer, arrivalRate, 
            serveRate, restRate, restProb, greedyProb);
        eventScheduler.simulate();
    }
}
 