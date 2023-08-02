package com.bike.bikeproject.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
@DiscriminatorValue("T")
public class Travel extends Place {

    @Enumerated(EnumType.STRING)
    private TravelType category;

    @Builder
    public Travel(double latitude, double longitude, int ranking,
                      String name, float time, TravelType category, BikeStation bikeStation) {
        super(latitude, longitude, ranking, name, time, bikeStation);
        this.category = category;
    }

}