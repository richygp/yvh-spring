package com.seedtag.codetest.yvh.controller;

import com.seedtag.codetest.yvh.dto.RadarEntryDTO;
import com.seedtag.codetest.yvh.mapper.DtoMapper;
import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.Enemy;
import com.seedtag.codetest.yvh.model.EnemyType;
import com.seedtag.codetest.yvh.model.ProtocolType;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.model.Scan;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    void whenSendingEmptyScan_AndThenResponseOK() throws Exception {
        RadarEntry radarEntry = new RadarEntry(new ArrayList<>(), new ArrayList<>());
        // Given
        given(dtoMapper.mapRadarEntry(any(RadarEntryDTO.class)))
                .willReturn(radarEntry);
        given(radarService.getAttackCoordinates(any(RadarEntry.class)))
                .willReturn(new Coordinates(0, 0));
        // When
        MockHttpServletResponse response = mvc.perform(
                post("/radar/")
                        .content("{\"protocols\":[],\"scan\":[]}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // Then
        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals("{\"x\":0,\"y\":0}", response.getContentAsString());
    }

    @Test
    void whenSendingWrongPayload_AndThenResponse() throws Exception {
        // When
        MockHttpServletResponse response = mvc.perform(
                        post("/radar/")
                                .content("{}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // Then
        assertEquals(400, response.getStatus());
    }

    @Test
    void whenSendingValidScan_AndThenResponseOK() throws Exception {
        List<ProtocolType> protocols = new ArrayList<>();
        protocols.add(ProtocolType.AVOID_MECH);
        List<Scan> scans = new ArrayList<>();
        Scan scan1 = new Scan(new Coordinates(0, 40), new Enemy(EnemyType.SOLDIER, 10), 0);
        Scan scan2 = new Scan(new Coordinates(0, 80), new Enemy(EnemyType.MECH, 1), 5);
        scans.add(scan1);
        scans.add(scan2);
        RadarEntry radarEntry = new RadarEntry(protocols, scans);
        // Given
        given(dtoMapper.mapRadarEntry(any(RadarEntryDTO.class)))
                .willReturn(radarEntry);
        given(radarService.getAttackCoordinates(any(RadarEntry.class)))
                .willReturn(new Coordinates(0, 40));
        // When
        String payload = "{\"protocols\":[\"avoid-mech\"],\"scan\":[" +
                "{\"coordinates\":{\"x\":0,\"y\":40},\"enemies\":{\"type\":\"soldier\",\"number\":10}}," +
                "{\"coordinates\":{\"x\":0,\"y\":80},\"allies\":5,\"enemies\":{\"type\":\"mech\",\"number\":1}}]}";
        MockHttpServletResponse response = mvc.perform(
                        post("/radar/")
                                .content(payload)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        // Then
        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals("{\"x\":0,\"y\":40}", response.getContentAsString());
    }
}
