package com.bike.bikeproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlaceRepositoryTest {

    @Autowired PlaceRepository placeRepository;

    @PersistenceContext EntityManager em;

    @Test
    public void savePlacesTest() {
        // given: 각종 Place Entity 들

    }

}
