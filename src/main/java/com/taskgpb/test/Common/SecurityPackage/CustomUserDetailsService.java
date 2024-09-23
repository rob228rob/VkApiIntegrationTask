package com.taskgpb.test.Common.SecurityPackage;

import com.taskgpb.test.Common.CustomException.UserBadCredentialsException;
import com.taskgpb.test.Common.DTOs.RegisterDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                customUser.getUsername(),
                customUser.getPassword(),
                Collections.emptyList()
        );
    }

    public boolean existByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public CustomUser saveUser(RegisterDTO userDTO) throws UserBadCredentialsException {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            log.error("passwords: " + userDTO.getPassword() + " " + userDTO.getConfirmPassword());
            throw new UserBadCredentialsException("Passwords do not match");
        }

        return userRepository.save(
                new CustomUser(
                        null,
                        userDTO.getLogin(),
                        passwordEncoder.encode(userDTO.getPassword()))
        );
    }

    public Optional<CustomUser> findUserByUsername(@NotNull String username) {
        return userRepository.findByUsername(username);
    }
}
