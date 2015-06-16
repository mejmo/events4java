package de.mejmo.events4java.exceptions;

/**
 * @author: Martin Formanko 2015
 */
public class E4JConfigurationException extends RuntimeException {
    public E4JConfigurationException() { super(); }
    public E4JConfigurationException(String s) { super(s); }
    public E4JConfigurationException(String s, Throwable throwable) { super(s, throwable); }
    public E4JConfigurationException(Throwable throwable) { super(throwable); }
}
