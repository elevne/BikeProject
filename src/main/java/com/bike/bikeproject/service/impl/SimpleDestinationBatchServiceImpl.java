package com.bike.bikeproject.service.impl;

import com.bike.bikeproject.entity.*;
import com.bike.bikeproject.repository.BikeStationRepository;
import com.bike.bikeproject.repository.DestinationRepository;
import com.bike.bikeproject.service.SimpleDestinationBatchService;
import com.bike.bikeproject.util.DestinationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleDestinationBatchServiceImpl implements SimpleDestinationBatchService {

    private final DestinationRepository destinationRepository;

    private final BikeStationRepository bikeStationRepository;

    /**
     * 여행지 정보가 최신화 되면 txt, csv 파일 등으로 /data 경로에 저장,
     * DB 에 있는 정보와 동기화 해줄 수 있다.
     * @param destinationType Cafe, Restaurant, Travel 중 하나의 여행지 타입
     */
    @Override
    @Transactional
    public void batchInsert(DestinationType destinationType) throws IOException {
        destinationRepository.deleteAllByDtype(destinationType.getDtype());
        List<List<String>> dataFile = readFile(destinationType);

        List<Destination> batch = new ArrayList<>();
        for (int i = 1; i < dataFile.size(); i++) {  // 첫 번째 행은 컬럼명 행이기 때문에 i 를 1 부터 시작
            try {
                // txt 파일에서 정보를 읽어 Entity 로 매핑해주는 과정에서 오타 등의 이유로 예외가 발생할 수 있음
                Destination destination = listToDestination(dataFile.get(i), destinationType);
                batch.add(destination);
            } catch (IllegalArgumentException illegalArgumentException) {
                log.error("Insertion error at line " + String.valueOf(i+1) +
                            "of file (" + destinationType.getFilepath() + ")" +
                            " while mapping to entity");
            }
        }
        destinationRepository.saveAll(batch);
    }

    private List<List<String>> readFile(DestinationType destinationType) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(destinationType.getFilepath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (IOException ioException) {
            log.error("Exception raised while reading file " + destinationType.getFilepath());
            throw new IOException();
        }
        return records;
    }

    private Destination listToDestination(List<String> dataList, DestinationType destinationType) throws IllegalArgumentException {
        switch (destinationType) {
            case CAFE:
                return listToCafe(dataList);
            case RESTAURANT:
                return listToRestaurant(dataList);
            case TRAVEL:
                return listToTravel(dataList);
            default:
                throw new IllegalArgumentException(destinationType.name() + " is not configured in " + this.getClass().getSimpleName());
        }
    }

    private Cafe listToCafe(List<String> dataList) throws IllegalArgumentException {
        return Cafe.builder()
                .latitude(Double.parseDouble(dataList.get(1)))
                .longitude(Double.parseDouble(dataList.get(2)))
                .ranking(Integer.parseInt(dataList.get(5)))
                .name(dataList.get(6).replaceAll("\"", ""))
                .bikeStation(bikeStationRepository.findFirstByStationNameContaining(dataList.get(4).replaceAll("\"", "")))
                .build();
    }

    private Restaurant listToRestaurant(List<String> dataList) throws IllegalArgumentException {
        return Restaurant.builder()
                .latitude(Double.parseDouble(dataList.get(1)))
                .longitude(Double.parseDouble(dataList.get(2)))
                .ranking(Integer.parseInt(dataList.get(6)))
                .name(dataList.get(4).replaceAll("\"", ""))
                .bikeStation(bikeStationRepository.findFirstByStationNameContaining(dataList.get(5).replaceAll("\"", "")))
                .build();
    }

    private Travel listToTravel(List<String> dataList) throws IllegalArgumentException {
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
