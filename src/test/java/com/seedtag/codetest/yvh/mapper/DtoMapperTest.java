package com.seedtag.codetest.yvh.mapper;

import com.seedtag.codetest.yvh.dto.CoordinatesDTO;
import com.seedtag.codetest.yvh.dto.EnemyDTO;
import com.seedtag.codetest.yvh.dto.RadarEntryDTO;
import com.seedtag.codetest.yvh.dto.ScanDTO;
import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.Enemy;
import com.seedtag.codetest.yvh.model.EnemyType;
import com.seedtag.codetest.yvh.model.ProtocolType;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.model.Scan;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoMapperTest {
    private final DtoMapper dtoMapper = new DtoMapper();

    @Test
    void mapRadarEntryTest() {
        List<ProtocolType> protocols = new ArrayList<>();
        protocols.add(ProtocolType.AVOID_MECH);
        List<Scan> scans = new ArrayList<>();
        Scan scan1 = new Scan(new Coordinates(0, 40), new Enemy(EnemyType.SOLDIER, 10), 0);
        Scan scan2 = new Scan(new Coordinates(0, 80), new Enemy(EnemyType.MECH, 1), 5);
        scans.add(scan1);
        scans.add(scan2);
        RadarEntry radarEntry = new RadarEntry(protocols, scans);

        List<String> protocolDTOs = new ArrayList<>();
        protocolDTOs.add("avoid-mech");
        List<ScanDTO> scanDTOs = new ArrayList<>();
        ScanDTO scanDTO1 = new ScanDTO(new CoordinatesDTO(0, 40), new EnemyDTO("soldier", 10), 0);
        ScanDTO scanDTO2 = new ScanDTO(new CoordinatesDTO(0, 80), new EnemyDTO("mech", 1), 5);
        scanDTOs.add(scanDTO1);
        scanDTOs.add(scanDTO2);
        RadarEntryDTO radarEntryDTO = new RadarEntryDTO(protocolDTOs, scanDTOs);

        assertEquals(radarEntry, dtoMapper.mapRadarEntry(radarEntryDTO));
    }

    @Test
    void mapScanTest() {
        Scan scan = new Scan(new Coordinates(0, 40), new Enemy(EnemyType.SOLDIER, 10), 0);
        ScanDTO scanDTO = new ScanDTO(new CoordinatesDTO(0, 40), new EnemyDTO("soldier", 10), 0);

        assertEquals(scan, dtoMapper.mapScan(scanDTO));
    }
}