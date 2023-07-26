package io.gnt.bmt.commons.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.naming.AuthenticationException;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.io.Decoders;


public class JwtUtil {

    private String jwtSecret;

    public JwtUtil(String secret){
        this.jwtSecret = secret;
    }


    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();

    }


    public boolean validateToken(final String token) throws AuthenticationException {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);

            return true;
        } catch (MalformedJwtException ex) {
            throw new AuthenticationException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new AuthenticationException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationException("JWT claims string is empty.");
        }
    }


    public String generateToken(String id) {

        return Jwts.builder()
                .setSubject((id))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + (1000 * 60 * 20)))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

}
