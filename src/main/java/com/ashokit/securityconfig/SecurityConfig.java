package com.ashokit.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ashokit.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(JwtAuthFilter jwtAuthFilter,
                          AuthenticationProvider authenticationProvider,PasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.passwordEncoder=passwordEncoder;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**").permitAll()
//                        .requestMatchers("/api/laptops/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/h2-console/**").disable() // Allow H2 console from CSRF
//                )
//                .headers(headers -> headers
//                        .frameOptions(frame -> frame.sameOrigin()) // Enable frames for H2
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**").permitAll()
//                        .requestMatchers("/payment-page").permitAll()
//                        .requestMatchers("/h2-console/**").permitAll() // ✅ H2 console access
//                        .requestMatchers("/api/laptops/**").hasRole("ADMIN")
//                         .requestMatchers(
//                                "/company/**"
//                                
//                            ).hasRole("COMPANY")
//                         
//                        .anyRequest().authenticated()
//                      
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/razor/**").disable()) // Disable CSRF for testing; change for production
//                .headers(headers -> headers
//                        .frameOptions(frame -> frame.sameOrigin())
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**").permitAll()
//                     
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .requestMatchers("/api/laptops/**").hasRole("ADMIN")
//                        .requestMatchers("/company/**").hasRole("COMPANY")
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/razor/**").disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/h2-console/**").permitAll()

                    .requestMatchers("/razor/payment-page").permitAll()
                    .requestMatchers("/razor/payment/callback").permitAll()
                    .requestMatchers(HttpMethod.POST, "/razor/payment/order/**").permitAll()

                    .requestMatchers("/api/laptops/**").hasRole("ADMIN")
                    .requestMatchers("/company/**").hasRole("COMPANY")

                    .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    
}
