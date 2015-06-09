package de.mejmo.events4java.events;

import de.mejmo.events4java.exceptions.EventCreateException;

/**
 * Author: Martin Formanko 2015
 */
public interface EventEndpoint {

    public void dispatchEvent(EventData data) throws EventCreateException;

}
