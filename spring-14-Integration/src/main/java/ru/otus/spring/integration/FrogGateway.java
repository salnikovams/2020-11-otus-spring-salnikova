package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.Frog;


@MessagingGateway
public interface FrogGateway {

    @Gateway(requestChannel = "inFrogChannel", replyChannel = "outFrogChannel")
    public Frog processNewFrog(Frog frog);
}
