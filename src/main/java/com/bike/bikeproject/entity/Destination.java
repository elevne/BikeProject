package com.bike.bikeproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "destination")
@SequenceGenerator(
        name = "destination_seq_generator",
        initialValue = 1,
        allocationSize = 150
)
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Destination {

    @Column(name="dtype", insertable = false, updatable = false)
    protected String dtype;

    @Id @Column(name = "destination_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "destination_seq_generator")
    private Long id;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "ranking", nullable = false)
    private int ranking;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "station_id")
    private BikeStation bikeStation;

    protected Destination(double latitude, double longitude, int ranking,
                          String name, BikeStation bikeStation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ranking = ranking;
        this.name = name;
        this.bikeStation = bikeStation;
    }
}
