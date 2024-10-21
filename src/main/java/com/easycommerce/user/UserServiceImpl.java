package com.easycommerce.user;

import com.easycommerce.auth.SignUpRequest;
import com.easycommerce.exception.ResourceAlreadyExistsException;
import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.user.role.Role;
import com.easycommerce.user.role.RoleName;
import com.easycommerce.user.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO addUser(SignUpRequest signUpRequest) {
        if (existsByUsername(signUpRequest.getUsername()))
            throw new ResourceAlreadyExistsException("User", "username", signUpRequest.getUsername());

        if (existsByEmail(signUpRequest.getEmail()))
            throw new ResourceAlreadyExistsException("User", "email", signUpRequest.getEmail());

        User user = modelMapper.map(signUpRequest, User.class);
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", String.valueOf(RoleName.ROLE_USER)));
        Set<Role> roles = Set.of(role);

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
