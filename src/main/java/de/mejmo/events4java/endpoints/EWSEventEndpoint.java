package de.mejmo.events4java.endpoints;

import de.mejmo.events4java.config.E4JConfiguration;
import de.mejmo.events4java.events.*;
import de.mejmo.events4java.exceptions.E4JConfigurationException;
import de.mejmo.events4java.exceptions.EventCreateException;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ConnectingIdType;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.misc.TraceFlags;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.SendInvitationsMode;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.misc.ImpersonatedUserId;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

import java.net.URI;
import java.util.EnumSet;

/**
 * EWS Endpoint using EWS API in order to add calendar entry for impersonated user.
 *
 * @author: Martin Formanko 2015
*/
public class EWSEventEndpoint extends EventEndpoint {

    private ExchangeService service;

    /**
     * Main entry, parses data from parameter and inserts calendar entry into remote user's calendar. Using impersonation.
     * @throws EventCreateException
     */
    @Override
    public void dispatchEvent() throws EventCreateException {

        ExchangeService service = getService();
        Appointment appointment;
        ExchangeEventData ewsData = (ExchangeEventData)getData();

        try {
            appointment = new Appointment(getService());

            if (getData().getDateInfo().getType() == EventDateInfo.EventType.AllDay) {
                AllDayEvent e = (AllDayEvent)getData().getDateInfo();
                appointment.setIsAllDayEvent(true);
                appointment.setStart(e.getDate().toDate());
            }

            if (getData().getDateInfo().getType() == EventDateInfo.EventType.TimeSpecific) {
                TimeSpecificEvent e = (TimeSpecificEvent)getData().getDateInfo();
                appointment.setStart(e.getStart().toDate());
                appointment.setEnd(e.getEnd().toDate());
            }

            this.getService().setImpersonatedUserId(new ImpersonatedUserId(ConnectingIdType.SmtpAddress, ewsData.getImpersonatedEmail()));
            Folder destFolder = getCalendarFolder(E4JConfiguration.getString("ExchangeCalendarName"), ewsData.getImpersonatedEmail());
            appointment.save(destFolder.getId(), SendInvitationsMode.valueOf("ExchangeInvitationsMode"));

        } catch (Exception e) {
            throw new EventCreateException("EXC: Could not create Exchange appointment", e);
        }

    }

    /**
     * Gets folder (which is actually calendar) of the impersonated user specified with email address.
     * @param name
     * @param destEmail
     * @return
     * @throws EventCreateException
     */
    private Folder getCalendarFolder(String name, String destEmail) throws EventCreateException {

        getService().setImpersonatedUserId(new ImpersonatedUserId(ConnectingIdType.SmtpAddress, destEmail));

        FolderView view = new FolderView(1);
        view.setPropertySet(new PropertySet(BasePropertySet.IdOnly));
        SearchFilter filter = new SearchFilter.IsEqualTo(FolderSchema.DisplayName, name);

        try {
            FindFoldersResults findResults = getService().findFolders(WellKnownFolderName.Root, filter, view);

            if (findResults.getTotalCount() != 1) {
                throw new EventCreateException("EVNT_EXC: Cannot open calendar folder of remote user");
            }

            return Folder.bind(getService(), findResults.getFolders().get(0).getId());
        } catch (Exception e) {
            throw new EventCreateException("EXC: Could not bind to folder list of remote user");
        }

    }

    private ExchangeService getService() {
        if (service == null ) {
            ExchangeVersion version = null;
            try {
                version = ExchangeVersion.valueOf(E4JConfiguration.getString("ExchangeVersion"));
            } catch (Throwable t) {
                throw new E4JConfigurationException("E4J_CONF_EXC: Unknown or missing Exchange Version in properties file");
            }
            String username = E4JConfiguration.getString("ExchangeUsername");
            String password = E4JConfiguration.getString("ExchangePassword");
            URI url = E4JConfiguration.getURI("ExchangeURL");
            boolean isTrace = E4JConfiguration.getBoolean("ExchangeTraceEnabled");

            service = setupService(username, password, version, url, isTrace);
            return service;
        } else {
            return service;
        }
    }

    private ExchangeService setupService(String username, String password, ExchangeVersion version, URI url, boolean trace) {

        ExchangeService service = new ExchangeService();
        ExchangeCredentials credentials = new WebCredentials(username, password);
        service.setCredentials(credentials);
        service.setUrl(url);
        service.setTraceFlags(EnumSet.of(TraceFlags.EwsRequest, TraceFlags.EwsResponse));
        service.setTraceEnabled(trace);
        return service;

    }

    public EWSEventEndpoint(EventData data) {
        super(data);
    }



}
