package com.bike.bikeproject.service.impl;

import com.bike.bikeproject.dto.DestinationDTO;
import com.bike.bikeproject.vo.SuggestedRouteVO;
import com.bike.bikeproject.repository.DestinationRepository;
import com.bike.bikeproject.service.TravelRouteService;
import com.bike.bikeproject.util.DistanceUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravelRouteServiceImpl implements TravelRouteService {

    private final DestinationRepository destinationRepository;

    private final DistanceUtil distanceUtil;

    /**
     * 숙소에서 출발, 여행지를 전부 들른 후 동일한 숙소로 복귀하는 경우에 사용하는 메소드
     * @param startAndEnd 출발/복귀하는 숙소 위치에 대한 정보
     * @param destinationIds 들르고자 하는 여행지들의 id
     * @return 추천경로 정보를 담은 리스트
     */
    @Override
    public SuggestedRouteVO getSuggestedRoute(DestinationDTO startAndEnd, List<Long> destinationIds) throws IllegalArgumentException {
        if (destinationIds.size() < 2) throw new IllegalArgumentException("Size of List<Long> destinationIds must be larger than '2'");
        List<DestinationDTO> destinations = new ArrayList<>();
        destinations.add(startAndEnd);
        destinations.addAll(destinationRepository.getDTOList(destinationIds));

        final double[][] distanceMap = distanceUtil.getDistanceMap(destinations);

        TSP tsp = new TSP();
        tsp.setOrderAndTotalDistance(distanceMap);

        List<DestinationDTO> suggestedRoute = new ArrayList<>();
        List<Integer> orders = tsp.getOrder();
        for (int i = 0; i < orders.size(); i++) {
            DestinationDTO dto = destinations.get(orders.get(i));
            if (i != (orders.size() - 1)) dto.setOrder(i);
            suggestedRoute.add(dto);
        }
        return new SuggestedRouteVO(suggestedRoute, tsp.getTotalDistance());
    }


    @Getter
    private class TSP {
        private List<Integer> order = Collections.emptyList();
        private double totalDistance = 0d;
        public void setOrderAndTotalDistance(double[][] distanceMap) {
            // 출발지를 포함한 총 여행지의 수
            int count = distanceMap.length;
            // (2^n) 노드 방문 상태를 2 진수로 나타냄: 0001 이면 0 번 노드만 방문한 상태, 0111 이면 0, 1, 2 번 노드를 방문한 상태를 나타냄
            int totalStates = 1 << count;
            /* 모든 상태에 대한 최단 경로 길이를 저장하는 배열:
            dp[7][2] 면 (count 가 4 일 때) 0, 1, 2 번 노드를 방문한 상태에서 현재 마지막으로 방문한 노드가 2 번 노드일 때의 최단경로 길이를 나타냄 */
            double[][] dp = new double[totalStates][count];
            /* 각 상태에서 마지막으로 방문한 노드와 이전 노드를 저장하는 배열:
            dp[7][2] = 1 이면 0, 1, 2 번 노드를 방문한 상태에서 현재 마지막으로 방문한 노드가 2 번 노드이며, 이전 노드는 1 번 노드라는 것을 나타냄 */
            int[][] prev = new int[totalStates][count];
            // 두 배열 모두 초기화
            for (int i = 0; i < totalStates; i++) {
                Arrays.fill(dp[i], Double.POSITIVE_INFINITY);
                Arrays.fill(prev[i], -1);
            }
            // 0001 (0번 노드만 방문한 상태) & 이전 노드 0 번 노드 => 출발 노드 설정
            dp[1][0] = 0;
            // 순서대로 0001 0011 0101 0110 1001 1010 1101 1110
            // mask 는 비트마스크로서 방문한 노드들은 나타낸다. (0 번 노드를 제외한 나머지 노드를 방문해야하므로, mask 는 1 부터 시작 (mask 가 0 이면 모든 노드를 방문하지 않은 상태))
            for (int mask = 1; mask < totalStates; mask += 2) {
                // 0 1 2 3: 현재 방문한 노드의 인덱스, 0 번 노드는 출발 노드이므로 i 는 1 부터 시작
                for (int i = 1; i < count; i++) {
                    if ((mask & (1 << i)) != 0) { // i 번째 비트가 1 인지 확인
                        // 0 1 2 3: 이전에 방문한 노드의 인덱스, j 는 0 부터 시작할 수 있음
                        for (int j = 0; j < count; j++) {
                            if ((mask & (1 << j)) != 0) { // j 번째 비트가 1 인지 확인
                                // i 번째 노드를 방문하지 않은 상태로 바꾸고
                                // 그 상태에서 j 번째 노드에서 i 번째 노드로 이동하는 비용을 계산
                                double candidate = dp[mask ^ (1 << i)][j] + distanceMap[j][i];
                                if (candidate < dp[mask][i]) {
                                    dp[mask][i] = candidate;
                                    prev[mask][i] = j;
                                }
                            }
                        }
                    }
                }
            }
            int finalMask = totalStates - 1;  // 1111: 모든 노드 방문한 상태
            double minTour = Double.POSITIVE_INFINITY;
            int lastNode = -1;
            for (int i = 1; i < count; i++) {
                double candidate = dp[finalMask][i] + distanceMap[i][0];
                if (candidate < minTour) {
                    minTour = candidate;
                    lastNode = i;
                }
            }
            // 최단 경로의 루트
            List<Integer> path = new ArrayList<>();
            path.add(0);
            while (lastNode != -1) {
                path.add(lastNode);
                int prevNode = prev[finalMask][lastNode];
                finalMask ^= (1 << lastNode);
                lastNode = prevNode;
            }
            // 값 세팅
            this.totalDistance = minTour;
            this.order = path;
        }
    }
}
