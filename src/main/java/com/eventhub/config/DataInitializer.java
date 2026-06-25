package com.eventhub.config;

import com.eventhub.entity.Role;
import com.eventhub.entity.User;
import com.eventhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // ADMIN
        if(userRepository.findByEmail("admin@eventhub.com").isEmpty()) {

            User admin = new User();

            admin.setName("Admin");
            admin.setSurname("EventHub");
            admin.setEmail("admin@eventhub.com");

            admin.setPassword(
                    passwordEncoder.encode("admin123")
            );
            admin.setProfileImage("/images/default-profile.png");
            admin.setRegistrationDate(java.time.LocalDateTime.now());

            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("ADMIN CREATED");
        }

        // ORGANIZER
        if(userRepository.findByEmail("organizer@eventhub.com").isEmpty()) {

            User organizer = new User();

            organizer.setName("Organizer");
            organizer.setSurname("EventHub");
            organizer.setEmail("organizer@eventhub.com");

            organizer.setPassword(
                    passwordEncoder.encode("organizer123")
            );
            organizer.setProfileImage("/images/default-profile.png");
            organizer.setRegistrationDate(java.time.LocalDateTime.now());
            organizer.setRole(Role.ORGANIZER);

            userRepository.save(organizer);

            System.out.println("ORGANIZER CREATED");
        }
    }
}