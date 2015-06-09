package de.mejmo.events4java.events;

/**
 * Author: Martin Formanko 2015
 */
public class ExchangeEventData extends EventData {

    private String impersonatedEmail;

    public ExchangeEventData(EventDateInfo date) {
        super(date);
    }

    public String getImpersonatedEmail() {
        return impersonatedEmail;
    }

    public void setImpersonatedEmail(String impersonatedEmail) {
        this.impersonatedEmail = impersonatedEmail;
    }
}
