package com.productsapp.rest;

import com.productsapp.ProductsApp;
import com.productsapp.jwt.JwtPayload;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static com.productsapp.security.SecurityConstants.HEADER_STRING;
import static com.productsapp.security.SecurityConstants.REGISTER_URL;
import static com.productsapp.security.SecurityConstants.TOKEN_PREFIX;
import static com.productsapp.util.TestUtils.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.config.BeanIds.SPRING_SECURITY_FILTER_CHAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = ProductsApp.class)
public class SecurityIntTest {

    private MockMvc registerMockMvc;

    private JwtPayload user;

    private static final String LOGIN_ENDPOINT = "/login";

    @Autowired
    @Qualifier(SPRING_SECURITY_FILTER_CHAIN)
    private Filter securityFilter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Before
    public void setup() throws Exception {
        user = JwtPayload.builder().username("user").password("password").build();
        registerMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .addFilter(securityFilter)
                .build();
    }

    @After
    public void clear() {
        userDetailsManager.deleteUser(user.getUsername());

    }

    @Test
    public void successRegisterUser() throws Exception {
        registerMockMvc.perform(post(REGISTER_URL)
                .content(convertObjectToJsonBytes(user))
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void successLoginAfterRegisterUser() throws Exception {
        register(user);
        login(user);
    }

    @Test
    public void failLoginWithoutRegisterUser() throws Exception {
        registerMockMvc.perform(post(LOGIN_ENDPOINT)
                .content(convertObjectToJsonBytes(user)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void failAccessProtectedEndpointsWithoutToken() throws Exception {
        registerMockMvc.perform(get(ProductController.ENDPOINT))
                .andExpect(status().isForbidden());
    }

    @Test
    public void successAccessProtectedEndpointsWithToken() throws Exception {
        register(user);
        String tokenHeader = login(user);
        registerMockMvc.perform(get(ProductController.ENDPOINT).header(HEADER_STRING, tokenHeader))
                .andExpect(status().isOk());
    }

    private void register(JwtPayload user) throws Exception {
        registerMockMvc.perform(post(REGISTER_URL)
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(user))
        ).andExpect(status().isOk());

    }

    private String login(JwtPayload user) throws Exception {
        return registerMockMvc.perform(post(LOGIN_ENDPOINT)
                .content(convertObjectToJsonBytes(user)))
                .andExpect(status().isOk())
                .andExpect(header().string(HEADER_STRING, notNullValue()))
                .andExpect(header().string(HEADER_STRING, startsWith(TOKEN_PREFIX)))
                .andReturn().getResponse().getHeader(HEADER_STRING);
    }

    @Test
    public void contextLoads() {
    }
}
