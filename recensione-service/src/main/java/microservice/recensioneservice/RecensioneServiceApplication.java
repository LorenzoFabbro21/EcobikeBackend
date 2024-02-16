package microservice.recensioneservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.web.client.*;


@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("microservice.recensioneservice.model")
@EnableJpaRepositories("microservice.recensioneservice.repo")
public class RecensioneServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecensioneServiceApplication.class, args);
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
