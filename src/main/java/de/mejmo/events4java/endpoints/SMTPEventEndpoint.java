package de.mejmo.events4java.endpoints;

import de.mejmo.events4java.config.E4JConfiguration;
import de.mejmo.events4java.events.*;
import de.mejmo.events4java.exceptions.CalendarCreateException;
import de.mejmo.events4java.exceptions.EventCreateException;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Properties;

/**
 * SMTP Endpoint class sends ICS file to the destination email. Uses iCal4j to generate ICS string.
 * Supports SSL/TLS encryption and authentication as well.
 *
 * Author: Martin Formanko 2015
 */
public class SMTPEventEndpoint implements EventEndpoint {



    public enum EncryptionType {
        SSL, TLS, none
    }
    private Session session;

    /**
     * Generates ICS data in memory and sends them to remote SMTP server
     * @param data
     * @throws EventCreateException
     */
    @Override
    public void dispatchEvent(EventData data) throws EventCreateException {

        Message message = new MimeMessage(getSession());

        try {
            byte[] ics = ((SMTPEventData)data).getCalendarBytes();

            EventMailInfo mailInfo = (EventMailInfo)((SMTPEventData) data).getMailInfo();

            message.setFrom(new InternetAddress(E4JConfiguration.getString("SMTPFromAddress")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailInfo.getDestinationEmail()));
            message.setSubject(mailInfo.getSubject());

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailInfo.getEmailBody());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(ics, "text/calendar")));
            messageBodyPart.setFileName(mailInfo.getFilename());

            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

        } catch (MessagingException | CalendarCreateException e) {
            throw new EventCreateException("MSG_EXC: Exception while creating email contents", e);
        }

        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new EventCreateException("MSG_EXC: Exception occured while sending email message", e);
        }

    }

    /**
     * Sets SSL, TLS, authentication if required and return Java mail session
     * @return Javamail session
     * @throws EventCreateException
     */
    private Session getSession() throws EventCreateException {

        if (this.session != null)
            return session;

        EncryptionType enc = EncryptionType.valueOf(E4JConfiguration.getString("SMTPEncryption"));

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", E4JConfiguration.getString("SMTPHostname"));
        props.put("mail.smtp.port", String.valueOf(E4JConfiguration.getInt("SMTPPort")));

        Session session = null;

        if (enc == EncryptionType.TLS) {
            props.put("mail.smtp.starttls.enable", "true");

            if (E4JConfiguration.getBoolean("SMTPAuthenticationNeeded")) {
                props.put("mail.smtp.auth", "true");
                session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(
                                        E4JConfiguration.getString("SMTPUsername"),
                                        E4JConfiguration.getString("SMTPPassword"));
                            }
                        });
            }
        } else if (enc == EncryptionType.SSL) {
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", E4JConfiguration.getString("SMTPPort"));
        }

        if (E4JConfiguration.getBoolean("SMTPAuthenticationNeeded")) {
            props.put("mail.smtp.auth", "true");

            session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    E4JConfiguration.getString("SMTPUsername"),
                                    E4JConfiguration.getString("SMTPPassword"));
                        }
                    });
        } else {
            session = Session.getInstance(props);
        }

        session.setDebug(E4JConfiguration.getBoolean("SMTPDebugEnabled"));

        return session;

    }


}
