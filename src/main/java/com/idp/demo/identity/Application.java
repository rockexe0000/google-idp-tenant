package com.idp.demo.identity;

import com.google.auth.oauth2.GoogleCredentials;
import com.idp.demo.identity.service.GcpIdentityService;
import com.idp.demo.identity.service.TenantService;
import com.idp.demo.identity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    GcpIdentityService gcpIdentityService;

    @Autowired
    TenantService tenantService;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public GoogleCredentials initGoogleCredentials() {
        Collection<String> scope = new ArrayList<>();
        scope.add("https://www.googleapis.com/auth/cloud-platform");
        scope.add("https://www.googleapis.com/auth/firebase");
        try {
            return GoogleCredentials.getApplicationDefault().createScoped(scope);
        } catch (IOException e) {
            log.error("Init GoogleCredentials failed!", e);
            throw new IllegalStateException("Init GoogleCredentials failed!");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Identity service started!");
    }
}
