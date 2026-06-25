package com.eventhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname; 
    
    @Column(length = 1000)
    private String profileImage;

    private LocalDateTime registrationDate;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

   

    @Enumerated(EnumType.STRING)
    private Role role;

    // EVENTS CREATI
    @JsonIgnore
    @OneToMany(mappedBy = "creator")
    private List<Event> createdEvents;

    //BOOKINGS
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    //REVIEWS
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    //NOTIFICATIONS
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;
    
    //SECURITY
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}