package com.eventhub.service;

import com.eventhub.dto.AuthResponseDTO;
import com.eventhub.dto.LoginRequestDTO;
import com.eventhub.dto.RegisterDTO;
import com.eventhub.entity.Role;
import com.eventhub.entity.User;
import com.eventhub.exception.custom.EmailAlreadyExistsException;
import com.eventhub.exception.custom.InvalidCredentialsException;
import com.eventhub.exception.custom.UserNotFoundException;
import com.eventhub.repository.UserRepository;
import com.eventhub.security.JwtService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    String defaultImage = "/images/default-profile.png";

    public AuthResponseDTO register(RegisterDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        User user = User.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .registrationDate(LocalDateTime.now())
                .profileImage(defaultImage)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponseDTO(token);
    }
}