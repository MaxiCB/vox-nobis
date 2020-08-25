package com.maxicb.backend.service;

import com.maxicb.backend.dto.AuthResponse;
import com.maxicb.backend.dto.LoginRequest;
import com.maxicb.backend.dto.RefreshTokenRequest;
import com.maxicb.backend.dto.RegisterRequest;
import com.maxicb.backend.exception.ActivationException;
import com.maxicb.backend.model.AccountVerificationToken;
import com.maxicb.backend.model.NotificationEmail;
import com.maxicb.backend.model.RefreshToken;
import com.maxicb.backend.model.User;
import com.maxicb.backend.repository.TokenRepository;
import com.maxicb.backend.repository.UserRepository;
import com.maxicb.backend.security.JWTProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.maxicb.backend.config.Constants.EMAIL_ACTIVATION;

@Service
@AllArgsConstructor

public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    TokenRepository tokenRepository;
    MailService mailService;
    MailBuilder mailBuilder;
    AuthenticationManager authenticationManager;
    JWTProvider jwtProvider;
    RefreshTokenService refreshTokenService;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreationDate(Instant.now());
        user.setAccountStatus(false);

        userRepository.save(user);

        String token = generateToken(user);
        String message = mailBuilder.build("Welcome to React-Spring-Reddit Clone. " +
                "Please visit the link below to activate you account : " + EMAIL_ACTIVATION + "/" + token);
        mailService.sendEmail(new NotificationEmail("Please Activate Your Account", user.getEmail(), message));
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + principal.getUsername()));
    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return new AuthResponse(token, refreshTokenService.generateRefreshToken().getToken(), Instant.now().plusMillis(jwtProvider.getJwtExpirationMillis()), refreshTokenRequest.getUsername());
    }

    public AuthResponse login (LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authToken = jwtProvider.generateToken(authenticate);
        String refreshToken = refreshTokenService.generateRefreshToken().getToken();
        return new AuthResponse(authToken, refreshToken, Instant.now().plusMillis(jwtProvider.getJwtExpirationMillis()), loginRequest.getUsername());
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generateToken(User user) {
        String token = UUID.randomUUID().toString();
        AccountVerificationToken verificationToken = new AccountVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);
        return token;
    }

    public void verifyToken(String token) {
        Optional<AccountVerificationToken> verificationToken = tokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new ActivationException("Invalid Activation Token"));
        enableAccount(verificationToken.get());
    }

    public void enableAccount(AccountVerificationToken token) {
        String username = token.getUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ActivationException("User not found with username: " + username));
        user.setAccountStatus(true);
        userRepository.save(user);
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
