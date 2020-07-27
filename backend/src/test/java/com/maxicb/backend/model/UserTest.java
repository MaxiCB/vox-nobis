package com.maxicb.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

public class UserTest {
    @Test
    public void userTesting() {
        Instant nowInstant = Instant.now();
        User testUser = new User(1, "Test", "Test", "Test", nowInstant, true);
        assertNotNull(testUser);
        assertEquals(1, testUser.getUserId());
        assertEquals("Test", testUser.getUsername());
        assertEquals("Test", testUser.getPassword());
        assertEquals("Test", testUser.getEmail());
        assertEquals(nowInstant, testUser.getCreationDate());
        assertTrue(testUser.isAccountStatus());
        User noArgUser = new User();
        assertNotNull(noArgUser);
    }
}
