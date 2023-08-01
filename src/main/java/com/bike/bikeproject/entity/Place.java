package com.bike.bikeproject.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "place")
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Place {

    @Column(name="dtype", insertable = false, updatable = false)
    protected String dtype;

    @Id @Column(name = "place_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "ranking", nullable = false)
    private int ranking;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "time", nullable = false)
    private float time;

    @OneToOne
    @JoinColumn(name = "station_id")
    private BikeStation bikeStation;

    protected Place(double latitude, double longitude, int ranking,
                    String name, float time, BikeStation bikeStation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ranking = ranking;
        this.name = name;
        this.time = time;
        this.bikeStation = bikeStation;
    }
}
