package com.example.lightsaber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.jedi.jedishared"})
public class LightsaberApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightsaberApplication.class, args);
    }

}
