package com.taskgpb.test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.taskgpb.test.SecurityPackage.CustomUser;
import com.taskgpb.test.SecurityPackage.CustomUserDetailsService;
import com.taskgpb.test.SecurityPackage.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private CustomUser customUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customUser = new CustomUser(null,"testUser", "password");
    }

    @Test
    public void loadUserByUsername_UserExists_ReturnsUserDetails() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(customUser));

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        verify(userRepository).findByUsername("testUser");
    }

    @Test
    public void loadUserByUsername_UserDoesNotExist_ThrowsUsernameNotFoundException() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("unknownUser");
        });

        verify(userRepository).findByUsername("unknownUser");
    }
}
