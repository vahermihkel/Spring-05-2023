package ee.mihkel.webshop.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class TokenGenerator {

    public String generateToken(String email, boolean isAdmin) {

        Calendar expiration = Calendar.getInstance();
        expiration.setTime(new Date());
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "super-secrect-key")
                .setIssuer("Mihkel's webshop")
                .setSubject(email)
                .setExpiration(expiration.getTime())
                .setAudience(isAdmin ? "ADMIN" : "USER")
                .compact();
    }
}
