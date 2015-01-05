package com.agilecrm.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AgileConnectionTest {

    @Mock
    private APIManager apiManager;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getConnection_with_filename_not_found() {
        AgileConnection agileConnection = new AgileConnection();

        APIManager apiManager = null;
        try {
            agileConnection.getConnection("no-such-filename");
            fail("expect exception");

        } catch (Exception e) {
            assertEquals("no-such-filename (No such file or directory)", e.getLocalizedMessage());
            assertNull(apiManager);
            assertNull(agileConnection.baseUrl);
            assertNull(agileConnection.userName);
            assertNull(agileConnection.apiKey);
        }
    }

    @Test
    public void getConnection_missing_property_baseUrl() {
        AgileConnection agileConnection = new AgileConnection();

        APIManager apiManager = null;
        try {
            String properties = "agilecrm.userName = AgileCRM username\n" +
                    "agilecrm.apikey = AgileCRM apikey";
            InputStream propertiesInputStream = new ByteArrayInputStream(properties.getBytes(StandardCharsets.UTF_8));
            agileConnection.getConnection(propertiesInputStream);
            fail("expect exception");

        } catch (Exception e) {
            assertEquals("Agile plugin preferences null", e.getLocalizedMessage());
            assertNull(apiManager);
            assertNull(agileConnection.baseUrl);
            assertNotNull(agileConnection.userName);
            assertNotNull(agileConnection.apiKey);
        }
    }

    @Test
    public void getConnection_missing_property_userName() {
        AgileConnection agileConnection = new AgileConnection();

        APIManager apiManager = null;
        try {
            String properties = "agilecrm.baseUrl = https://{domain}.agilecrm.com/dev/\n" +
                    "agilecrm.apikey = AgileCRM apikey";
            InputStream propertiesInputStream = new ByteArrayInputStream(properties.getBytes(StandardCharsets.UTF_8));
            agileConnection.getConnection(propertiesInputStream);
            fail("expect exception");

        } catch (Exception e) {
            assertEquals("Agile plugin preferences null", e.getLocalizedMessage());
            assertNull(apiManager);
            assertNotNull(agileConnection.baseUrl);
            assertNull(agileConnection.userName);
            assertNotNull(agileConnection.apiKey);
        }
    }

    @Test
    public void getConnection_missing_property_apikey() {
        AgileConnection agileConnection = new AgileConnection();

        APIManager apiManager = null;
        try {
            String properties = "agilecrm.baseUrl = https://{domain}.agilecrm.com/dev/\n" +
                    "agilecrm.userName = AgileCRM username";
            InputStream propertiesInputStream = new ByteArrayInputStream(properties.getBytes(StandardCharsets.UTF_8));
            agileConnection.getConnection(propertiesInputStream);
            fail("expect exception");

        } catch (Exception e) {
            assertEquals("Agile plugin preferences null", e.getLocalizedMessage());
            assertNull(apiManager);
            assertNotNull(agileConnection.baseUrl);
            assertNotNull(agileConnection.userName);
            assertNull(agileConnection.apiKey);
        }
    }

    @Test
    public void initialize() {
        AgileConnection agileConnection = new AgileConnection();

        try {
            InputStream inputStream = AgileConnectionTest.class.getClassLoader().getResourceAsStream("agile-connection-test.properties");
            assertNotNull(inputStream);
            agileConnection.initialize(inputStream);
            assertEquals("https://{domain}.agilecrm.com/dev/", agileConnection.baseUrl);
            assertEquals("AgileCRM username", agileConnection.userName);
            assertEquals("AgileCRM apikey", agileConnection.apiKey);

        } catch (Exception e) {
            fail("unexpected exception: " + e.getLocalizedMessage());
        }
    }

    @Test
    public void getConnection() {
        AgileConnection agileConnection = new AgileConnection();

        APIManager apiManager = null;

        try {
            agileConnection.apiManager = this.apiManager;
            APIManager localApiManager = agileConnection.getConnection("src/test/resources/agile-connection-test.properties");
            assertEquals(this.apiManager, localApiManager);

        } catch (Exception e) {
            fail("unexpected exception: " + e.getLocalizedMessage());
        }
    }

}
