package com.maxicb.backend.service;

import com.maxicb.backend.dto.VoteDTO;
import com.maxicb.backend.exception.PostNotFoundException;
import com.maxicb.backend.exception.VoteException;
import com.maxicb.backend.model.Post;
import com.maxicb.backend.model.Vote;
import com.maxicb.backend.repository.PostRepository;
import com.maxicb.backend.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.maxicb.backend.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    private Vote maptoVote(VoteDTO voteDTO, Post post) {
        return Vote.builder()
                .voteType(voteDTO.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }

    @Transactional
    public void vote(VoteDTO voteDTO) {
        Post post = postRepository.findById(voteDTO.getId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with id:" + voteDTO.getId()));
        Optional<Vote> votePostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if(votePostAndUser.isPresent() && votePostAndUser.get().getVoteType().equals(voteDTO.getVoteType())) {
            throw new VoteException("You've already " + voteDTO.getVoteType() + "'d this post");
        }
        if(UPVOTE.equals(voteDTO.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(maptoVote(voteDTO, post));
        postRepository.save(post);
    }
}
