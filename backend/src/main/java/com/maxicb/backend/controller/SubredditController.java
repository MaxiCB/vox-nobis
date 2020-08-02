package com.maxicb.backend.controller;

import com.maxicb.backend.dto.ErrorResponseDTO;
import com.maxicb.backend.dto.SubredditDTO;
import com.maxicb.backend.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

    SubredditService subredditService;

    @GetMapping("/{page}")
    public List<SubredditDTO> getAllSubreddits (@PathVariable int page) {
        return subredditService.getAll(page);
    }

    @GetMapping("/sub/{id}")
    public SubredditDTO getSubreddit(@PathVariable Long id) {
        return subredditService.getSubreddit(id);
    }

    @PostMapping
    public SubredditDTO addSubreddit(@RequestBody @Valid SubredditDTO subredditDTO) throws Exception {
        try {
            return subredditService.save(subredditDTO);
        } catch (Exception e) {
            throw new Exception("HEREEEEEEEEEEEEE");
        }
    }
}
