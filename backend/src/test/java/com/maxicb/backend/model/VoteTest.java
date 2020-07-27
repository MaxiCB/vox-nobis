package com.maxicb.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VoteTest {
    Long id = (long)0;
    VoteType down = VoteType.DOWNVOTE;
    VoteType up = VoteType.UPVOTE;
    Post post = new Post();
    User user = new User();

    @Test
    public void voteTesting() {
        Vote noArgVote = new Vote();
        Vote vote = new Vote(id, down, post, user);

        assertNotNull(noArgVote);
        assertNotNull(vote);
        assertEquals(id, vote.getVoteId());
        assertEquals(down, vote.getVoteType());
        assertEquals(post, vote.getPost());
        assertEquals(user, vote.getUser());
    }

    @Test
    public void voteBuilderTesting() {
        Vote noArgVote = new Vote();
        Vote vote = Vote.builder()
                .voteId(id).voteType(up)
                .post(post).user(user)
                .build();

        assertNotNull(noArgVote);
        assertNotNull(vote);
        assertEquals(id, vote.getVoteId());
        assertEquals(up, vote.getVoteType());
        assertEquals(post, vote.getPost());
        assertEquals(user, vote.getUser());
    }
}
