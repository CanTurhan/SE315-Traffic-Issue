package traficsim.event;

import traficsim.Ramp;
import traficsim.Simulation;
import traficsim.monitorobj.SynchronizedList;
import traficsim.observer.Event;
import traficsim.observer.EventGenerator;
import traficsim.observer.EventPublisher;
import traficsim.observer.EventSubscriber;
import traficsim.reactor.RampEventHandler;
import traficsim.reactor.RampOffEventHandler;
import traficsim.reactor.RampOnEventHandler;
import traficsim.reactor.Reactor;


//This Class is a Event Generator. Implemented from 3 interfaces
public class MeteringEventGenerator implements EventGenerator, EventPublisher,
		Runnable {

	private SynchronizedList rampList;

	private static MeteringEventGenerator me;

	private final static Reactor reactor = new Reactor();

	public static MeteringEventGenerator getInstance() {
		if (me == null) {
			me = new MeteringEventGenerator();
			final RampEventHandler onHandler = new RampOnEventHandler();
			final RampEventHandler offHandler = new RampOffEventHandler();
			reactor.registerHandler(onHandler.getHandle(), true);
			reactor.registerHandler(offHandler.getHandle(), false);
		}
		return me;
	}

	public void generate() {
		// generate events to allert/wake up
		// Reactor handlers
		// dynamically generate event type and the ramp it occurs on
		Event event;
		int randomRampIdx = (int) (Math.random() * rampList.size());
		double rampToggle = (Math.random() * 100);
		Ramp ramp = (Ramp) rampList.getItem(new Integer(randomRampIdx));
		if (rampToggle <= 50)
			event = new RampOffEvent(ramp);
		else
			event = new RampOnEvent(ramp);
		//wake-up reactor
		broadcast(event);
	}

	public void run() {
		while (Simulation.getTimer().getTimeLeft() >= 0)
			generate();
	}

	public synchronized void setRampMap(SynchronizedList list) {
		rampList = list;
	}

	public void addListener(EventSubscriber l) {
		// do nothing since we have only one listener - REACTOR
	}

	public void removeListener(EventSubscriber l) {
		// do nothing since we have only one listener - REACTOR

	}

	public void broadcast(Event e) {
		reactor.messageReceived(e);
	}

	private MeteringEventGenerator() {
	} // hide the constructor

	public Thread createThread() {
		return new Thread(this);
	}

}
