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
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.seedtag.codetest.yvh.model.ProtocolType.ASSIST_ALLIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.AVOID_CROSSFIRE;
import static com.seedtag.codetest.yvh.model.ProtocolType.AVOID_MECH;
import static com.seedtag.codetest.yvh.model.ProtocolType.CLOSEST_ENEMIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.FURTHEST_ENEMIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.PRIORITIZE_MECH;

@Component
public class DtoMapper {
    private final Map<String, ProtocolType> protocolsMapper = Map.of(
            "closest-enemies", CLOSEST_ENEMIES,
            "furthest-enemies", FURTHEST_ENEMIES,
            "assist-allies", ASSIST_ALLIES,
            "avoid-crossfire", AVOID_CROSSFIRE,
            "prioritize-mech", PRIORITIZE_MECH,
            "avoid-mech", AVOID_MECH);

    private final Map<String, EnemyType> enemyMapper = Map.of(
            "soldier", EnemyType.SOLDIER,
            "mech", EnemyType.MECH);

    public RadarEntry mapRadarEntry(RadarEntryDTO radarEntryDTO) {
        return new RadarEntry(
                radarEntryDTO.protocols().stream()
                        .map(protocolsMapper::get)
                        .toList(),
                radarEntryDTO.scan().stream()
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
        return new Enemy(enemyMapper.get(enemyDTO.type()), enemyDTO.number());
    }
}
