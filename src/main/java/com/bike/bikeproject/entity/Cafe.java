package com.bike.bikeproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("C")
public class Cafe extends Place {

    @Builder
    public Cafe(double latitude, double longitude, int ranking,
                String name, float time, BikeStation bikeStation) {
        super(latitude, longitude, ranking, name, time, bikeStation);
    }

}