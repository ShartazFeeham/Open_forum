package com.microforum.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/posts")
    Mono<String> postServiceFailureResponse() {
        // Handle actions & events like fallback strategies, failure alarm etc.
        return Mono.just("An error occurred in posts service, please contact administration for emergency.");
    }
}
