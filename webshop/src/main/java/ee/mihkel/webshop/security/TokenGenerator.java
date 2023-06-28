package ee.mihkel.webshop.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    public String generateToken() {

        return Jwts.builder()
                        .signWith(SignatureAlgorithm.HS512, "super-secrect-key")
                        .setIssuer("Mihkel's webshop")
                        .compact();
    }
}
