package com.easycommerce.user;

import com.easycommerce.auth.JwtDTO;
import com.easycommerce.auth.LoginDTO;
import com.easycommerce.auth.RegisterDTO;

public interface UserService {

    JwtDTO login(LoginDTO loginDTO);
    User getLoggedInUser();
    UserDTO addUser(RegisterDTO registerDTO);
    UserDTO updateUser(UserDTO userDTO);
    String deleteUser();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
