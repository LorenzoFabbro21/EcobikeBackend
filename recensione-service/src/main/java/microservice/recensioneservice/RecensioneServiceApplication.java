package microservice.recensioneservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.*;


@SpringBootApplication
@EnableDiscoveryClient
public class RecensioneServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecensioneServiceApplication.class, args);
	}

}
