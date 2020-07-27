package com.maxicb.backend.repository;

import com.maxicb.backend.model.Comment;
import com.maxicb.backend.model.Post;
import com.maxicb.backend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
