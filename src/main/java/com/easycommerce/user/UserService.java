package com.easycommerce.user;

import com.easycommerce.auth.JwtDTO;
import com.easycommerce.auth.LoginDTO;
import com.easycommerce.auth.RegisterDTO;

public interface UserService {

    JwtDTO login(LoginDTO loginDTO);
    UserDTO addUser(RegisterDTO registerDTO);
    User getLoggedInUser();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
