package com.schoolapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchoolManagementApplication {

    // 1. Initialize the logger instance
    private static final Logger logger = LoggerFactory.getLogger(SchoolManagementApplication.class);

    public static void main(String[] args) {
        logger.info("###########Starting School Management Application##################");
        
        SpringApplication.run(SchoolManagementApplication.class, args);
        
        logger.info("#######################School Management Application started successfully#################");
    }

}