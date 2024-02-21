package microservice.recensioneservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.web.client.*;
import org.springframework.web.cors.*;
import org.springframework.web.filter.*;

import java.util.*;


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
/*
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		List<String>  listHeaders= new ArrayList<>();

		listHeaders.addAll(Arrays.asList("Access-Control-Allow-Headers", "Authorization", "Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
		config.addAllowedOrigin("http://localhost:4200");
		config.setAllowedHeaders(listHeaders);
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

 */
}
