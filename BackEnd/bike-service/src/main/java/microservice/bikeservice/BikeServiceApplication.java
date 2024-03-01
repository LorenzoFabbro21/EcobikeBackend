package microservice.bikeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.*;
import org.springframework.web.filter.*;
import org.springframework.web.servlet.config.annotation.*;

@SpringBootApplication
@EnableDiscoveryClient
public class BikeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BikeServiceApplication.class, args);
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    }

