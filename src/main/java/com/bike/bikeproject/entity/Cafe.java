package com.bike.bikeproject.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("C")
public class Cafe extends Destination {

    @Builder
    public Cafe(double latitude, double longitude, int ranking,
                String name, BikeStation bikeStation) {
        super(latitude, longitude, ranking, name, bikeStation);
    }

}