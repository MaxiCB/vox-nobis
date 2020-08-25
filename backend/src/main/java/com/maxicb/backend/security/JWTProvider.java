package com.maxicb.backend.security;

import com.maxicb.backend.exception.ActivationException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;

import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.Jwts.parserBuilder;
import static java.util.Date.from;

@Service
public class JWTProvider {
    private KeyStore keystore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationMillis;

    @PostConstruct
    public void init() {
        try {
            keystore = KeyStore.getInstance("JKS");
            InputStream resourceStream = getClass().getResourceAsStream("/reddit.jks");
            keystore.load(resourceStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new ActivationException("Exception occured while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User princ = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(princ.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivKey())
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationMillis)))
                .compact();
    }

    public String generateTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivKey())
                .setExpiration(from(Instant.now().plusMillis(jwtExpirationMillis)))
                .compact();
    }

    public boolean validateToken (String token) {
        parserBuilder().setSigningKey(getPubKey()).build().parseClaimsJws(token);
        return true;
    }

    private PrivateKey getPrivKey () {
        try {
            return (PrivateKey) keystore.getKey("vox", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new ActivationException("Exception occurred while retrieving public key");
        }
    }

    private PublicKey getPubKey () {
        try {
            return keystore.getCertificate("vox").getPublicKey();
        } catch(KeyStoreException e) {
            throw new ActivationException("Exception occurred retrieving public key");
        }
    }

    public String getNameFromJWT(String token) {
        Claims claims = parserBuilder()
                .setSigningKey(getPubKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long getJwtExpirationMillis() {
        return jwtExpirationMillis;
    }
}
