package com.fyp.companyinterfaceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompanyInterfaceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyInterfaceServiceApplication.class, args);
    }

}
