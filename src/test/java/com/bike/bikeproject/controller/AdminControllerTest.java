package com.bike.bikeproject.controller;

import com.bike.bikeproject.common.WithCustomMockUser;
import com.bike.bikeproject.config.SecurityConfig;
import com.bike.bikeproject.entity.Role;
import com.bike.bikeproject.filter.JwtAuthFilter;
import com.bike.bikeproject.service.SimpleBikeStationBatchService;
import com.bike.bikeproject.service.SimpleDestinationBatchService;
import com.bike.bikeproject.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = AdminController.class,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthFilter.class})
        }
)
public class AdminControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean SimpleDestinationBatchService destinationBatchService;

    @MockBean SimpleBikeStationBatchService bikeStationBatchService;

    @MockBean UserDetailsService userDetailsService;

    @MockBean JwtUtil jwtUtil;

    @Test
    @WithCustomMockUser(userRole = Role.ROLE_ADMIN)
    @DisplayName("ADMIN 용 따릉이 API 이용 정류소 정보 갱신 API 호출 테스트")
    public void updateStationsAPITest() throws Exception {
        // given
        doNothing().when(bikeStationBatchService).batchInsertBikeStation();
        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders
                .put("/bike/admin/updateStations")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Batch Insert of Bike Station succeeded"));
        verify(bikeStationBatchService, times(1))
                .batchInsertBikeStation();
    }

    @Test
    @WithCustomMockUser(userRole = Role.ROLE_USER)
    @DisplayName("ADMIN 용 따릉이 API 이용 정류소 정보 갱신 API 호출 예외발생 테스트")
    public void updateStationsAPIExceptionTest() throws Exception {
        // given
        doNothing().when(bikeStationBatchService).batchInsertBikeStation();
        // when, then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/bike/admin/updateStations")
                                .with(csrf()))
                .andExpect(status().isForbidden());
        verify(bikeStationBatchService, times(0))
                .batchInsertBikeStation();
    }

    @Test
    @WithCustomMockUser(userRole = Role.ROLE_ADMIN)
    @DisplayName("ADMIN 용 전처리된 데이터파일 이용 여행지/식당/카페 정보 갱신 API 호출 테스트")
    public void updateDestinationsAPITest() throws Exception {
        // given
        doNothing().when(destinationBatchService).batchInsert(any());
        String[] destinationTypes = {"C", "R", "T"};
        // when, then
        for (String type : destinationTypes) {
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .put("/bike/admin/updateDestinations?type="+type)
                                    .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Batch Insert Destination for type "+type+" succeeded"));
        }
        verify(destinationBatchService, times(3))
                .batchInsert(any());
    }

    @Test
    @WithCustomMockUser(userRole = Role.ROLE_USER)
    @DisplayName("ADMIN 용 전처리된 데이터파일 이용 여행지/식당/카페 정보 갱신 API 호출 예외발생 테스트")
    public void updateDestinationsAPIExceptionTest() throws Exception {
        // given
        doNothing().when(destinationBatchService).batchInsert(any());
        String[] destinationTypes = {"C", "R", "T"};
        // when, then
        for (String type : destinationTypes) {
            mockMvc.perform(
                            MockMvcRequestBuilders
                                    .put("/bike/admin/updateDestinations?type="+type)
                                    .with(csrf()))
                    .andExpect(status().isForbidden());
        }
        verify(destinationBatchService, times(0))
                .batchInsert(any());
    }
}
