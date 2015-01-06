package com.agilecrm.api;

import com.agilecrm.utils.StringUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

;

/**
 * <code>APIManager</code> class contains methods to return {@link ContactAPI},
 * {@link NoteAPI} and {@link DealAPI} class instances
 *
 * @author Tejaswi
 * @see {@link NoteAPI}, {@link DealAPI}, {@link ContactAPI}
 * @since March 2013
 */
public class APIManager {

    private static final Logger logger = LoggerFactory.getLogger(APIManager.class.getName());

    /**
     * Holds a {@link WebResource} object
     */
    private WebResource resource;

    /**
     * Configures the client and initializes the resource with the given
     * parameters
     *
     * @param baseUrl  {@link String} base URL of AgileCRM
     * @param userName {@link String} user name of AgileCRM
     * @param apiKey   {@link String} api key of AgileCRM
     * @throws Exception
     */
    public APIManager(String baseUrl, String userName, String apiKey)
            throws Exception {
        if (StringUtils
                .isNullOrEmpty(new String[]{baseUrl, userName, apiKey}))
            throw new Exception("Agile plugin preferences null");

        ClientConfig config = new DefaultClientConfig();

        Client client = Client.create(config);

        // add a logger if debug is enabled
        if (logger.isDebugEnabled()) {
            client.addFilter(new LoggingFilter());
        }

        this.resource = client.resource(baseUrl);

        this.resource.addFilter(new HTTPBasicAuthFilter(userName, apiKey));

        resource.path("/api").get(String.class);
    }

    /**
     * Returns instance of {@link ContactAPI} with the configured resource
     *
     * @return {@link ContactAPI}
     */
    public ContactAPI getContactAPI() {
        return new ContactAPI(resource);
    }

    /**
     * Returns instance of {@link DealAPI} with the configured resource
     *
     * @return {@link ContactAPI}
     */
    public DealAPI getDealAPI() {
        return new DealAPI(resource);
    }

    /**
     * Returns instance of {@link NoteAPI} with the configured resource
     *
     * @return {@link NoteAPI}
     */
    public NoteAPI getNoteAPI() {
        return new NoteAPI(resource);
    }

    /**
     * Returns configured {@link WebResource}
     *
     * @return {@link WebResource}
     */
    public WebResource getResource() {
        return resource;
    }
}
