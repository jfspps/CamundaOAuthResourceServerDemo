package com.example.camundaoauthresourceserverdemo.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class SecurityBeans {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        var client = clientRegistration();
        return new InMemoryClientRegistrationRepository(client);
    }

    private ClientRegistration clientRegistration() {
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                // these would ultimately go in a config file not here
                .clientId("e1f50c7761db0a57c8ae")
                .clientSecret("e394489610a8a38ecb5dd9e3957e8d9893b265b1")
                .build();
    }
}
