package com.maxicb.backend.repository;

import com.maxicb.backend.model.Subreddit;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface SubredditRepository extends PagingAndSortingRepository<Subreddit, Long> {
    Optional<Subreddit> findByName(String subredditName);


}
