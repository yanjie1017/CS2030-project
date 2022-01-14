import cs2030.simulator.EventScheduler;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Main2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numOfServers = sc.nextInt();
        int maxQueueLength = sc.nextInt();
        List<Double> arrivalTimes = new ArrayList<Double>();
        List<Double> serviceTimes = new ArrayList<Double>();
        while (sc.hasNextDouble()) {
            arrivalTimes.add(sc.nextDouble());
            serviceTimes.add(sc.nextDouble());
        }
        EventScheduler eventScheduler = new EventScheduler(
            numOfServers, maxQueueLength, arrivalTimes, serviceTimes);
        eventScheduler.simulate();
    }
}