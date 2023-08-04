package com.bike.bikeproject.repository;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.entity.*;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired DestinationRepository destinationRepository;

    @Autowired BikeStationRepository bikeStationRepository;

    @PersistenceContext EntityManager em;

    private BikeStation bikeStation;
    private Cafe cafe;
    private Restaurant restaurant;
    private Travel travel1;
    private Travel travel2;
    private Travel travel3;

    @BeforeEach
    public void setUp() {
        BikeStation bs = BikeStation.builder()
                .id(9734)
                .stationName("Test station")
                .latitude(11d).longitude(11d)
                .build();
        bikeStation = bikeStationRepository.save(bs);
        Cafe c = Cafe.builder()
                .ranking(1)
                .name("Test Cafe")
                .longitude(11d).latitude(11d)
                .bikeStation(bikeStation)
                .build();
        Restaurant r = Restaurant.builder()
                .ranking(1)
                .name("Test Restaurant")
                .longitude(11d).latitude(11d)
                .build();
        Travel t1 = Travel.builder()
                .ranking(1)
                .name("Test Travel")
                .latitude(111d).longitude(111d)
                .bikeStation(bikeStation)
                .category(TravelType.HISTORY)
                .time(3f)
                .build();
        // travel2 는 연결된 BikeStation 엔티티가 없는 것으로 가정
        Travel t2 = Travel.builder()
                .ranking(1)
                .name("Test Travel")
                .latitude(111d).longitude(111d)
                .category(TravelType.HISTORY)
                .time(3f)
                .build();
        Travel t3 = Travel.builder()
                .ranking(1)
                .name("Test Travel")
                .latitude(111d).longitude(111d)
                .bikeStation(bikeStation)
                .category(TravelType.HISTORY)
                .time(3f)
                .build();
        cafe = destinationRepository.save(c);
        restaurant = destinationRepository.save(r);
        travel1 = destinationRepository.save(t1);
        travel2 = destinationRepository.save(t2);
        travel3 = destinationRepository.save(t3);
    }

    @Test
    @DisplayName("deleteAllByDtype, findAllByDtype 메소드 테스트")
    public void deleteAllFindAllTest() {
        // given: @BeforeEach setUp()
        // when:
        List<Destination> cafes = destinationRepository.findAllByDtype("C");
        List<Destination> restaurants = destinationRepository.findAllByDtype("R");
        List<Destination> travels = destinationRepository.findAllByDtype("T");
        destinationRepository.deleteAllByDtype("C");
        destinationRepository.deleteAllByDtype("R");
        destinationRepository.deleteAllByDtype("T");
        List<Destination> cafes2 = destinationRepository.findAllByDtype("C");
        List<Destination> restaurants2 = destinationRepository.findAllByDtype("R");
        List<Destination> travels2 = destinationRepository.findAllByDtype("T");
        // then:
        assertEquals(1, cafes.size());
        assertEquals(1, restaurants.size());
        assertEquals(3, travels.size());
        assertEquals(0, cafes2.size());
        assertEquals(0, restaurants2.size());
        assertEquals(0, travels2.size());
    }

    @Test
    @DisplayName("여행지 목록 조회 테스트")
    public void getDTOListTest() {
        // given:
        List<Long> listOfDestinationIds = List.of(travel1.getId(), travel2.getId(), travel3.getId());
        // when:
        List<DestinationDTO> result = destinationRepository.getDTOList(listOfDestinationIds);
        // then:
        assertEquals(3, result.size());
    }

}
