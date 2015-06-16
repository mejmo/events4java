package endpoints;

import de.mejmo.events4java.endpoints.SMTPEventEndpoint;
import de.mejmo.events4java.events.EventData;

import javax.mail.Message;

public class SMTPEventEndpointStub extends SMTPEventEndpoint {
    public Message message;
    public SMTPEventEndpointStub(EventData data) {
        super(data);
    }
    public void sendMessage(Message message) {
        this.message = message;
    }
}