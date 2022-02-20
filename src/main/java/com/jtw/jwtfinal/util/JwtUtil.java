package com.jtw.jwtfinal.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;
    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    public String getUserNamefromToken( String token){
        return getClaimFromToken(token,Claims::getSubject);
    }
    private <T> T getClaimFromToken(String token, Function<Claims,T> claimResolver){
        final Claims claims=getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails detailsService){
       String userName= getUserNamefromToken(token);
       return (userName.equals(detailsService.getUsername()) && !isTokenExpired(token));
    }
    private  Boolean isTokenExpired(String token){
        final Date expiration= getExpirationDateFromToken(token);
        return  expiration.before(new Date());
    }
    private Date getExpirationDateFromToken(String token){
        return  getClaimFromToken(token,Claims::getExpiration);
    }

    public  String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();


    }
}
