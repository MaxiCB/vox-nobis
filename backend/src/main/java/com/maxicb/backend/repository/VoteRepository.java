package com.maxicb.backend.repository;

import com.maxicb.backend.model.Post;
import com.maxicb.backend.model.User;
import com.maxicb.backend.model.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
