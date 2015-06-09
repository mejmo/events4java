package de.mejmo.events4java.events;


/**
 * Author: Martin Formanko 2015
 */
public abstract class EventData {

    private EventDateInfo date;

    public EventData(EventDateInfo date) {
        this.date = date;
    }

    public EventDateInfo getDateInfo() {
        return date;
    }

    public void setDateInfo(EventDateInfo date) {
        this.date = date;
    }

}
