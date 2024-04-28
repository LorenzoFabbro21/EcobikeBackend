package microservice.authenticationservice.service;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import lombok.*;
import lombok.extern.slf4j.*;
import microservice.authenticationservice.dto.*;
import microservice.authenticationservice.model.*;
import microservice.authenticationservice.rabbitMQ.RabbitMQSender;
import net.minidev.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.net.*;
import java.nio.charset.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final long TOKEN_EXP = 600000; // 10 minute
    private final String SECRET_KEY = "secret";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user-service.url}")
    String userServiceUrl;
    
    private final RabbitMQSender rabbitMQSender;

    @Override
    public void googleLogin(OAuth2User user) {
        //send a message to the user service to create the user
        User userDetails = new User();
        userDetails.setMail(user.getAttribute("email"));
        userDetails.setName(user.getAttribute("given_name"));
        userDetails.setLastName(user.getAttribute("family_name"));
        userDetails.setGoogleCheck(true);
        userDetails.setPassword("");
        userDetails.setPhoneNumber("");

        rabbitMQSender.sendCreateUser(userDetails);

    }

    @Override
    public ResponseEntity<?> signup(UserDetails userDetails) {
        userDetails.setGoogleCheck(false);

        //User u = new User(userDetails.getName(), userDetails.getLastName(), userDetails.getMail(), userDetails.getPassword(), userDetails.getPhoneNumber(), userDetails.getGoogleCheck())
        //rabbitMQSender.sendSignUp(u);
        try {
            HttpStatusCode responseStatusCode = restTemplate.postForEntity("http://user-service:8081/api/private", userDetails,String.class).getStatusCode();
            if (responseStatusCode != HttpStatus.OK) {
                return ResponseEntity.status(responseStatusCode).body("Invalid request");
            }
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "User add request sent successfully");
            return ResponseEntity.status(HttpStatus.OK).body(body);

        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
        }
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {

            Optional<User> private_user = Optional.ofNullable(restTemplate.getForObject("http://user-service:8081/api/private/email/" + loginRequest.getEmail(), User.class));
            Optional<User> dealer = Optional.ofNullable(restTemplate.getForObject("http://user-service:8081/api/dealer/email/" + loginRequest.getEmail(), User.class));
            if (private_user.isPresent()) {
                try {
                    HttpStatusCode responseStatusCode = restTemplate.getForEntity("http://user-service:8081/api/private/verify?email=" + loginRequest.getEmail() + "&password=" + loginRequest.getPassword(), String.class).getStatusCode();
                    if (responseStatusCode != HttpStatus.OK) {
                        Map<String, String> body = new HashMap<>();
                        body.put("Message", "Invalid credentials");
                        return ResponseEntity.status(responseStatusCode).body(body);
                    }
                    ResponseEntity<User> response = restTemplate.getForEntity("http://user-service:8081/api/private/email/" + loginRequest.getEmail(), User.class);
                    if (response.getBody() == null) {
                        Map<String, String> body = new HashMap<>();
                        body.put("Message", "Server error occurred");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
                    }
                    // Generate JWT token
                    String token = JWT.create().withSubject(loginRequest.getEmail()).withClaim("name", response.getBody().getName() != null ? response.getBody().getName() : "").withClaim("last_name", response.getBody().getLastName() != null ? response.getBody().getLastName() : "").withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXP)).sign(Algorithm.HMAC256(SECRET_KEY));

                    Map<String, String> body = new HashMap<>();
                    body.put("token", token);

                    return ResponseEntity.status(HttpStatus.OK).body(body);
                }
                catch (HttpClientErrorException e) {
                    return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
                } catch (Exception e) {

                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
                }
        } else if (dealer.isPresent()){
                try {
                    HttpStatusCode responseStatusCode = restTemplate.getForEntity("http://user-service:8081/api/dealer/verify?email=" + loginRequest.getEmail() + "&password=" + loginRequest.getPassword(), String.class).getStatusCode();
                    if (responseStatusCode != HttpStatus.OK) {
                        Map<String, String> body = new HashMap<>();
                        body.put("Message", "Invalid credentials");
                        return ResponseEntity.status(responseStatusCode).body(body);
                    }
                    ResponseEntity<User> response = restTemplate.getForEntity("http://user-service:8081/api/dealer/email/" + loginRequest.getEmail(), User.class);

                    if (response.getBody() == null) {
                        Map<String, String> body = new HashMap<>();
                        body.put("Message", "Server error occurred");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
                    }
                    // Generate JWT token
                    String token = JWT.create().withSubject(loginRequest.getEmail()).withClaim("name", response.getBody().getName() != null ? response.getBody().getName() : "").withClaim("last_name", response.getBody().getLastName() != null ? response.getBody().getLastName() : "").withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXP)).sign(Algorithm.HMAC256(SECRET_KEY));

                    Map<String, String> body = new HashMap<>();
                    body.put("token", token);

                    return ResponseEntity.status(HttpStatus.OK).body(body);
                }
                catch (HttpClientErrorException e) {
                    return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
                }
            }
            else
            {
                Map<String, String> body = new HashMap<>();
                body.put("Message","User not exists");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
            }

    }
}
