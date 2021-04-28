package ru.otus.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel inFrogChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel outFrogChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public IntegrationFlow bookFlow() {
        return IntegrationFlows.from("inFrogChannel")
                .handle("frogServiceImpl", "addFrog")
                .handle("randomStateServiceImpl", "addRandomState")
                .channel("outFrogChannel")
                .get();
    }
}
