package com.eventhub.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data

@EqualsAndHashCode(callSuper = true)

public class OnlineEvent extends Event {

    private String meetingLink;
}