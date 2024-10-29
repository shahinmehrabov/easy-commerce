package com.easycommerce.user;

import com.easycommerce.auth.SignUpRequest;

public interface UserService {

    UserDTO addUser(SignUpRequest signUpRequest);
    User getLoggedInUser();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
