package com.seedtag.codetest.yvh.controller;

import com.seedtag.codetest.yvh.dto.CoordinatesDTO;
import com.seedtag.codetest.yvh.dto.EnemyDTO;
import com.seedtag.codetest.yvh.dto.RadarEntryDTO;
import com.seedtag.codetest.yvh.dto.ScanDTO;
import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.Enemy;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.model.Scan;
import com.seedtag.codetest.yvh.service.IRadarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class YvhController {
    private final IRadarService radarService;

    public YvhController(IRadarService radarService) {
        this.radarService = radarService;
    }

    private RadarEntry mapRadarEntry(RadarEntryDTO radarEntryDTO) {
        return new RadarEntry(radarEntryDTO.getProtocols(),
                radarEntryDTO.getScan().stream()
                        .map(this::mapScan)
                        .toList());
    }

    private Scan mapScan(ScanDTO scanDTO) {
        CoordinatesDTO coordinatesDTO = scanDTO.coordinates();
        EnemyDTO enemyDTO = scanDTO.enemies();
        return new Scan(
                new Coordinates(coordinatesDTO.x(), coordinatesDTO.y()),
                new Enemy(enemyDTO.getType(), enemyDTO.getNumber()),
                scanDTO.allies());
    }

    @PostMapping(value = "/radar")
    public CoordinatesDTO radar(@RequestBody RadarEntryDTO radarEntryDTO) {
        Coordinates coordinatesResult = radarService.getAttackCoordinates(mapRadarEntry(radarEntryDTO));
        return new CoordinatesDTO(coordinatesResult.x(), coordinatesResult.y());
    }
}
