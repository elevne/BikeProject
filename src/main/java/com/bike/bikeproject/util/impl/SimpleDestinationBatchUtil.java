package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.entity.*;
import com.bike.bikeproject.repository.BikeStationRepository;
import com.bike.bikeproject.repository.DestinationRepository;
import com.bike.bikeproject.util.DestinationBatchUtil;
import com.bike.bikeproject.util.PlaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SimpleDestinationBatchUtil implements DestinationBatchUtil {

    @PersistenceContext
    EntityManager em;

    private final DestinationRepository destinationRepository;

    private final BikeStationRepository bikeStationRepository;

    /**
     * 여행지 정보가 최신화 되면 txt, csv 파일 등으로 /data 경로에 저장,
     * DB 에 있는 정보와 동기화 해줄 수 있다.
     * @param placeType Cafe, Restaurant, Travel 중 하나의 여행지 타입
     */
    @Override
    @Transactional
    public void batchInsert(PlaceType placeType) {
        destinationRepository.deleteAllByDtype(placeType.getDtype());
        List<List<String>> dataFile = readFile(placeType);

        List<Destination> batch = new ArrayList<>();
        for (int i = 1; i < dataFile.size(); i++) {  // 첫 번째 행은 컬럼명 행이기 때문에 i 를 1 부터 시작
            Destination destination = listToDestination(dataFile.get(i), placeType);
            batch.add(destination);
        }
        destinationRepository.saveAll(batch);
    }

    private List<List<String>> readFile(PlaceType placeType) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(placeType.getFilepath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException();  // todo: 적절한 예외로 바꿔 던지기
        }
        return records;
    }

    private Destination listToDestination(List<String> dataList, PlaceType placeType) {
        switch (placeType) {
            case CAFE:
                return listToCafe(dataList);
            case RESTAURANT:
                return listToRestaurant(dataList);
            case TRAVEL:
                return listToTravel(dataList);
            default:
                throw new RuntimeException();  // todo: 적절한 예외로 바꿔 던지기
        }
    }

    private Cafe listToCafe(List<String> dataList) {
        return Cafe.builder()
                .latitude(Double.parseDouble(dataList.get(1)))
                .longitude(Double.parseDouble(dataList.get(2)))
                .ranking(Integer.parseInt(dataList.get(5)))
                .name(dataList.get(6).replaceAll("\"", ""))
                .bikeStation(bikeStationRepository.findFirstByStationNameContaining(dataList.get(4).replaceAll("\"", "")))
                .build();
    }

    private Restaurant listToRestaurant(List<String> dataList) {
        return Restaurant.builder()
                .latitude(Double.parseDouble(dataList.get(1)))
                .longitude(Double.parseDouble(dataList.get(2)))
                .ranking(Integer.parseInt(dataList.get(6)))
                .name(dataList.get(4).replaceAll("\"", ""))
                .bikeStation(bikeStationRepository.findFirstByStationNameContaining(dataList.get(5).replaceAll("\"", "")))
                .build();
    }

    private Travel listToTravel(List<String> dataList) {
        return Travel.builder()
                .latitude(Double.parseDouble(dataList.get(1)))
                .longitude(Double.parseDouble(dataList.get(2)))
                .ranking(Integer.parseInt(dataList.get(4)))
                .name(dataList.get(7).replaceAll("\"", ""))
                .time(Float.parseFloat(dataList.get(6)))
                .category(TravelType.getTravelType(dataList.get(5)))
                .bikeStation(bikeStationRepository.findFirstByStationNameContaining(dataList.get(3).replaceAll("\"", "")))
                .build();
    }


}
