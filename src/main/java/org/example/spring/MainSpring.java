package org.example.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class MainSpring {
    public static void main(String[] args) {
        SpringApplication.run(MainSpring.class, args);
    }
}
