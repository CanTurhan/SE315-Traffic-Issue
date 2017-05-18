package traficsim;

import java.util.ArrayList;
import java.util.List;

public class Ramp implements Runnable {

    private final List<Car> allCars = new ArrayList<Car>();

    private int carIndex;

    private Lane lane;

    private String rampID;

    private boolean rampOn;

    public Ramp(String name) {
        this.rampID = name;
        this.carIndex = 0;
        this.rampOn = false;
    }

    private void enterHighWay() {

        if (this.lane.getAllCars().size() < Simulation.MAXLANESIZE) {

            // Remove the first car on the ramp
            final Car enteringCar = this.allCars.remove(0);

            // Record when the car moved from the Ramp to the Lane
            enteringCar.setTimeEnteredLane(Simulation.getCurrentTime());
            this.lane.getAllCars().addItem(enteringCar);

            System.out.println("--> @ " + Simulation.getCurrentTime()
                    + enteringCar.toString());
        } else {
            // If the lane is full, wait the specified period of time before
            // trying to merger onto the lane
            System.out.println("XXX @ " + Simulation.getCurrentTime()
                    + this.toString() + "\tLANE FULL\tCAR:"
                    + this.allCars.get(0).getID() + "\tNot Added to Lane");
            try {
                Thread.sleep(Simulation.WAITBETWEENMERGETRIES);
            } catch (final InterruptedException e) {
                e.getMessage();
            }
        }

    }

    private void generateCar() {
        if (this.getAllCars().size() < Simulation.MAXRAMPSIZE) {
            final Car c = new Car(this.getRampID(), this.useCarIndex(),
                    Simulation.getCurrentTime());
            this.getAllCars().add(c);
        }
    }

    public List<Car> getAllCars() {
        return this.allCars;
    }

    public String getRampID() {
        return this.rampID;
    }

    public void setRampID(String rampID) {
        this.rampID = rampID;
    }

    public boolean isRampOn() {
        return this.rampOn;
    }

    public void setRampOn(boolean rampOn) {
        this.rampOn = rampOn;
    }

    public void run() {
        while (Simulation.getTimer().getTimeLeft() >= 0) {
            this.generateCar();
            if (this.isRampOn()) {
                this.enterHighWay();
            }
        }

    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    @Override
    public String toString() {
        return "\tRAMP:" + this.getRampID();
    }

    public int useCarIndex() {
        return this.carIndex++;
    }
}