package de.mejmo.events4java.events;

import de.mejmo.events4java.exceptions.CalendarCreateException;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.StringWriter;
import java.util.Calendar;

/**
 * SMTPEventData class holds all information about mail or calendar entry.
 *
 * @author: Martin Formanko 2015
*/
public class SMTPEventData extends EventData {

    private EventMailInfo mailInfo;

    public SMTPEventData(EventMailInfo mailInfo, EventDateInfo date) {
        super(date);
        this.mailInfo = mailInfo;
    }

    public EventMailInfo getMailInfo() {
        return mailInfo;
    }

    public void setMailInfo(EventMailInfo mailInfo) {
        this.mailInfo = mailInfo;
    }

    /**
     * Creates byte array which holds ICS file with all important information.
     * @return
     * @throws de.mejmo.events4java.exceptions.EventCreateException
     */
    public byte[] getCalendarBytes() throws CalendarCreateException {

        Calendar calendar = Calendar.getInstance();
        String description = null;
        StringWriter str = new StringWriter();
        VEvent vEvent = null;

        description = getDateInfo().getDescription();

        if (getDateInfo().getType() == EventDateInfo.EventType.AllDay) {
            AllDayEvent e = (AllDayEvent)getDateInfo();
            vEvent = new VEvent(new Date(e.getDate().toDate()), description);
        }

        if (getDateInfo().getType() == EventDateInfo.EventType.TimeSpecific) {
            TimeSpecificEvent e = (TimeSpecificEvent)getDateInfo();
            vEvent = new VEvent(new DateTime(e.getStart().toDate()), new DateTime(e.getEnd().toDate()), description);
        }

        try {

            UidGenerator ug = new UidGenerator("1");
            vEvent.getProperties().add(ug.generateUid());

            net.fortuna.ical4j.model.Calendar cal = new net.fortuna.ical4j.model.Calendar();
            cal.getComponents().add(vEvent);
            cal.getProperties().add(Version.VERSION_2_0);
            cal.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
            cal.getProperties().add(CalScale.GREGORIAN);

            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(cal, str);
            byte[] res = str.toString().getBytes("UTF-8");
            return res;

        } catch (Throwable t) {
            throw new CalendarCreateException("EXC: Cannot create calendar ICS file", t);
        }

    }

}
