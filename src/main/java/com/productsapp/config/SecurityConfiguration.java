package com.productsapp.config;

import com.productsapp.jwt.JWTService;
import com.productsapp.security.filter.JWTAuthenticationFilter;
import com.productsapp.security.filter.JWTRequestAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.productsapp.security.SecurityConstants.HEADER_STRING;
import static com.productsapp.security.SecurityConstants.REGISTER_URL;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, REGISTER_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
                .addFilterAfter(new JWTRequestAuthenticationFilter(userDetailsManager(), jwtService)
                        , JWTAuthenticationFilter.class);

        // don't create session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // Un-secure H2 Database
                .antMatchers("/h2-console/**/**").permitAll()
                .anyRequest().authenticated();

        http.logout()
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));

        // disable page caching
        http.headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsManager()).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.GET
                        , "/"
                        , "/*.html"
                        , "/favicon.ico"
                        , "/**/*.html"
                        , "/**/*.css"
                        , "/**/*.js"
                )

                // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
                .and().ignoring().antMatchers("/h2-console/**/**");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addExposedHeader(HEADER_STRING);
        configuration.addAllowedMethod(CorsConfiguration.ALL);
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        configuration.addAllowedOrigin(CorsConfiguration.ALL);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
