package microservice.authenticationservice.dto;

import lombok.*;

@Data
public class ClientResponse {

	private long id;
	private String name;
	private String lastName;
	private String mail;
	private String password;
	private String phoneNumber;
	private Boolean googleCheck;
}
