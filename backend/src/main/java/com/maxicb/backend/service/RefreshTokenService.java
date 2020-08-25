package com.maxicb.backend.service;

import com.maxicb.backend.exception.VoxNobisException;
import com.maxicb.backend.model.RefreshToken;
import com.maxicb.backend.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;

    RefreshToken generateRefreshToken () {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreationDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    void validateToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new VoxNobisException("Invalid Refresh Token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
