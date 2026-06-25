package com.eventhub.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data

@EqualsAndHashCode(callSuper = true)

public class PhysicalEvent extends Event {

    private String address;

    private String city;

    private String country;

    private Double latitude;

    private Double longitude;
}