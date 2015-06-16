package endpoints;

import de.mejmo.events4java.config.E4JConfiguration;
import de.mejmo.events4java.events.*;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.internet.MimeUtility;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class SMTPEventEndpointTest {

    @BeforeClass
    public static void setConfig() {
        E4JConfiguration.setFilename("/events4java002.properties");
    }

    @Test
    public void testCreateSpecific() throws Throwable {

        EventMailInfo mailInfo = new EventMailInfo("test@test", "SUBJECT", "EMAILBODY", "FILENAME.ics");
        EventDateInfo dateInfo = new TimeSpecificEvent("Christmas", new DateTime(2015, 12, 24, 10, 0), new DateTime(2015, 12, 24, 10, 30));
        SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);
        SMTPEventEndpointStub stub = new SMTPEventEndpointStub(eventData);
        stub.dispatchEvent();

        Message message = stub.message;

        assertEquals(MimeUtility.decodeText(message.getAllRecipients()[0].toString()), "test@test");
        assertEquals(message.getSubject(), "SUBJECT");

        StringReader sin = new StringReader(MailUtils.getAttachment(message));
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(sin);

        assertEquals(calendar.getComponents().getComponent("VEVENT").getProperty("DTSTART").toString().trim(),
                "DTSTART:20151224T100000");
        assertEquals(calendar.getComponents().getComponent("VEVENT").getProperty("DTEND").toString().trim(),
                "DTEND:20151224T103000");
        assertEquals(calendar.getComponents().getComponent("VEVENT").getProperty("SUMMARY").toString().trim(),
                "SUMMARY:Christmas");

    }

    @Test
    public void testCreateAllDay() throws Throwable {

        EventMailInfo mailInfo = new EventMailInfo("test@test", "SUBJECT", "EMAILBODY", "FILENAME.ics");
        EventDateInfo dateInfo = new AllDayEvent("Christmas", 24, 12, 2015);
        SMTPEventData eventData = new SMTPEventData(mailInfo, dateInfo);
        SMTPEventEndpointStub stub = new SMTPEventEndpointStub(eventData);
        stub.dispatchEvent();

        Message message = stub.message;

        assertEquals(MimeUtility.decodeText(message.getAllRecipients()[0].toString()), "test@test");
        assertEquals(message.getSubject(), "SUBJECT");

        StringReader sin = new StringReader(MailUtils.getAttachment(message));
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(sin);

        assertEquals(calendar.getComponents().getComponent("VEVENT").getProperty("DTSTART").toString().trim(),
                "DTSTART;VALUE=DATE:20151224");
        assertEquals(calendar.getComponents().getComponent("VEVENT").getProperty("SUMMARY").toString().trim(),
                "SUMMARY:Christmas");

    }


}
