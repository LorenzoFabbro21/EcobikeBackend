package microservice.apigateway.filter;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.*;
import com.auth0.jwt.exceptions.*;
import org.springframework.cloud.gateway.filter.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.http.server.reactive.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;
import reactor.core.publisher.*;

import java.util.*;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
	private final String SECRET_KEY = "secret";
	private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
	private List<String> UriNotProtected = Arrays.asList("/api/adrent","/api/adrent/bikes", "/api/adsell","/api/adsell/bikes", "/api/bike/**","/api/bike", "/api/private/email/","/api/dealer/email/", "/auth/**","/api/shop","/api/shop/","/api/adrent/user/{id}/bikes" ,"/api/adsell/user/{id}/bikes");
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		String requestPath = exchange.getRequest().getPath().value();
		HttpMethod requestMethod = exchange.getRequest().getMethod();

		if (NotRequiresAuthentication(requestPath,requestMethod)) {
			return chain.filter(exchange); // Non richiede autenticazione, passa semplicemente la richiesta
		}

		try {

			String token = extractJwtFromRequest(exchange.getRequest());
			if (token == null) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}

			JWT.require(algorithm).build().verify(token);
			return chain.filter(exchange);
		} catch (JWTVerificationException e) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
	}

	private boolean NotRequiresAuthentication(String requestPath,HttpMethod requestMethod) {
		// Verifica se l'URI è contenuta nella lista della Uri che non richiedono autenticazione
		if (UriNotProtected.stream().anyMatch(uri -> requestPath.startsWith(uri))){

			if (requestPath.contains("auth")) {
				return true;
			}
			else {
				return requestMethod == HttpMethod.GET;
			}
		}
		else {
			return false;
		}
	}

	private String extractJwtFromRequest(ServerHttpRequest request) {
		List<String> authHeaders = request.getHeaders().get("Authorization");
		if (authHeaders != null && !authHeaders.isEmpty()) {
			String authHeader = authHeaders.get(0);
			String[] parts = authHeader.split(" ");
			if (parts.length == 2 && "Bearer".equals(parts[0])) {
				return parts[1];
			}
		}
		return null;
	}

	@Override
	public int getOrder() {
		return -1;
	}
}