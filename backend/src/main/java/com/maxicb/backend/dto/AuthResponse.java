package com.maxicb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AuthResponse {
        private String authenticationToken;
        private String refreshToken;
        private Instant expiresAt;
        private String username;
}
