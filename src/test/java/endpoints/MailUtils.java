package endpoints;

import org.apache.commons.io.IOUtils;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

/**
 * Created by MFO on 16.06.2015.
 */
public class MailUtils {

    public static String getAttachment(Message message) throws MessagingException, IOException {

        Multipart multipart = (Multipart)message.getContent();

        for (int j = 0; j < multipart.getCount(); j++) {
            BodyPart bodyPart = multipart.getBodyPart(j);
            if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                if (bodyPart.getContent().getClass().equals(MimeMultipart.class)) {
                    MimeMultipart mimemultipart = (MimeMultipart) bodyPart.getContent();
                    for (int k = 0; k < mimemultipart.getCount(); k++) {
                        if (mimemultipart.getBodyPart(k).getFileName() != null && mimemultipart.getBodyPart(k).getFileName() == "FILENAME.ics") {
                            return IOUtils.toString(mimemultipart.getBodyPart(k).getInputStream());
                        }
                    }
                }
                continue;
            }
            if (bodyPart.getFileName().equalsIgnoreCase("FILENAME.ics"))
                return IOUtils.toString(bodyPart.getInputStream());
        }

        return "";

    }


}
