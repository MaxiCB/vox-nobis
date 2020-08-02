package com.maxicb.backend.service;

import com.maxicb.backend.dto.SubredditDTO;
import com.maxicb.backend.exception.ActivationException;
import com.maxicb.backend.exception.SubredditNotFoundException;
import com.maxicb.backend.model.Subreddit;
import com.maxicb.backend.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final AuthService authService;

    private SubredditDTO mapToDTO (Subreddit subreddit) {
        return SubredditDTO.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .postCount(subreddit.getPosts().size())
                .build();

    }

    private Subreddit mapToSubreddit (SubredditDTO subredditDTO) {
        return Subreddit.builder().name("/r/" + subredditDTO.getName())
                .description(subredditDTO.getDescription())
                .user(authService.getCurrentUser())
                .creationDate(Instant.now())
                .build();

    }

    @Transactional(readOnly = true)
    public List<SubredditDTO> getAll(int page) {
        PageRequest pageRequest = PageRequest.of(page, 100);
        return StreamSupport
                .stream(subredditRepository.findAll(pageRequest).spliterator(), false)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubredditDTO save(SubredditDTO subredditDTO) throws  Exception {
        try {
            Subreddit subreddit = subredditRepository.save(mapToSubreddit(subredditDTO));
            subredditDTO.setId(subreddit.getId());
            return subredditDTO;
        } catch (Exception e) {
            throw new Exception("HEREAAAAAAA");
        }
    }

    @Transactional(readOnly = true)
    public SubredditDTO getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id -" + id));
        return mapToDTO(subreddit);
    }
}
