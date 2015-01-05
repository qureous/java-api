package com.agilecrm.examples;

import com.agilecrm.api.AgileConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>TestAgileConnection</code> creates and tests connection
 * with Agile CRM
 *
 * @author matthew
 * @since January 2015
 * @see AgileConnection
 *
 */
public class TestAgileConnection {

    private static final Logger logger = LoggerFactory.getLogger(TestAgileConnection.class);

    public static void main(String[] args) {
        try {
            new AgileConnection().getConnection();

        } catch (Exception e) {
            logger.error("Connection Failed", e);
        }
    }
}