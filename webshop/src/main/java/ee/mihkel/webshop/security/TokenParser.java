package ee.mihkel.webshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class TokenParser extends BasicAuthenticationFilter {

    public TokenParser(@Lazy AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");
        log.info("Authorization: {}",header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");
            Claims claims = Jwts.parser()
                    .setSigningKey("super-secrect-key")
                    .parseClaimsJws(token)
                    .getBody();
            String email = claims.getSubject();
            log.info("Email: {}", email);

            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
//        if (header != null && header.equals("Bearer 1234")) {
//            Authentication authentication = new UsernamePasswordAuthenticationToken("s@s.ee", null, null);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }

        super.doFilterInternal(request, response, chain);
    }
}
