package com.maxicb.backend.service;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.maxicb.backend.dto.CommentRequest;
import com.maxicb.backend.dto.CommentResponse;
import com.maxicb.backend.exception.PostNotFoundException;
import com.maxicb.backend.exception.UserNotFoundException;
import com.maxicb.backend.model.Comment;
import com.maxicb.backend.model.Post;
import com.maxicb.backend.model.User;
import com.maxicb.backend.repository.CommentRepository;
import com.maxicb.backend.repository.PostRepository;
import com.maxicb.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;

    private CommentResponse mapToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .postId(comment.getPost().getPostId())
                .creationDate(TimeAgo.using(comment.getCreationDate().toEpochMilli()))
                .userName(comment.getUser().getUsername())
                .build();
    }

    private Comment mapToComment(CommentRequest commentRequest) {
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + commentRequest.getPostId()));
        return Comment.builder()
                .text(commentRequest.getText())
                .post(post)
                .creationDate(Instant.now())
                .user(user)
                .build();
    }

    public CommentResponse save(CommentRequest commentRequest) {
        return mapToResponse(commentRepository.save(mapToComment(commentRequest)));
    }

    public List<CommentResponse> getCommentsForPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
        return commentRepository.findByPost(post)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CommentResponse> getCommentsForUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
