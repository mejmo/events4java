package de.mejmo.events4java.config;

import de.mejmo.events4java.exceptions.E4JConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Simple class (than commons-config) for parsing properties file, Settings can be loaded with
 * file or manually with properties object.
 *
 * @author: Martin Formanko 2015
 */
public class E4JConfiguration {

    private static Properties props;
    private static String filename = "/events4java.properties";

    /**
     * Get integer value from properties
     * @param key
     * @return
     */
    public static int getInt(String key) {

        if (!getProperties().containsKey(key))
            throw new E4JConfigurationException("Key "+key+" in properties file not found");

        try {
            return Integer.parseInt(getProperties().getProperty(key));
        } catch (Throwable t) {
            throw new E4JConfigurationException("Key "+key+" not integer");
        }

    }

    /**
     * Get String value from properties
     * @param key
     * @return
     */
    public static String getString(String key) {

        if (!getProperties().containsKey(key))
            throw new E4JConfigurationException("Key "+key+" in properties file not found");

        return getProperties().getProperty(key);

    }

    /**
     * Manually set properties without parsing any file
     * @param p
     */
    public static void setPropeties(Properties p) {
        props = p;
    }

    /**
     * Parse file if properties are null
     * @return
     */
    public static Properties getProperties() {
        if (props == null) {
            Properties props = new Properties();
            InputStream in = E4JConfiguration.class.getResourceAsStream(filename);
            if (in == null) {
                throw new RuntimeException("Cannot find events4java.properties in classpath");
            }
            try {
                props.load(in);
                in.close();
            } catch (IOException e) {
                throw new RuntimeException("Cannot read from properties file");
            }
            props = props;
            return props;
        } else {
            return props;
        }
    }

    /**
     * Delete properties to refresh new one
     * @param f
     */
    public static void setFilename(String f) {
        props = null;
        filename = f;
    }

    /**
     * URI for Exchange URL
     * @param key
     * @return
     */
    public static URI getURI(String key) {

        if (!getProperties().containsKey(key))
            throw new E4JConfigurationException("Key "+key+" in properties file not found");

        try {
            return new URI(getProperties().getProperty(key));
        } catch (URISyntaxException e) {
            throw new E4JConfigurationException("URL not correctly formatted: "+key);
        }

    }

    /**
     * Boolean
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {

        if (!getProperties().containsKey(key))
            throw new E4JConfigurationException("Key "+key+" in properties file not found");

        if (!getProperties().getProperty(key).equalsIgnoreCase("true") && !getProperties().getProperty(key).equalsIgnoreCase("false"))
            throw new E4JConfigurationException("True/false value for key "+key+" in properties file needed");

        return Boolean.parseBoolean(getProperties().getProperty(key));

    }


}
