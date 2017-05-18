package traficsim.reactor;

import traficsim.Ramp;

public interface RampEventHandler {
	public RampEventHandler getHandle();

	public void handleEvent(Ramp ramp);
}