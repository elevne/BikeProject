package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.entity.Cafe;
import com.bike.bikeproject.entity.Restaurant;
import com.bike.bikeproject.entity.Travel;
import com.bike.bikeproject.entity.TravelType;
import com.bike.bikeproject.repository.PlaceRepository;
import com.bike.bikeproject.util.PlaceBatchUtil;
import com.bike.bikeproject.util.PlaceType;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.OneToOne;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SimplePlaceBatchUtil implements PlaceBatchUtil {

    private final PlaceRepository repository;

    @Override
    public void batchInsert(PlaceType placeType) {}

    public List<List<String>> readFile(PlaceType placeType) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(placeType.getFilepath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException();  // todo: Exception 적절한 것으로 던지기
        }
        return records;
    }

    private Cafe listToCafe(List<String> dataList) {
        return Cafe.builder()
                .latitude(Double.parseDouble(dataList.get(1)))
                .longitude(Double.parseDouble(dataList.get(2)))
                .ranking(Integer.parseInt(dataList.get(5)))
                .name(dataList.get(6))
                .time(1.0f)
                .bikeStation(null)  // todo: nearest 값으로 BikeStation 정보 가져오는 메소드 필요
                .build();
    }

    private Restaurant listToRestaurant(List<String> dataList) {
        return Restaurant.builder()
                .latitude(Double.parseDouble(dataList.get(1)))
                .longitude(Double.parseDouble(dataList.get(2)))
                .ranking(Integer.parseInt(dataList.get(6)))
                .name(dataList.get(4))
                .time(1.0f)
                .bikeStation(null)
                .build();
    }

    private Travel listToTravel(List<String> dataList) {
        return Travel.builder()
                .latitude(Double.parseDouble(dataList.get(1)))
                .longitude(Double.parseDouble(dataList.get(2)))
                .ranking(Integer.parseInt(dataList.get(4)))
                .name(dataList.get(7))
                .time(Float.parseFloat(dataList.get(6)))
                .category(TravelType.getTravelType(dataList.get(5)))
                .bikeStation(null)
                .build();
    }

}
