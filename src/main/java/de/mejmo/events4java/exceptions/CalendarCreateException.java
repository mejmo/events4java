package de.mejmo.events4java.exceptions;

/**
 * @author: Martin Formanko 2015
 */
public class CalendarCreateException extends Exception {
    public CalendarCreateException() {
    }

    public CalendarCreateException(String s) {
        super(s);
    }

    public CalendarCreateException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CalendarCreateException(Throwable throwable) {
        super(throwable);
    }

    public CalendarCreateException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
