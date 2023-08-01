package com.bike.bikeproject.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("T")
public class Travel extends Place {

    @Enumerated(EnumType.STRING)
    private TravelType category;

}