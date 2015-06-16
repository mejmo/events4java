package de.mejmo.events4java.events;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Holds start and end date of the event.
 *
 * @author: Martin Formanko 2015
 */
public class TimeSpecificEvent extends EventDateInfo {

    private DateTime start;
    private DateTime end;

    public TimeSpecificEvent(String description, DateTime start, DateTime end) {
        super(description);
        this.start = start;
        this.end = end;
        this.setType(EventType.TimeSpecific);
    }

    public TimeSpecificEvent(String description, Date start, Date end) {
        super(description);
        this.start = new DateTime(start);
        this.end = new DateTime(end);
        this.setType(EventType.TimeSpecific);
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }
}
