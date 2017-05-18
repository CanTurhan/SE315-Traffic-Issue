package traficsim.event;


import traficsim.Ramp;
import traficsim.observer.Event;

public class RampOnEvent implements Event {

	private Ramp ramp;

	public RampOnEvent(Ramp ramp) {
		this.ramp = ramp;
	}

	public synchronized Ramp getRamp() {
		return ramp;
	}

	public synchronized void setRamp(Ramp ramp) {
		this.ramp = ramp;
	}
}