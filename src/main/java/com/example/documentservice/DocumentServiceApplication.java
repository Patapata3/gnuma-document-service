package com.example.documentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


//login vorname.nachname
//passwort 123456
@SpringBootApplication
public class DocumentServiceApplication {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DocumentServiceApplication.class, args);
    }
}

