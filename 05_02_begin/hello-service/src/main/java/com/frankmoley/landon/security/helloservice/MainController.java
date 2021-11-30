package com.frankmoley.landon.security.helloservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class MainController {

    private DicePairRoll dice = new DicePairRoll();

    @GetMapping("/hello")
    Mono<String> getGreeting(){
        return Mono.just("Hello LinkedIn Learning! Never stop learning");
    }

    @GetMapping("/roll")
    Mono<DicePairRoll> getDiceRoll(){
        dice.rollDice();
        return Mono.just(dice);
    }

}
