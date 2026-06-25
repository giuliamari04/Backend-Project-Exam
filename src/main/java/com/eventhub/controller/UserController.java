package com.eventhub.controller;

import com.eventhub.dto.UserRequestDTO;
import com.eventhub.dto.UserResponseDTO;
import com.eventhub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.eventhub.dto.UpdateProfileImageDTO;
import com.eventhub.dto.UpdateUserDTO;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/me/image")
    public UserResponseDTO updateMyProfileImage(
            @RequestBody @Valid UpdateProfileImageDTO dto) {
        return userService.updateMyProfileImage(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponseDTO save(@RequestBody @Valid UserRequestDTO dto) {
        return userService.save(dto);
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @PutMapping("/me")
    public UserResponseDTO updateMe(@RequestBody @Valid UpdateUserDTO dto) {
        return userService.updateMe(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}