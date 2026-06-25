package com.eventhub.service;

import com.eventhub.dto.UserRequestDTO;
import com.eventhub.dto.UserResponseDTO;
import com.eventhub.dto.UpdateUserDTO;
import com.eventhub.entity.Role;
import com.eventhub.entity.User;
import com.eventhub.exception.custom.EmailAlreadyExistsException;
import com.eventhub.exception.custom.UserNotFoundException;
import com.eventhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventhub.dto.UpdateProfileImageDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDTO save(UserRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user = User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole() != null ? dto.getRole() : Role.USER)
                .registrationDate(LocalDateTime.now())
                .profileImage("/images/default-profile.png")
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponseDTO(savedUser);
    }

   public void deleteUser(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);

    if (user.getRole() == Role.ADMIN) {
        throw new IllegalArgumentException("Admin user cannot be deleted");
    }

    userRepository.delete(user);
}

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public UserResponseDTO updateMyProfileImage(UpdateProfileImageDTO dto) {

    User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

    user.setProfileImage(dto.getProfileImage());

    User savedUser = userRepository.save(user);

    return mapToResponseDTO(savedUser);
}

public UserResponseDTO updateMe(UpdateUserDTO dto) {

    User user = (User) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

    if (dto.getName() != null && !dto.getName().isBlank()) {
        user.setName(dto.getName());
    }

    if (dto.getSurname() != null && !dto.getSurname().isBlank()) {
        user.setSurname(dto.getSurname());
    }

    if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
        if (!dto.getEmail().equals(user.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        user.setEmail(dto.getEmail());
    }

    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    if (dto.getProfileImage() != null && !dto.getProfileImage().isBlank()) {
        user.setProfileImage(dto.getProfileImage());
    }

    User savedUser = userRepository.save(user);

    return mapToResponseDTO(savedUser);
}    

private UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .registrationDate(user.getRegistrationDate())
                .role(user.getRole())
                .build();
    }
}