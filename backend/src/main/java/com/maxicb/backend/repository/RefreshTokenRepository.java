package com.maxicb.backend.repository;

import com.maxicb.backend.model.RefreshToken;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends PagingAndSortingRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
