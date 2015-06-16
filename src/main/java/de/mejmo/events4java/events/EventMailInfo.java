package de.mejmo.events4java.events;

/**
 * Basic class holding information about SMTP mail being sent with ICS attachment
 *
 * @author: Martin Formanko 2015
 */
public class EventMailInfo {

    private String subject;
    private String emailBody;
    private String filename;
    private String destinationEmail;

    public EventMailInfo(String destinationEmail, String subject, String emailBody, String filename) {
        this.subject = subject;
        this.emailBody = emailBody;
        this.filename = filename;
        this.destinationEmail = destinationEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }


    public String getDestinationEmail() {
        return destinationEmail;
    }

    public void setDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
