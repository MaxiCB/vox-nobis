package com.maxicb.backend.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {
    Post post = new Post();
    Instant now = Instant.now();
    User user = new User();
    @Test
    public void commentTesting() {
        Comment noArgComment = new Comment();
        Comment comment = new Comment((long)0, "test", post, now, user);
        assertNotNull(noArgComment);
        assertNotNull(comment);
        assertEquals((long)0, comment.getId());
        assertEquals("test", comment.getText());
        assertEquals(post, comment.getPost());
        assertEquals(now, comment.getCreationDate());
        assertEquals(user, comment.getUser());
    }

    @Test
    public void commentBuilderTesting() {
        Comment comment = Comment.builder().id((long)0).text("test").post(post).creationDate(now).user(user).build();
        assertNotNull(comment);
        assertEquals((long)0, comment.getId());
        assertEquals("test", comment.getText());
        assertEquals(post, comment.getPost());
        assertEquals(now, comment.getCreationDate());
        assertEquals(user, comment.getUser());
    }
}
