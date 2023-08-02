package com.bike.bikeproject.repository;

import com.bike.bikeproject.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DestinationRepositoryTest {

    @Autowired
    DestinationRepository destinationRepository;

    @PersistenceContext EntityManager em;

    @Test
    @DisplayName("deleteAllByDtype, findAllByDtype 메소드 테스트")
    public void deleteAllFindAllTest() {
        // given
        Cafe cafe = Cafe.builder()
                .ranking(1).name("Test Cafe").longitude(11d).latitude(11d).build();
        Restaurant restaurant = Restaurant.builder()
                .ranking(1).name("Test Restaurant").longitude(11d).latitude(11d).build();
        Travel travel = Travel.builder()
                .ranking(1).name("Test Travel").latitude(111d).longitude(111d).category(TravelType.HISTORY).time(3f).build();
        destinationRepository.save(cafe);
        destinationRepository.save(restaurant);
        destinationRepository.save(travel);
        // when
        List<Destination> cafes = destinationRepository.findAllByDtype("C");
        List<Destination> restaurants = destinationRepository.findAllByDtype("R");
        List<Destination> travels = destinationRepository.findAllByDtype("T");
        destinationRepository.deleteAllByDtype("C");
        destinationRepository.deleteAllByDtype("R");
        destinationRepository.deleteAllByDtype("T");
        List<Destination> cafes2 = destinationRepository.findAllByDtype("C");
        List<Destination> restaurants2 = destinationRepository.findAllByDtype("R");
        List<Destination> travels2 = destinationRepository.findAllByDtype("T");
        // then
        assertEquals(1, cafes.size());
        assertEquals(1, restaurants.size());
        assertEquals(1, travels.size());
        assertEquals(0, cafes2.size());
        assertEquals(0, restaurants2.size());
        assertEquals(0, travels2.size());
    }

}
