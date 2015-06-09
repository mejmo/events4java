package events;

import de.mejmo.events4java.events.AllDayEvent;
import de.mejmo.events4java.events.EventDateInfo;
import de.mejmo.events4java.events.EventMailInfo;
import de.mejmo.events4java.events.SMTPEventData;
import de.mejmo.events4java.exceptions.CalendarCreateException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by MFO on 09.06.2015.
 */
public class EventData {

    @Test
    public void testCreateCalendar() throws CalendarCreateException {

        EventMailInfo mailInfo = new EventMailInfo("m.formanko@ots-ag.de", "SUBJECT", "EMAILBODY", "FILENAME.ics");
        EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
        SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);

        String test = null;
        test = new String(eventData.getCalendarBytes());

        assertTrue(test.contains("VALUE=DATE:20151224"));
        assertTrue(test.contains("SUMMARY:Christmas"));

    }

}
