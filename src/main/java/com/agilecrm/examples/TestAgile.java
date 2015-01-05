package com.agilecrm.examples;

import com.agilecrm.api.APIManager;
import com.agilecrm.api.AgileConnection;
import com.agilecrm.api.ContactAPI;
import com.agilecrm.stubs.Contact;

import java.util.List;

public class TestAgile {

    public static void main(String[] args) {
        try {
            AgileConnection agileConnection = new AgileConnection();

            // Create a connection to Agile CRM
            APIManager apiManager = agileConnection.getConnection();

            // Get the Contact API with configured resource
            ContactAPI contactApi = apiManager.getContactAPI();

            // --------------------- Get contacts -----------------------------
            List<Contact> contacts = contactApi.getContacts();
            System.out.println("All contacts.. " + contacts);
        } catch (Exception e) {
            System.out.println("Exception message.. " + e.getMessage());
            e.printStackTrace();
        }
    }
}
