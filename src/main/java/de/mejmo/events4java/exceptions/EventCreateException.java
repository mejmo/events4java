package de.mejmo.events4java.exceptions;

/**
 * @author: Martin Formanko 2015
 */
public class EventCreateException extends Exception {
    public EventCreateException() {
    }

    public EventCreateException(String s) {
        super(s);
    }

    public EventCreateException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EventCreateException(Throwable throwable) {
        super(throwable);
    }

    public EventCreateException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
