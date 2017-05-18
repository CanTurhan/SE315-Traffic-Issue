package traficsim.reactor;

import traficsim.Ramp;

public class RampOnEventHandler implements RampEventHandler {

	public RampEventHandler getHandle() {
		return this;
	}

	public synchronized void handleEvent(Ramp ramp) {

		ramp.setRampOn(true);
	}

}