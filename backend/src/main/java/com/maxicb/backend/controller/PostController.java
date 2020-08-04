package com.maxicb.backend.controller;

import com.maxicb.backend.dto.PostRequest;
import com.maxicb.backend.dto.PostResponse;
import com.maxicb.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> addPost(@RequestBody PostRequest postRequest) {
        return new ResponseEntity<>(postService.save(postRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPost(@RequestParam Optional<Integer> page) {
        return new ResponseEntity<>(postService.getAllPost(page.orElse(0)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> getPostByID(@PathVariable Long id) {
        return new ResponseEntity<>(postService.findByID(id), HttpStatus.OK);
    }
    
    @GetMapping("/sub/{id}")
    public ResponseEntity<Page<PostResponse>> getPostsBySubreddit(@PathVariable Long id, @RequestParam Optional<Integer> page) {
        return new ResponseEntity<>(postService.getPostsBySubreddit(page.orElse(0), id), HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<Page<PostResponse>> getPostsByUsername(@PathVariable("name") String username, @RequestParam Optional<Integer> page) {
        return new ResponseEntity<>(postService.getPostsByUsername(username, page.orElse(0)), HttpStatus.OK);
    }
}
