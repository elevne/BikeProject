package com.bike.bikeproject.service;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.vo.SuggestedRouteVO;
import com.bike.bikeproject.entity.Travel;
import com.bike.bikeproject.repository.DestinationRepository;
import com.bike.bikeproject.util.DistanceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TravelRouteServiceTest {

    @MockBean DestinationRepository destinationRepository;

    @Autowired TravelRouteService travelRouteService;

    @Autowired DistanceUtil distanceUtil;

    private Travel travel1;
    private Travel travel2;
    private Travel travel3;

    @BeforeEach
    public void setUp() {
        travel1 = Travel.builder()
                .name("travel1")
                .latitude(1d).longitude(1d)
                .build();
        travel2 = Travel.builder()
                .name("travel2")
                .latitude(5d).longitude(5d)
                .build();
        travel3 = Travel.builder()
                .name("travel3")
                .latitude(7d).longitude(7d)
                .build();
    }

    @Test
    public void permutationsTest() {
        // given: @BeforeEach +
        List<DestinationDTO> destinations = List.of(
                new DestinationDTO(travel1.getName(), travel1.getLatitude(), travel1.getLongitude(), null, null, 0),
                new DestinationDTO(travel2.getName(), travel2.getLatitude(), travel2.getLongitude(), null, null, 0),
                new DestinationDTO(travel3.getName(), travel3.getLatitude(), travel3.getLongitude(), null, null, 0)
        );
        DestinationDTO startAndEnd = new DestinationDTO(
                "StartAndEnd", 3d, 3d, null, null, 0
        );
        List<DestinationDTO> destinationDTOList = new ArrayList<>();
        destinationDTOList.add(startAndEnd);
        destinationDTOList.addAll(destinations);
        when(destinationRepository.getDTOList(List.of(1L, 2L, 3L)))
                .thenReturn(destinations);
        // when
        SuggestedRouteVO result = travelRouteService.getSuggestedRoute(startAndEnd, List.of(1L, 2L, 3L));
        List<Double> allRouteDistances = getDistanceOfAllCases(destinationDTOList);
        // then
        assertEquals(result.getSuggestedRoute().size(), 5);
        assertTrue(allRouteDistances.stream().allMatch(d -> result.getTotalDistance() <= d));
    }

    // TSP 알고리즘 테스트용 메소드 : 가능한 모든 경로의 순열을 만들어서 완전탐색, 모든 경우의 수의 거리 리스트 반환
    private List<Double> getDistanceOfAllCases(List<DestinationDTO> destinationDTOS) {
        double[][] distanceMap = distanceUtil.getDistanceMap(destinationDTOS);
        int[] idxs = IntStream.rangeClosed(1, destinationDTOS.size()-1).toArray();
        List<int[]> permutations = generatePermutations(idxs);
        List<Double> resultList = new ArrayList<>();
        for (int[] permutation : permutations) {
            double distance = 0d;
            for (int i = 0; i < permutation.length + 1; i++) {
                if (i == 0) distance += distanceMap[0][permutation[i]];
                else if (i == permutation.length) distance += distanceMap[permutation[i-1]][0];
                else distance += distanceMap[permutation[i-1]][permutation[i]];
            }
            resultList.add(distance);
        }
        return resultList;
    }
    private List<int[]> generatePermutations(int[] arr) {
        List<int[]> permutations = new ArrayList<>();
        generatePermutationsHelper(arr, 0, permutations);
        return permutations;
    }
    private void generatePermutationsHelper(int[] arr, int index, List<int[]> permutations) {
        if (index == arr.length - 1) {
            permutations.add(arr.clone());
            return;
        }

        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            generatePermutationsHelper(arr, index + 1, permutations);
            swap(arr, index, i);
        }
    }
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
