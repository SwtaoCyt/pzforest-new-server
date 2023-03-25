package com.song.pzforestserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import static org.springdoc.core.utils.Constants.SPRINGDOC_ENABLED;

@ConditionalOnProperty(name = SPRINGDOC_ENABLED, matchIfMissing = true)
@SpringBootApplication
public class PzforestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PzforestServerApplication.class, args);
    }

}
