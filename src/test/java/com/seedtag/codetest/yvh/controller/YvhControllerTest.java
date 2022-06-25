package com.seedtag.codetest.yvh.controller;

import com.seedtag.codetest.yvh.dto.RadarEntryDTO;
import com.seedtag.codetest.yvh.mapper.DtoMapper;
import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.service.IRadarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class YvhControllerTest {
    @Mock
    private IRadarService radarService;
    @Mock
    private DtoMapper dtoMapper;
    @InjectMocks
    private YvhController yvhController;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(yvhController).build();
    }

    @Test
    void whenSendingScan_AndThenOK() throws Exception {
//        RadarEntryDTO radarEntryDTO = new RadarEntryDTO(new ArrayList<>(), new ArrayList<>());
//        RadarEntry radarEntry = new RadarEntry(new ArrayList<>(), new ArrayList<>());
//        // Given
//        given(dtoMapper.mapRadarEntry(radarEntryDTO))
//                .willReturn(radarEntry);
//        given(radarService.getAttackCoordinates(radarEntry))
//                .willReturn(new Coordinates(0, 0));
//        // When
//        MockHttpServletResponse response = mvc.perform(
//                post("/radar/")
//                        .content("{\"protocols\":[],\"scan\":[]}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();
//        // Then
//        assertEquals(200, response.getStatus());
//        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
//        assertEquals("{\"x\":0,\"y\":0}", response.getContentAsString());
    }
}