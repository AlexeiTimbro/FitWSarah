package com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SecurityConfigTest {

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnCorsConfigurationSource() {
        CorsConfigurationSource corsConfigurationSource = securityConfig.corsConfigurationSource();
        assertNotNull(corsConfigurationSource);
    }

    @Test
    public void shouldBuildSecurityFilterChain() throws Exception {
        when(httpSecurity.cors(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.oauth2ResourceServer(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(null);

        securityConfig.securityFilterChain(httpSecurity);

        verify(httpSecurity, times(1)).build();
    }

    @Test
    public void shouldValidateJwtWithCorrectAudience() {
        when(jwt.getAudience()).thenReturn(Arrays.asList("correct-audience"));
        OAuth2TokenValidatorResult result = securityConfig.new AudienceValidator("correct-audience").validate(jwt);
        assertFalse(result.hasErrors());
    }

    @Test
    public void shouldNotValidateJwtWithIncorrectAudience() {
        when(jwt.getAudience()).thenReturn(Arrays.asList("incorrect-audience"));
        OAuth2TokenValidatorResult result = securityConfig.new AudienceValidator("correct-audience").validate(jwt);
        assertTrue(result.hasErrors());
        assertEquals("invalid_token", result.getErrors().iterator().next().getErrorCode());
    }
}