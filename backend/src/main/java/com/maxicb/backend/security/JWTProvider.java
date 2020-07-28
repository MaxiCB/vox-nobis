package com.maxicb.backend.security;

import com.maxicb.backend.exception.ActivationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import io.jsonwebtoken.Jwts;

@Service
public class JWTProvider {
    private KeyStore keystore;

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
        return Jwts.builder().setSubject(princ.getUsername()).signWith(getPrivKey()).compact();
    }

    private PrivateKey getPrivKey () {
        try {
            return (PrivateKey) keystore.getKey("vox", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new ActivationException("Exception occurred while retrieving public key");
        }
    }
}
