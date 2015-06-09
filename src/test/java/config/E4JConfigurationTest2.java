package config;

import de.mejmo.events4java.config.E4JConfiguration;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
* Created by MFO on 09.06.2015.
*/
public class E4JConfigurationTest2 {

    @Test
    public void testLoadFromProperties() {
        Properties props = new Properties();
        props.put("SMTPHostname", "test");
        E4JConfiguration.setPropeties(props);
        assertEquals("test", E4JConfiguration.getString("SMTPHostname"));
    }

}
