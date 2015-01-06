package com.agilecrm.examples;

import com.agilecrm.api.APIManager;
import com.agilecrm.api.AgileConnection;
import com.agilecrm.api.ContactAPI;
import com.agilecrm.stubs.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TestAgile {

    private static final Logger logger = LoggerFactory.getLogger(TestAgile.class);

    public static void main(String[] args) {
        try {
            AgileConnection agileConnection = new AgileConnection();

            // Create a connection to Agile CRM
            APIManager apiManager = agileConnection.getConnection();

            // Get the Contact API with configured resource
            ContactAPI contactApi = apiManager.getContactAPI();

            // --------------------- Get contacts -----------------------------
            List<Contact> contacts = contactApi.getContacts();

            logger.info("All contacts.. {}", contacts);

        } catch (Exception e) {
            logger.error("API Exception", e);
        }
    }
}
