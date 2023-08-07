package com.bike.bikeproject.repository;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.entity.Destination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    @Modifying
    @Query("DELETE FROM Destination d WHERE d.dtype = :dtype")
    void deleteAllByDtype(@Param("dtype") String dtype);

    List<Destination> findAllByDtype(String dtype);

    /**
     * 클라이언트로부터 들르고자 하는 여행지들의 id 값을 받아서, 해당 여행지
     * 정보들을 DTO 로 조회한다.
     * @param destinations 클라이언트로부터 전달받는 들르고자하는 여행지들의 id 값
     * @return 해당 여행지 정보들을 담은 DTO 리스트
     */
    @Query("SELECT " +
            "new com.bike.bikeproject.dto.DestinationDTO(d.name, d.latitude, d.longitude, b.id, b.stationName, 0) " +
            "FROM Destination d LEFT OUTER JOIN BikeStation b ON b = d.bikeStation " +
            "WHERE d.id IN :destinations AND d.dtype = 'T'")
    List<DestinationDTO> getDTOList(@Param("destinations") List<Long> destinations);

}
