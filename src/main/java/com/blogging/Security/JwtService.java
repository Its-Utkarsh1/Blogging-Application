package com.blogging.Security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

//This is to perform operation with jwt

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 15*60*1000;
    private static final String SECRET = "mySuperSecureJwtSecretKeyThatIsAtLeast32BytesLong!";


    //GENERATE TOKEN
    public String generateToken(String username){

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)),SignatureAlgorithm.HS256)
                .compact();
    }


    //GET USERNAME FORM TOKEN
    public String getUsername(String token){

        return Jwts.parserBuilder().setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8)).build()
                .parseClaimsJws(token).getBody().getSubject();
    }


    //VALIDATE TOKEN
    public boolean validateToken(String token){

        if(this.isTokenExpired(token))
            return false;

        try{
            Jwts.parserBuilder().setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8)).build().parseClaimsJws(token);
            return true;

        }catch (JwtException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build()
                .parseClaimsJws(token).getBody().getExpiration();

        return expiration.before(new Date());
    }


}
