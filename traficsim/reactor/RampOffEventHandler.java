package traficsim.reactor;

import traficsim.Ramp;

public class RampOffEventHandler implements RampEventHandler {

	public RampEventHandler getHandle() {
		return this;
	}

	public synchronized void handleEvent(Ramp ramp) {

		ramp.setRampOn(false);
	}

}