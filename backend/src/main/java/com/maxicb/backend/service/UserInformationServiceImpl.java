package com.maxicb.backend.service;

import com.maxicb.backend.model.User;
import com.maxicb.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInformationServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    private Collection<? extends GrantedAuthority> fetchAuths (String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(s);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + s));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                user.isAccountStatus(),
                true,
                true,
                true,
                fetchAuths("USER"));
    }
}
