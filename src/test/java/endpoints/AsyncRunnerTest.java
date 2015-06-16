package endpoints;

import de.mejmo.events4java.config.E4JConfiguration;
import de.mejmo.events4java.endpoints.AsyncHandler;
import de.mejmo.events4java.endpoints.AsyncRunner;
import de.mejmo.events4java.endpoints.SMTPEventEndpoint;
import de.mejmo.events4java.events.*;
import de.mejmo.events4java.exceptions.EventCreateException;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.StringReader;

/**
 * Created by MFO on 12.06.2015.
 */
public class AsyncRunnerTest {

    @BeforeClass
    public static void setConfig() {
        E4JConfiguration.setFilename("/events4java002.properties");
    }

    @Test
    public void testAsync() throws Throwable {

        EventMailInfo mailInfo = new EventMailInfo("m.formanko@ots-ag.de", "SUBJECT", "EMAILBODY", "FILENAME.ics");
        EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
        SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);

        AsyncRunner asyncRunner = new AsyncRunner(new SMTPEventEndpointStub(eventData), new AsyncHandler() {
            @Override
            public void done(EventEndpoint e) {
            }

            @Override
            public void error(EventEndpoint e, Throwable t) {
                throw new RuntimeException(t);
            }
        });
        asyncRunner.start();

    }

}
