package com.maxicb.backend.repository;

import com.maxicb.backend.model.Comment;
import com.maxicb.backend.model.Post;
import com.maxicb.backend.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
