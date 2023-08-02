package com.bike.bikeproject.util.impl;

import com.bike.bikeproject.entity.Place;
import com.bike.bikeproject.repository.PlaceRepository;
import com.bike.bikeproject.util.PlaceBatchUtil;
import com.bike.bikeproject.util.PlaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
public class SimplePlaceBatchUtil implements PlaceBatchUtil {

    @PersistenceContext
    EntityManager em;

    private final PlaceRepository repository;

    @Override
    @Transactional
    public void batchInsert(PlaceType placeType) {
        repository.deleteAllByDtype(placeType.getDtype());
        List<List<String>> dataFile = readFile(placeType);

        List<Place> batch = new ArrayList<>();
        for (int i = 1; i < dataFile.size(); i++) {  // 첫 번째 행은 컬럼명 행이기 때문에 i 를 1 부터 시작
            Place place = placeType.getFunction().apply(dataFile.get(i));
            batch.add(place);
            if (i % 1000 == 0) {
                repository.saveAll(batch);
                em.flush();
                em.clear();
                batch.clear();
            }
        }
        repository.saveAll(batch);
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
            throw new RuntimeException();  // todo: Exception 적절한 것으로 던지기
        }
        return records;
    }
}
