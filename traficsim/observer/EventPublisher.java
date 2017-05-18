package traficsim.observer;



public interface  EventPublisher {

abstract void addListener (EventSubscriber l);
abstract void removeListener (EventSubscriber l);
abstract void broadcast (Event e);

}