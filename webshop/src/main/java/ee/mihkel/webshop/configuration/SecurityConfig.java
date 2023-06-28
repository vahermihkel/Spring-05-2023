package ee.mihkel.webshop.configuration;

import ee.mihkel.webshop.security.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//    private final TokenParser tokenParser;
//
//    public SecurityConfig(TokenParser tokenParser) {
//        this.tokenParser = tokenParser;
//    }
    @Autowired
    TokenParser tokenParser;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .cors().and().headers().xssProtection().disable().and()
                .csrf().disable()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("product").permitAll()
                        .requestMatchers("public-products").permitAll()
                        .requestMatchers("categories").permitAll()
                        .requestMatchers("parcel-machines/**").permitAll()
                        .requestMatchers("payment/**").permitAll()
                        .requestMatchers("login").permitAll()
                        .requestMatchers("signup").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenParser, BasicAuthenticationFilter.class).build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//
//        return httpSecurity
//                .authorizeHttpRequests()
//                .antMatchers("product").permitAll()
//                .antMatchers("categories").permitAll()
//                .anyRequest().authenticated()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .addFilterBefore(tokenParser, BasicAuthenticationFilter.class).build();
//    }
}
