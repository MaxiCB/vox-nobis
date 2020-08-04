package com.maxicb.backend.controller;

import com.maxicb.backend.dto.CommentRequest;
import com.maxicb.backend.dto.CommentResponse;
import com.maxicb.backend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Page<CommentResponse>> getCommentsByPost(@PathVariable("id") Long id, @RequestParam Optional<Integer> page) {
        return new ResponseEntity<>(commentService.getCommentsForPost(id, page.orElse(0)), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<CommentResponse>> getCommentsByUser(@PathVariable("id") Long id,@RequestParam Optional<Integer> page) {
        return new ResponseEntity<>(commentService.getCommentsForUser(id, page.orElse(0)), HttpStatus.OK);
    }
}
