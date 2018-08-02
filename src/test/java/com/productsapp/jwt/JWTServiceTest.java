package com.productsapp.jwt;

import io.jsonwebtoken.Clock;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class JWTServiceTest {

    private static final String TEST_USERNAME = "testUser";

    @Mock
    private Clock clockMock;

    @InjectMocks
    private JWTService jwtService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsernameFromToken() throws Exception {
        when(clockMock.now()).thenReturn(DateUtil.now());

        final String token = createToken();

        assertThat(jwtService.getUsernameFromToken(token)).isEqualTo(TEST_USERNAME);
    }

    private String createToken() {
        return jwtService.generateToken(User.withUsername(TEST_USERNAME)
            .password(TEST_USERNAME)
            .authorities(Collections.emptyList())
            .build());
    }
}
