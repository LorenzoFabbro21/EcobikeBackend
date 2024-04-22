package microservice.authenticationservice.service;

import microservice.authenticationservice.dto.*;
import microservice.authenticationservice.model.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.oauth2.core.user.*;

public interface AuthenticationService {

    void googleLogin(OAuth2User auth);

    void signup(UserDetails userDetails);

    ResponseEntity<?> login(LoginRequest loginRequest);
}
