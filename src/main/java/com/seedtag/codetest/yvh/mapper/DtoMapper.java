package com.seedtag.codetest.yvh.mapper;

import com.seedtag.codetest.yvh.dto.CoordinatesDTO;
import com.seedtag.codetest.yvh.dto.EnemyDTO;
import com.seedtag.codetest.yvh.dto.RadarEntryDTO;
import com.seedtag.codetest.yvh.dto.ScanDTO;
import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.Enemy;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.model.Scan;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public RadarEntry mapRadarEntry(RadarEntryDTO radarEntryDTO) {
        return new RadarEntry(radarEntryDTO.getProtocols(),
                radarEntryDTO.getScan().stream()
                        .map(this::mapScan)
                        .toList());
    }

    public Scan mapScan(ScanDTO scanDTO) {
        CoordinatesDTO coordinatesDTO = scanDTO.coordinates();
        EnemyDTO enemyDTO = scanDTO.enemies();
        return new Scan(
                mapCoordinates(coordinatesDTO),
                mapEnemy(enemyDTO),
                scanDTO.allies());
    }

    public Coordinates mapCoordinates(CoordinatesDTO coordinatesDTO) {
        return new Coordinates(coordinatesDTO.x(), coordinatesDTO.y());
    }

    public Enemy mapEnemy(EnemyDTO enemyDTO) {
        return new Enemy(enemyDTO.getType(), enemyDTO.getNumber());
    }
}
