package microservice.authenticationservice.dto;

import lombok.*;

@Data
public class ClientResponse {

	private long id;
	private String nome;
	private String cognome;
	private String mail;
	private String password;
	private int telefono;
	private Boolean googleAccount;
}
