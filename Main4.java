import cs2030.simulator.EventScheduler;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

class Main4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numOfServers = sc.nextInt();
        int numOfCounters = sc.nextInt();
        int maxQueueLength = sc.nextInt();
        int numOfCustomer = sc.nextInt();
        List<Double> arrivalTimes = new ArrayList<Double>();
        List<Double> serviceTimes = new ArrayList<Double>();
        LinkedList<Double> restTimes = new LinkedList<Double>();
        for (int i = 0; i < numOfCustomer; i++) {
            arrivalTimes.add(sc.nextDouble());
            serviceTimes.add(sc.nextDouble());
        }
        for (int i = 0; i < numOfCustomer; i++) {
            restTimes.add(sc.nextDouble());
        }
        EventScheduler eventScheduler = new EventScheduler(
            numOfServers, numOfCounters, maxQueueLength, arrivalTimes, serviceTimes, restTimes);
        eventScheduler.simulate();
    }
}