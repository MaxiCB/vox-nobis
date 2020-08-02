package com.maxicb.backend.controller;

import com.maxicb.backend.dto.CommentRequest;
import com.maxicb.backend.dto.CommentResponse;
import com.maxicb.backend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.save(commentRequest), HttpStatus.CREATED);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getCommentsForPost(id), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByUser(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getCommentsForUser(id), HttpStatus.OK);
    }
}
