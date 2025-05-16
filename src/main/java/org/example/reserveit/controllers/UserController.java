package org.example.reserveit.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.reserveit.dto.UserDto;
import org.example.reserveit.dto.requests.UpdateUserRequest;
import org.example.reserveit.dto.requests.UserRequest;
import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.exceptions.EmailAlreadyExistException;
import org.example.reserveit.exceptions.UserNotFoundException;
import org.example.reserveit.models.User;
import org.example.reserveit.services.UserService;
import org.example.reserveit.utils.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create a new user",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict - Email already exists"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody @Valid UserRequest request)
    throws EmailAlreadyExistException {

        User user = userService.createUser(request.getName(), request.getEmail(),
                request.getPhoneNumber(), request.getPassword(), request.getType());

        UserDto userDto = UserDto.from(user);

        ApiResponse<UserDto> response = ResponseBuilder.created( "User created successfully", userDto);

        return ResponseEntity.status(CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable("id") Long id) throws UserNotFoundException {
        User user = userService.getUser(id);

        UserDto userDto = UserDto.from(user);

        ApiResponse<UserDto> response = ResponseBuilder.ok("User fetched successfully", userDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable("id") Long id,
                                                           @RequestBody UpdateUserRequest request) throws UserNotFoundException {
        User user = userService.updateUser(id, request.getName(), request.getEmail(), request.getPhoneNumber(), request.getType());

        UserDto userDto = UserDto.from(user);

        ApiResponse<UserDto> response = ResponseBuilder.ok("User details updated successfully", userDto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> deleteUser(@PathVariable("id") Long id)
        throws UserNotFoundException {

        User user = userService.deleteUser(id);

        UserDto userDto = UserDto.from(user);

        ApiResponse<UserDto> response = ResponseBuilder.ok("User deleted successfully", userDto);

        return ResponseEntity.status(OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers() {

        List<User> users = userService.getUsers();

        List<UserDto> userDtos = UserDto.from(users);

        ApiResponse<List<UserDto>> response = ResponseBuilder.ok("Users fetched successfully", userDtos, userDtos.size());
        return ResponseEntity.ok(response);
    }
}
