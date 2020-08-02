package com.maxicb.backend.controller;

import com.maxicb.backend.dto.PostRequest;
import com.maxicb.backend.dto.PostResponse;
import com.maxicb.backend.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> addPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost() {
        return new ResponseEntity<>(postService.getAllPost(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> getPostByID(@PathVariable Long id) {
        return new ResponseEntity<>(postService.findByID(id), HttpStatus.OK);
    }
    
    @GetMapping("/sub/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostsBySubreddit(id), HttpStatus.OK);
    }

//    Need to handle cases where username seems to be Int when it is a string
//    Causes errors fetching posts
//    IE - Username: 22222
    @GetMapping("/user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(postService.getPostsByUsername(username), HttpStatus.OK);
    }
}
