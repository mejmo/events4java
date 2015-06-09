package de.mejmo.events4java.events;

/**
 * Author: Martin Formanko 2015
 */
public abstract class EventDateInfo {

    private EventType type;
    private String description;

    public EventDateInfo(String description) {
        this.description = description;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum EventType {
        AllDay, TimeSpecific
    }

}
