package microservice.authenticationservice.controller;
import lombok.*;
import lombok.extern.slf4j.*;
import microservice.authenticationservice.dto.*;
import microservice.authenticationservice.dto.User;
import microservice.authenticationservice.model.UserDetails;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import jakarta.servlet.http.*;
import microservice.authenticationservice.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.net.*;
import java.nio.charset.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:32000")
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private static final long TOKEN_EXP = 600000; // 10 minute
    private final String SECRET_KEY = "secret";

    private final AuthenticationService authService;
    @GetMapping("/google")
    public ResponseEntity<String> successLogin(Authentication auth ) {
        //return authService.googleLogin(auth);

        OAuth2User user = (OAuth2User) auth.getPrincipal();
        //make jwt token
        String token = JWT.create().withSubject(user.getAttribute("email")).withClaim("name", (String) user.getAttribute("given_name")).withClaim("last_name", (String) user.getAttribute("family_name")).withClaim("picture", (String) user.getAttribute("picture")).withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXP)).sign(Algorithm.HMAC256(SECRET_KEY));
        //send a message to the user service to create the user
        HttpStatusCode result = authService.googleLogin(user).getStatusCode();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", URLDecoder.decode("http://localhost:32000/authentication", StandardCharsets.UTF_8) + "?token=" + token);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDetails userDetails) {
        return authService.signup(userDetails);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);

    }



   // This method is called when the user is not successfully authenticated
    @GetMapping("/failureLogin")
    public ResponseEntity<String> failureLogin() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        String redirectUri = "http://localhost:32000/";

        // redirect to the client
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUri));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }




}
