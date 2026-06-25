package com.eventhub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
//categorie degli eventi (musica, sport, arte, ecc.)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}