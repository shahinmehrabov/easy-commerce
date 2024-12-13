package com.easycommerce.user;

import com.easycommerce.auth.JwtDTO;
import com.easycommerce.auth.LoginDTO;
import com.easycommerce.auth.RegisterDTO;
import com.easycommerce.auth.jwt.JwtTokenProvider;
import com.easycommerce.exception.APIException;
import com.easycommerce.exception.ResourceAlreadyExistsException;
import com.easycommerce.exception.ResourceNotFoundException;
import com.easycommerce.user.role.Role;
import com.easycommerce.user.role.RoleName;
import com.easycommerce.user.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public JwtDTO login(LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            String jwtToken = jwtTokenProvider.generateToken(loginDTO.getUsername());

            return new JwtDTO(jwtToken, jwtTokenProvider.getExpirationInMs());
        } catch (AuthenticationException e) {
            throw new APIException("Bad credentials");
        }
    }

    @Override
    public UserDTO addUser(RegisterDTO registerDTO) {
        if (existsByUsername(registerDTO.getUsername()))
            throw new ResourceAlreadyExistsException("User", "username", registerDTO.getUsername());

        if (existsByEmail(registerDTO.getEmail()))
            throw new ResourceAlreadyExistsException("User", "email", registerDTO.getEmail());

        User user = modelMapper.map(registerDTO, User.class);
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", String.valueOf(RoleName.ROLE_USER)));
        Set<Role> roles = Set.of(role);

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User user = getLoggedInUser();

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public String deleteUser() {
        User user = getLoggedInUser();
        userRepository.delete(user);
        return user.getUsername();
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
