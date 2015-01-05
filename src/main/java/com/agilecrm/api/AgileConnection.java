package com.agilecrm.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <code>AgileConnection</code> Create connection with Agile CRM
 *
 * @author Tejaswi
 * @see APIManager
 * @since March 2013
 */
public class AgileConnection {

    private static final Logger logger = LoggerFactory.getLogger(AgileConnection.class);

    String baseUrl;

    String userName;

    String apiKey;

    APIManager apiManager;

    /**
     * Initialize API connection settings from properties file
     *
     * @param propertiesInputStream
     * @throws Exception
     */
    protected void initialize(InputStream propertiesInputStream) throws Exception {
        if (null == propertiesInputStream) {
            throw new Exception("propertiesInputStream is required");
        }
        Properties properties = new Properties();
        properties.load(propertiesInputStream);
        baseUrl = properties.getProperty("agilecrm.baseUrl");
        userName = properties.getProperty("agilecrm.userName");
        apiKey = properties.getProperty("agilecrm.apikey");
    }

    /**
     * Creates a connection to the Agile CRM and configures the resources in
     * {@link APIManager} class default property file <code>agile-connection.properties</code>
     *
     * @return
     * @throws Exception
     */
    public APIManager getConnection() throws Exception {
        String filename = "agile-connection.properties";
        InputStream propertiesInputStream = AgileConnection.class.getClassLoader().getResourceAsStream(filename);
        if (null == propertiesInputStream) {
            throw new IOException("unable to read default connection properties: " + filename);
        }
        return getConnection(propertiesInputStream);
    }

    /**
     * Creates a connection to the Agile CRM and configures the resources in
     * {@link APIManager} class from <code>filename</code>
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public APIManager getConnection(String filename) throws Exception {
        return getConnection(new FileInputStream(filename));
    }

    /**
     * Creates a connection to the Agile CRM and configures the resources in
     * {@link APIManager} class from <code>propertiesInputStream</code>
     *
     * @param propertiesInputStream
     * @return Instance of {@link APIManager}
     * @throws Exception
     */
    public APIManager getConnection(InputStream propertiesInputStream) throws Exception {
        // Configures a resource with AGILE CRM credentials
        try {
            if (null == apiManager) {
                initialize(propertiesInputStream);
                APIManager apiManager = new APIManager(baseUrl, userName, apiKey);
                return apiManager;
            }
            return apiManager;

        } catch (Exception e) {
            logger.error("failed to initialize connection", e);
            throw e;
        }
    }

}
