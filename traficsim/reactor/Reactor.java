package traficsim.reactor;


import traficsim.Ramp;
import traficsim.event.RampOffEvent;
import traficsim.event.RampOnEvent;
import traficsim.observer.Event;
import traficsim.observer.EventSubscriber;

public class Reactor implements EventSubscriber {

	private RampEventHandler rampOffHandler;

	private RampEventHandler rampOnHandler;

	public Reactor() {

	}

	public void registerHandler(RampEventHandler handler, boolean rampOn) {
		if (rampOn) {
			this.rampOnHandler = handler;

		} else {
			this.rampOffHandler = handler;

		}
	}

	public void removeHandler(RampEventHandler handler, boolean rampOn) {
		if (rampOn) {
			this.rampOnHandler = null;

		} else {
			this.rampOffHandler = null;

		}
	}

	public void messageReceived(Event e) {

		handleEvents(e);

	}

	public synchronized void handleEvents(Event e) {
		if (e instanceof RampOnEvent) {
			RampOnEvent event = (RampOnEvent) e;
			Ramp r = event.getRamp();
			this.rampOnHandler.handleEvent(r);
		} else if (e instanceof RampOffEvent) {
			RampOffEvent event = (RampOffEvent) e;
			Ramp r = event.getRamp();
			this.rampOffHandler.handleEvent(r);
		}
	}
}