package com.easycommerce.config;

import com.easycommerce.auth.jwt.AuthEntryPoint;
import com.easycommerce.auth.jwt.AuthTokenFilter;
import com.easycommerce.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPoint unauthorizedHandler;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest()
                                .authenticated()
                );

        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return httpSecurity.build();
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            Role roleUser = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_USER)));
            if (!userRepository.existsByUsername("user")) {
                User user = new User("user", "user@easycommerce.com", passwordEncoder().encode("userPassword"));
                userRepository.save(user);
            }

            Role roleSeller = roleRepository.findByName(RoleName.ROLE_SELLER)
                    .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_SELLER)));
            if (!userRepository.existsByUsername("seller")) {
                User seller = new User("seller", "seller@easycommerce.com", passwordEncoder().encode("sellerPassword"));
                userRepository.save(seller);
            }

            Role roleAdmin = roleRepository.findByName(RoleName.ROLE_SELLER)
                    .orElseGet(() -> roleRepository.save(new Role(RoleName.ROLE_SELLER)));
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User("admin", "admin@easycommerce.com", passwordEncoder().encode("adminPassword"));
                userRepository.save(admin);
            }

            userRepository.findByUsername("user").ifPresent(user -> {
                user.setRoles(Set.of(roleUser));
                userRepository.save(user);
            });

            userRepository.findByUsername("seller").ifPresent(seller -> {
                seller.setRoles(Set.of(roleSeller));
                userRepository.save(seller);
            });

            userRepository.findByUsername("admin").ifPresent(admin -> {
                admin.setRoles(Set.of(roleAdmin));
                userRepository.save(admin);
            });
        };
    }
}
