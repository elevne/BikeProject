package com.bike.bikeproject.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorValue("T")
public class Travel extends Destination {

    @Enumerated(EnumType.STRING)
    private TravelType category;

    @Column(name = "time")
    private float time;

    @Builder
    public Travel(double latitude, double longitude, int ranking,
                      String name, float time, TravelType category, BikeStation bikeStation) {
        super(latitude, longitude, ranking, name, bikeStation);
        this.category = category;
        this.time = time;
    }

}