package com.easycommerce.auth;

import com.easycommerce.auth.jwt.JwtTokenProvider;
import com.easycommerce.auth.login.LoginRequest;
import com.easycommerce.auth.login.LoginResponse;
import com.easycommerce.exception.APIException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            String jwtToken = jwtTokenProvider.generateToken(loginRequest.getUsername());

            return new LoginResponse(jwtToken, jwtTokenProvider.getExpirationInMs());
        } catch (AuthenticationException e) {
            throw new APIException("Bad credentials");
        }
    }
}
