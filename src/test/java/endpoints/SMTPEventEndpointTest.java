package endpoints;

import de.mejmo.events4java.config.E4JConfiguration;
import de.mejmo.events4java.endpoints.SMTPEventEndpoint;
import de.mejmo.events4java.events.AllDayEvent;
import de.mejmo.events4java.events.EventDateInfo;
import de.mejmo.events4java.events.EventMailInfo;
import de.mejmo.events4java.events.SMTPEventData;
import de.mejmo.events4java.exceptions.EventCreateException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
* Created by MFO on 09.06.2015.
*/
public class SMTPEventEndpointTest {

    @BeforeClass
    public static void setConfig() {
        E4JConfiguration.setFilename("/events4java002.properties");
    }

    @Test
    public void testCreateCalendar() throws EventCreateException {

        EventMailInfo mailInfo = new EventMailInfo("m.formanko@ots-ag.de", "SUBJECT", "EMAILBODY", "FILENAME.ics");
        EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
        SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);
        SMTPEventEndpoint endpoint = new SMTPEventEndpoint();

        endpoint.dispatchEvent(eventData);

    }

}
