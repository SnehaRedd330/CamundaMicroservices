package com.mudynamics.mudynamicsloginservice.security;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.mudynamics.mudynamicsloginservice.exceptions.JWTException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Created by SJ00495527.
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = Logger.getLogger(JwtTokenProvider.class.getName());

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPrincipal.getUsername()));
        claims.put("auth",userPrincipal.getAuthorities());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
        		 .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return (claims.getSubject());
    }

    public boolean validateToken(String authToken){
       boolean status=Boolean.FALSE;
    	try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            status= Boolean.TRUE;
        } catch (SignatureException ex) {
            logger.log(Level.SEVERE,"Invalid JWT signature",ex);
            throw new SignatureException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
        	 logger.log(Level.SEVERE,"Invalid JWT token",ex);
            throw new MalformedJwtException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        	 logger.log(Level.SEVERE,"Expired JWT token",ex);
            throw new ExpiredJwtException(null,null,"Expired JWT token");
        } catch (UnsupportedJwtException ex) {
        	 logger.log(Level.SEVERE,"Unsupported JWT token",ex);
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
        	 logger.log(Level.SEVERE,"JWT claims string is empty.",ex);
            throw new IllegalArgumentException("JWT claims string is empty.");
        }catch(Exception e){
        	 logger.log(Level.SEVERE,"JWT token related exception.",e);
        	throw new JWTException("JWT token related exception");
        }
        return status;
    }
}