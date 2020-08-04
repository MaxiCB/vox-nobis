package com.maxicb.backend.repository;

import com.maxicb.backend.model.Post;
import com.maxicb.backend.model.Subreddit;
import com.maxicb.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findAllBySubreddit(Subreddit subreddit, Pageable pageable);

    Page<Post> findByUser(User user, Pageable pageable);
}
