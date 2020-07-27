package com.maxicb.backend.repository;

import com.maxicb.backend.model.Subreddit;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubredditRepository extends CrudRepository<Subreddit, Long> {
    Optional<Subreddit> findByName(String subredditName);
}
