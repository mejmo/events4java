package de.mejmo.events4java.events;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Event without start or endtime specified
 *
 * @author: Martin Formanko 2015
 */
public class AllDayEvent extends EventDateInfo {

    private DateTime date;

    public AllDayEvent(String description, int day, int month, int year) {
        super(description);
        this.date = new DateTime(year, month, day, 1, 1);
        this.setType(EventType.AllDay);
    }

    public AllDayEvent(String description, Date date) {
        super(description);
        this.setType(EventType.AllDay);
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }


}
