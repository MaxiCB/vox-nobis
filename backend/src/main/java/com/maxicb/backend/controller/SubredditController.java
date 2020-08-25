package com.maxicb.backend.controller;

import com.maxicb.backend.dto.SubredditDTO;
import com.maxicb.backend.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

    SubredditService subredditService;

    @GetMapping("/{page}")
    public ResponseEntity<Page<SubredditDTO>> getAllSubreddits (@PathVariable("page") Integer page) {
        return new ResponseEntity<>(subredditService.getAll(page), HttpStatus.OK);
    }

    @GetMapping("/sub/{id}")
    public ResponseEntity<SubredditDTO> getSubreddit(@PathVariable("id") Long id) {
        return new ResponseEntity<>(subredditService.getSubreddit(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubredditDTO> addSubreddit(@RequestBody @Valid SubredditDTO subredditDTO) throws Exception{
        try {
            return new ResponseEntity<>(subredditService.save(subredditDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Error Creating Subreddit");
        }
    }
}
