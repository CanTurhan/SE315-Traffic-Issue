package traficsim.event;


import traficsim.Ramp;
import traficsim.observer.Event;

//Its a sub event class with 3 mutator functions
public class RampOffEvent implements Event {
private Ramp ramp;

public RampOffEvent(Ramp ramp) {
	this.ramp = ramp;
}

public synchronized Ramp getRamp() {
	return ramp;
}

public synchronized void setRamp(Ramp ramp) {
	this.ramp = ramp;
}
}
