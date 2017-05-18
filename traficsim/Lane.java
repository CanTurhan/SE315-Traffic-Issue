package traficsim;

import traficsim.monitorobj.SynchronizedList;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Lane implements Runnable {

    private final SynchronizedList allCars = new SynchronizedList(
            new ArrayList<Car>());

    public Thread createThread() {
        return new Thread(this, "Lane");
    }

    public SynchronizedList getAllCars() {
        return this.allCars;
    }

    @SuppressWarnings("unchecked")
    public void run() {

        while (Simulation.getTimer().getTimeLeft() >= 0) {
            try {

                // Adjust speed dependent on the number of cars in the lane
                double speed = 0.0;
                if (this.allCars.size() > 0) {
                    speed = ((10.0 / this.allCars.size()) * 60.0);
                } else {
                    speed = 60.0;
                }

                this.allCars.setValues("speed", speed);

            } catch (final ConcurrentModificationException cme) {
                System.out.println("Concurrent modification caught!");
            }

            // Pick a random number of cars to remove
            final int numCarsToRemove = (int) (Math.random() * (Simulation.MAXLANESIZE / 2.0));

            // Remove a number of cars and print some summary info
            if ((numCarsToRemove > 0)
                    && (Simulation.getTimer().getTimeLeft() >= 0)) {

                final List<Car> removedCars = this.allCars
                        .remove(numCarsToRemove);

                for (final Car c : removedCars) {
                    // Add back cars that have not been on the highway for
                    // atleast 1 time period
                    if (Simulation.getCurrentTime() == c.getTimeEnteredLane()) {

                        this.allCars.addItem(c);
                    } else {
                        c.setTimeLeftLane(Simulation.getCurrentTime());
                        System.out.println("<-- @ "
                                + Simulation.getCurrentTime()
                                + c.toString()
                                + "\tTotal Time:"
                                + (Simulation.getCurrentTime() - c
                                .getTimeEnteredRamp()));
                    }
                }
                // Wait for the next chance to remove the cars if none decided
                // to get off
                try {
                    Thread.sleep(Simulation.WAITBETWEENMERGETRIES);
                } catch (final InterruptedException e) {
                    e.getMessage();
                }

            }
        }
    }

}