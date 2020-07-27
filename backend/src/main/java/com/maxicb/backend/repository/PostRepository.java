package com.maxicb.backend.repository;

import com.maxicb.backend.model.Post;
import com.maxicb.backend.model.Subreddit;
import com.maxicb.backend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
