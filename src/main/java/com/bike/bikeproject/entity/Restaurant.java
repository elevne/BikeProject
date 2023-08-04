package com.bike.bikeproject.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("R")
public class Restaurant extends Destination {

    @Builder
    public Restaurant(double latitude, double longitude, int ranking,
                String name, BikeStation bikeStation) {
        super(latitude, longitude, ranking, name, bikeStation);
    }

}