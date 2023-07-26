package io.gnt.bmt.apigateway.filter;


import io.gnt.bmt.commons.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;


import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {


    private JwtUtil jwtUtil;


    public JwtAuthenticationFilter(@Value("${security.jwt.secret}") String secret) {
        super(Config.class);
        jwtUtil = new JwtUtil(secret);
    }

    private boolean isAuthorizationValid(String authorizationHeader) throws AuthenticationException {

        return jwtUtil.validateToken(authorizationHeader);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }


    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            try {

                if (!request.getHeaders().containsKey("Authorization")) {
                    return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
                }

                String jwtToken = this.getAuthHeader(request);

                if (!this.isAuthorizationValid(jwtToken)) {
                    return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
                }

                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().
                        header("claims", jwtUtil.getClaims(jwtToken).toString()).
                        build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());


            } catch(Exception e){
                return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
            }
        };
    }


    public static class Config {

    }

}


