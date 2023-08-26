package com.luizrossetti.rinhabackend2023q3java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RinhaBackend2023Q3JavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RinhaBackend2023Q3JavaApplication.class, args);
    }

}
