package config;

import de.mejmo.events4java.config.E4JConfiguration;
import de.mejmo.events4java.exceptions.E4JConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;

public class E4JConfigurationTest {

    @BeforeClass
    public static void setConfig() {
        E4JConfiguration.setFilename("/events4java001.properties");
    }

    @Test
    public void testGetProperty() {
        String result = E4JConfiguration.getString("ExchangeUsername");
        assertEquals("testusername", result);
    }

    @Test
    public void testGetURI() {
        URI z = null;
        try {
            z = new URI("http://test");
        } catch (Throwable t) {}
        assertEquals(z.toASCIIString(), E4JConfiguration.getURI("ExchangeURL").toASCIIString());
    }

    @Test
    public void testGetIntProperty() {
        int result = E4JConfiguration.getInt("TestInt");
        assertEquals(30, result);
    }

    @Test(expected = E4JConfigurationException.class)
    public void testGetInt() {
        E4JConfiguration.getInt("ExchangeURL");
    }

    @Test(expected = E4JConfigurationException.class)
    public void testKeyNotFound() {
        E4JConfiguration.getString("ExchangeURL2");
    }

    @Test(expected = E4JConfigurationException.class)
    public void testKeyNotFoundInt() {
        E4JConfiguration.getInt("ExchangeURL2");
    }

}
