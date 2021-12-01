package com.example.poc.masterspreparation;

import com.example.poc.masterspreparation.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class MastersPreparationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MastersPreparationApplication.class, args);
    }
}
