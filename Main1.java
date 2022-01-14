import cs2030.simulator.EventScheduler;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numOfServers = sc.nextInt();
        List<Double> arrivalTimes = new ArrayList<Double>();
        while (sc.hasNextDouble()) {
            arrivalTimes.add(sc.nextDouble());
        }
        EventScheduler eventScheduler = new EventScheduler(numOfServers, arrivalTimes);
        eventScheduler.simulate();
    }
}