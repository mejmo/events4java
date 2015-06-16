package de.mejmo.events4java.events;

import de.mejmo.events4java.endpoints.AsyncHandler;
import de.mejmo.events4java.exceptions.EventCreateException;

/**
 * @author: Martin Formanko 2015
 */
public abstract class EventEndpoint {

    private EventData data;

    public EventEndpoint(EventData data) {
        this.data = data;
    }

    public abstract void dispatchEvent() throws EventCreateException;

    protected EventData getData() {
        return data;
    }

}
