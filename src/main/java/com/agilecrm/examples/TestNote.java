package com.agilecrm.examples;

import com.agilecrm.api.APIManager;
import com.agilecrm.api.AgileConnection;
import com.agilecrm.api.ContactAPI;
import com.agilecrm.api.NoteAPI;
import com.agilecrm.stubs.Contact;
import com.agilecrm.stubs.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <code>TestNote</code> class contains main method to test the methods in
 * <code>NoteAPI</code> class.
 *
 * @author Tejaswi
 * @see NoteAPI
 * @since March 2013
 */
public class TestNote {

    private static final Logger logger = LoggerFactory.getLogger(TestNote.class);

    public static void main(String[] args) {
        try {
            // Create a connection to Agile CRM
            APIManager apiManager = new AgileConnection().getConnection();

            // Get the Note API with configured resource
            NoteAPI noteApi = apiManager.getNoteAPI();

            // List of contact id's to which notes are added
            List<String> contactIds = new ArrayList<String>();

            // create list of ids by finding contacts by email address,
            // this initial contact was created by running TestContact
            ContactAPI contactAPI = apiManager.getContactAPI();
            List<String> emails = Arrays.asList("test1@agilecrm.com");
            for (String email : emails) {
                Contact contact = contactAPI.getContactFromEmail("test1@agilecrm.com");
                contactIds.add(contact.getId().toString());
            }

            // Adding note
            Note note1 = new Note();

            note1.setSubject("Test Note1");
            note1.setDescription("Testing to add note1");

            note1 = noteApi.addNoteToContactIds(note1, contactIds);

            logger.info("Added note... {}", note1);

            // Another method to add note
            Note note2 = new Note();

            note2 = noteApi.addNoteToContactIds("Test Note2",
                    "Testing to add note2", contactIds);

            logger.info("Added note... {}", note2);

            // Get notes of a contact
            List<Note> notes = noteApi.getNotesByContactId(contactIds.get(0));

            logger.info("All notes of contact... " + notes);

            // Delete note of a contact by note id
            String noteId = String.valueOf(notes.get(0).getId());

            noteApi.deleteNoteByContactId(contactIds.get(0), noteId);

            logger.info("Deleted note... {} from Contact... {}", noteId, contactIds.get(0));

            notes = noteApi.getNotesByContactId(contactIds.get(0));

            logger.info("All notes of contact... {}", notes);

        } catch (Exception e) {
            logger.error("API Exception", e);
        }

    }
}
