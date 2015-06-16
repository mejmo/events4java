package de.mejmo.events4java.endpoints;

import de.mejmo.events4java.events.EventData;
import de.mejmo.events4java.events.EventEndpoint;

/**
 * Asyncrhonous call
 * @author: Martin Formanko
 */
public class AsyncRunner extends Thread {

    private AsyncHandler handler;
    private EventEndpoint endpoint;

    public AsyncRunner(EventEndpoint endpoint, AsyncHandler asyncHandler) {
        this.handler = asyncHandler;
        this.endpoint = endpoint;
    }

    public void run() {
        try {
            endpoint.dispatchEvent();
            handler.done(endpoint);
        } catch (Throwable t) {
            handler.error(endpoint, t);
        }
    }

}
