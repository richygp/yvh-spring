package com.seedtag.codetest.yvh.service;

import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.Enemy;
import com.seedtag.codetest.yvh.model.EnemyType;
import com.seedtag.codetest.yvh.model.ProtocolType;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.model.Scan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RadarServiceImplTest {
    @InjectMocks
    private RadarServiceImpl radarService;

    @Test
    void whenGetAttackCoordinatesWithRandomScan_AndOK() {
        List<ProtocolType> protocols = new ArrayList<>();
        protocols.add(ProtocolType.AVOID_MECH);
        List<Scan> scans = new ArrayList<>();
        Scan scan1 = new Scan(new Coordinates(0, 40), new Enemy(EnemyType.SOLDIER, 10), 0);
        Scan scan2 = new Scan(new Coordinates(0, 80), new Enemy(EnemyType.MECH, 1), 5);
        scans.add(scan1);
        scans.add(scan2);
        RadarEntry radarEntry = new RadarEntry(protocols, scans);
        // When
        Coordinates result = radarService.getAttackCoordinates(radarEntry);
        // Then
        Coordinates expectedResult = new Coordinates(0, 40);
        assertEquals(expectedResult, result);
    }

    @Test
    void whenGetAttackCoordinatesWithEmptyScan_AndOK() {
        RadarEntry radarEntry = new RadarEntry(new ArrayList<>(), new ArrayList<>());
        // When
        Coordinates result = radarService.getAttackCoordinates(radarEntry);
        // Then
        Coordinates expectedResult = new Coordinates(0, 0);
        assertEquals(expectedResult, result);
    }

    @Test
    void whenGetAttackCoordinatesWithMultiProtocolScan_AndEmptyResponse() {
        List<ProtocolType> protocols = new ArrayList<>();
        protocols.add(ProtocolType.FURTHEST_ENEMIES);
        protocols.add(ProtocolType.ASSIST_ALLIES);
        protocols.add(ProtocolType.AVOID_MECH);
        List<Scan> scans = new ArrayList<>();
        Scan scan1 = new Scan(new Coordinates(0, 40), new Enemy(EnemyType.SOLDIER, 10), 0);
        Scan scan2 = new Scan(new Coordinates(0, 80), new Enemy(EnemyType.MECH, 1), 5);
        scans.add(scan1);
        scans.add(scan2);
        RadarEntry radarEntry = new RadarEntry(protocols, scans);
        // When
        Coordinates result = radarService.getAttackCoordinates(radarEntry);
        // Then
        Coordinates expectedResult = new Coordinates(0, 0);
        assertEquals(expectedResult, result);
    }
    @Test
    void whenGetAttackCoordinatesWithMultiProtocolScan_AndOK() {
        List<ProtocolType> protocols = new ArrayList<>();
        protocols.add(ProtocolType.FURTHEST_ENEMIES);
        protocols.add(ProtocolType.ASSIST_ALLIES);
        protocols.add(ProtocolType.PRIORITIZE_MECH);
        List<Scan> scans = new ArrayList<>();
        Scan scan1 = new Scan(new Coordinates(0, 40), new Enemy(EnemyType.SOLDIER, 10), 0);
        Scan scan2 = new Scan(new Coordinates(0, 80), new Enemy(EnemyType.MECH, 1), 5);
        scans.add(scan1);
        scans.add(scan2);
        RadarEntry radarEntry = new RadarEntry(protocols, scans);
        // When
        Coordinates result = radarService.getAttackCoordinates(radarEntry);
        // Then
        Coordinates expectedResult = new Coordinates(0, 80);
        assertEquals(expectedResult, result);
    }
}
