package com.maxicb.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationEmailTest {
    @Test
    public void NotificationEmailTesting() {
        NotificationEmail email = new NotificationEmail("Test", "Test", "Test");
        assertNotNull(email);
        assertEquals(email.getBody(), "Test");
        assertEquals(email.getRecepient(), "Test");
        assertEquals(email.getSubject(), "Test");
        NotificationEmail noArgsEmail = new NotificationEmail();
        assertNotNull(noArgsEmail);
    }
}
