package com.eventhub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data

// importantissima ! dice a hibernate, usa una tabella base Event + tabelle
// separate per OnlineEvent e PhysicalEvent
@Inheritance(strategy = InheritanceType.JOINED)

public abstract class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private LocalDateTime date;

    private Integer capacity; // capacità massima dell'enevento (es. 500 zoom, 100 persone in sala)

    private Integer maxParticipants; // numero massimo di partecipanti che possono prenotare (es. 50 posti per 100
                                     // per sala oppure 50 utenti per 500 per zoom)

    // creator organizer/admin
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    // categoria
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private List<Review> reviews;
}