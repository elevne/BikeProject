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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    @DisplayName("따릉이 API 이용 정류소 정보 갱신 API (ADMIN 용) 테스트")
    public void updateStationsAPITest() throws Exception {
        // given
        doNothing().when(bikeStationBatchService).batchInsertBikeStation();
        // when, then
        mockMvc.perform(
                MockMvcRequestBuilders
                .put("/bike/admin/updateStations")
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Batch Insert of Bike Station succeeded"));
        verify(bikeStationBatchService, times(1))
                .batchInsertBikeStation();
    }

    // todo: ADMIN 아닌 일반 권한으로 실행했을 때 예외 나는 것부터 이어서 작성~
}
