package com.maxicb.backend.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
    Instant now = Instant.now();
    User user = new User();
    Subreddit sub = new Subreddit();
    Long id = (long)0;
    String title = "test";
    String url = "test";
    String desc = "test";
    Integer count = 0;

    @Test
    public void postTest() {
        Post noArgPost = new Post();
        Post post = new Post(id, title, url, desc, count, user, now, sub);

        assertNotNull(noArgPost);
        assertNotNull(post);
        assertEquals(id, post.getPostId());
        assertEquals(title, post.getPostTitle());
        assertEquals(url, post.getUrl());
        assertEquals(desc, post.getDescription());
        assertEquals(count, post.getVoteCount());
        assertEquals(user, post.getUser());
        assertEquals(now, post.getCreationDate());
        assertEquals(sub, post.getSubreddit());
    }

    @Test
    public void postBuilderTest() {
        final Post post = Post.builder()
                .postId(id).postTitle(title)
                .url(url).description(desc)
                .voteCount(count).user(user)
                .creationDate(now).subreddit(sub).build();

        assertNotNull(post);
        assertEquals(id, post.getPostId());
        assertEquals(title, post.getPostTitle());
        assertEquals(url, post.getUrl());
        assertEquals(desc, post.getDescription());
        assertEquals(count, post.getVoteCount());
        assertEquals(user, post.getUser());
        assertEquals(now, post.getCreationDate());
        assertEquals(sub, post.getSubreddit());
    }
}
