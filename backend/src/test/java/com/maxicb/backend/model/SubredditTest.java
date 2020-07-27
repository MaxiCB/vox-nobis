package com.maxicb.backend.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SubredditTest {
    Long id = (long)0;
    String name = "test";
    String desc = "test";
    Post post = new Post();
    List<Post> posts = new ArrayList<Post>();
    Instant now = Instant.now();
    User user = new User();

    @Test
    public void subTesting() {
        posts.add(post);
        Subreddit noArgSub = new Subreddit();
        Subreddit sub = new Subreddit(id, name, desc, posts, now, user);

        assertNotNull(noArgSub);
        assertNotNull(sub);
        assertEquals(id, sub.getId());
        assertEquals(name, sub.getName());
        assertEquals(desc, sub.getDescription());
        assertEquals(posts, sub.getPosts());
        assertEquals(now, sub.getCreationDate());
        assertEquals(user, sub.getUser());
    }

    @Test
    public void subBuilderTesting() {
        Subreddit sub = Subreddit.builder()
                .id(id).name(name).description(desc)
                .posts(posts).creationDate(now).user(user)
                .build();

        assertNotNull(sub);
        assertEquals(id, sub.getId());
        assertEquals(name, sub.getName());
        assertEquals(desc, sub.getDescription());
        assertEquals(posts, sub.getPosts());
        assertEquals(now, sub.getCreationDate());
        assertEquals(user, sub.getUser());
    }
}
