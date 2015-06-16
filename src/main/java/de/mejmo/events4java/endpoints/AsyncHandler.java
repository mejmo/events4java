package de.mejmo.events4java.endpoints;

import de.mejmo.events4java.events.EventEndpoint;

/**
 * Created by MFO on 12.06.2015.
 */
public abstract class AsyncHandler {

    public abstract void done(EventEndpoint e);
    public abstract void error(EventEndpoint e, Throwable t);

}
