package com.jobseeker.security;

import com.jobseeker.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.UUID;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${JobSeekerApplication.http.auth-token-header-name}")
    private String tokenName;

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        APIKeyAuthFilter filter = new APIKeyAuthFilter(tokenName);

        filter.setAuthenticationManager(authentication -> {
            if(!clientRepository.existsByUuid( UUID.fromString( (String) authentication.getPrincipal()))) {
                throw new BadCredentialsException("API key not found.");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });

        httpSecurity.
                csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(filter)
                .authorizeRequests()
                .antMatchers("/","/client").permitAll()
                .anyRequest().authenticated();
    }
}
