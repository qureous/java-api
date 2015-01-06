package com.agilecrm.examples;

import com.agilecrm.api.APIManager;
import com.agilecrm.api.AgileConnection;
import com.agilecrm.api.ContactAPI;
import com.agilecrm.stubs.Contact;
import com.agilecrm.stubs.Contact.Type;
import com.agilecrm.stubs.ContactField.FieldName;
import com.agilecrm.stubs.Tag;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>TestContact</code> class contains main method to test all the methods
 * in <code>ContactAPI</code> class
 *
 * @author Tejaswi
 * @see ContactAPI
 * @since March 2013
 */
public class TestContact {

    private static final Logger logger = LoggerFactory.getLogger(TestContact.class);

    public static void main(String[] args) {

        try {
            // Create a connection to Agile CRM
            APIManager apiManager = new AgileConnection().getConnection();

            // Get the Contact API with configured resource
            ContactAPI contactApi = apiManager.getContactAPI();

            // List of tags to add it to contact
            List<String> tags;

            // -------------------- Adding a company --------------------------
            Contact company = new Contact();

            company.setType(Type.COMPANY);
            company.setContactField(FieldName.COMPANY, "AgileCRM");
            company.setContactField(FieldName.URL, "http://www.agilecrm.com");

            tags = new ArrayList<String>();
            tags.add("Product based");
            company.setTags(tags);

            company = contactApi.addContact(company);

            logger.info("Added company... {}", company);

            // ------------------- Adding a person ----------------------------
            Contact person1 = new Contact();

            person1.setType(Type.PERSON);
            person1.setLead_score(3);
            person1.setContactField(FieldName.FIRST_NAME, "Test");
            person1.setContactField(FieldName.LAST_NAME, "Add1");
            person1.setContactField(FieldName.ORGANIZATION, "Agile");
            person1.setContactField(FieldName.EMAIL, "test1@agilecrm.com");
            person1.setContactField(FieldName.TITLE, "Software developer");
            person1.setContactField(FieldName.PHONE, "+48624981");
            person1.setContactField(FieldName.WEBSITE,
                    "http://agile-crm-cloud.appspot.com");
            JSONObject address = new JSONObject();
            address.put("city", "new delhi");
            address.put("state", "delhi");
            address.put("country", "india");
            person1.setContactField(FieldName.ADDRESS, address.toString());

            person1.setCustomField("Test", "Test Add Custom Field");

            tags = new ArrayList<String>();
            tags.add("developer");
            person1.setTags(tags);

            person1 = contactApi.addContact(person1);

            logger.info("Added person... {}", person1);

            // -------------- Another method to add person --------------------
            Contact person2 = new Contact();

            person2 = contactApi.addContact("Test", "Add2", "AgileCRM",
                    "test2@agilecrm.com", "Quality Analyst", "+1687621786",
                    "http://www.agilecrm.com");

            logger.info("Added person... {}", person2);

            // --------------------- Get contacts -----------------------------
            List<Contact> contacts = contactApi.getContacts();

            logger.info("All contacts... {}", contacts);

            // ----------------- Get contact by contact id --------------------
            Contact contact = new Contact();

            contact = contactApi.getContact(String.valueOf(person2.getId()));

            logger.info("Got contact by id... {}", contact);

            // ---------------- Get contact from email ------------------------
            contact = contactApi.getContactFromEmail("test1@agilecrm.com");

            logger.info("Got contact by email... {}", contact);

            // -------------------- update contact ----------------------------
            contact.setContactField(FieldName.LAST_NAME, "Update1");

            contact = contactApi.updateContact(contact);

            logger.info("updated contact... {}", contact);

            // --------------- update contact by id ----------------------------
            Map<FieldName, String> contactFields = new HashMap<FieldName, String>();
            contactFields.put(FieldName.TITLE, "software engineer");
            contactFields.put(FieldName.LAST_NAME, "Update2");

            // Adding custom fields to contact -----------------
            Map<String, String> customFields = new HashMap<String, String>();
            customFields.put("Test custom field", "Test");

            contact = contactApi.updateContact(String.valueOf(person2.getId()),
                    contactFields, customFields);

            logger.info("updated contact... {}", contact);

            // ----------- Adding tags to contacts based on contact id's ------
            tags = new ArrayList<String>();
            tags.add("Professor");
            tags.add("Consultant");
            tags.add("Dealer");

            List<String> contactIds = new ArrayList<String>();
            contactIds.add(String.valueOf(person1.getId()));
            contactIds.add(String.valueOf(person2.getId()));

            contactApi.addTagsToContacts(tags, contactIds);

            logger.info("Added tags to contact ids............");

            // ------------ Adding tag to contact based on email -------------
            Tag tag = new Tag();
            tag.setTag("CEO");

            contactApi.addTagsToEmail(tag, "test2@agilecrm.com");

            logger.info("Added tag based on email..............");

            // ------------ delete contact by contact id --------------------
            contactApi.deleteContact(String.valueOf(person2.getId()));

            // ------------- delete bulk contacts ------------------------
            contactIds = new ArrayList<String>();
            contactIds.add(String.valueOf(person1.getId()));
            contactIds.add(String.valueOf(company.getId()));

            contactApi.deleteBulkContacts(contactIds);

            //-------Adds a contact property, updates if it already exists---
            contactApi.addProperty("middlename", "crm", "test1@agilecrm.com");

        } catch (Exception e) {
            logger.error("API Exception", e);
        }
    }
}
