package com.easycommerce.user;

import com.easycommerce.response.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        UserDTO user = modelMapper.map(userService.getLoggedInUser(), UserDTO.class);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<APIResponse> deleteUser() {
        String username = userService.deleteUser();
        APIResponse response = new APIResponse(String.format("Successfully deleted user with username: %s", username), new Date());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
