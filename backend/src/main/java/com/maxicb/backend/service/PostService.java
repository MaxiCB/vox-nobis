package com.maxicb.backend.service;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.maxicb.backend.dto.PostRequest;
import com.maxicb.backend.dto.PostResponse;
import com.maxicb.backend.exception.PostNotFoundException;
import com.maxicb.backend.exception.SubredditNotFoundException;
import com.maxicb.backend.exception.UserNotFoundException;
import com.maxicb.backend.model.*;
import com.maxicb.backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final VoteRepository voteRepository;

    private boolean checkVoteType(Post post, VoteType voteType) {
        if(authService.isLoggedIn()) {
            Optional<Vote> voteForPostForUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
            return voteForPostForUser.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;
    }

    private PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .url(post.getUrl())
                .description(post.getDescription())
                .userName(post.getUser().getUsername())
                .subredditName(post.getSubreddit().getName())
                .voteCount(post.getVoteCount())
                .commentCount(commentRepository.findAllByPost(post).size())
                .duration(TimeAgo.using(post.getCreationDate().toEpochMilli()))
                .upVote(checkVoteType(post, VoteType.UPVOTE))
                .downVote(checkVoteType(post, VoteType.DOWNVOTE))
                .build();
    }

    private Post mapToPost(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        Post newPost = Post.builder()
                .postTitle(postRequest.getPostTitle())
                .url(postRequest.getUrl())
                .description(postRequest.getDescription())
                .voteCount(0)
                .user(authService.getCurrentUser())
                .creationDate(Instant.now())
                .subreddit(subreddit)
                .build();
        subreddit.getPosts().add(newPost);
        return newPost;
    }

    public PostResponse save(PostRequest postRequest) {
        return mapToResponse(postRepository.save(mapToPost(postRequest)));
    }

    public Page<PostResponse> getAllPost(Integer page) {
        return postRepository.findAll(PageRequest.of(page, 100)).map(this::mapToResponse);
    }

    public PostResponse findByID (Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
        return mapToResponse(post);
    }

    public Page<PostResponse> getPostsBySubreddit(Integer page, Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id: " + id));
        return postRepository
                .findAllBySubreddit(subreddit, PageRequest.of(page, 100))
                .map(this::mapToResponse);
    }

    public Page<PostResponse> getPostsByUsername(String username, Integer page) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return postRepository
                .findByUser(user, PageRequest.of(page, 100))
                .map(this::mapToResponse);
    }
}
