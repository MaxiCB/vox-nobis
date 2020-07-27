package com.maxicb.backend.model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

public class VerificationTokenTest {
    @Test
    public void tokenTest() {
        Instant now = Instant.now();
        User user = new User();
        AccountVerificationToken noArgToken = new AccountVerificationToken();
        AccountVerificationToken token = new AccountVerificationToken((long)0, "token", user, now);
        assertNotNull(noArgToken);
        assertNotNull(token);
        assertEquals((long)0, token.getId());
        assertEquals("token", token.getToken());
        assertEquals(user, token.getUser());
        assertEquals(now, token.getExpirationDate());
    }
    @Test
    public void tokenBuilderTest() {
        final Instant now = Instant.now();
        final User user = new User();

        final AccountVerificationToken token = AccountVerificationToken.builder().id((long)0).token("token").user(user).expirationDate(now).build();
        assertNotNull(token);
        assertEquals((long)0, token.getId());
        assertEquals("token", token.getToken());
        assertEquals(user, token.getUser());
        assertEquals(now, token.getExpirationDate());
    }
}
