package com.seedtag.codetest.yvh.service;

import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.ProtocolType;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.model.Scan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.seedtag.codetest.yvh.model.EnemyType.MECH;
import static com.seedtag.codetest.yvh.model.EnemyType.SOLDIER;
import static com.seedtag.codetest.yvh.model.ProtocolType.ASSIST_ALLIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.AVOID_CROSSFIRE;
import static com.seedtag.codetest.yvh.model.ProtocolType.AVOID_MECH;
import static com.seedtag.codetest.yvh.model.ProtocolType.CLOSEST_ENEMIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.FURTHEST_ENEMIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.PRIORITIZE_MECH;

@Service
public class RadarServiceImpl implements IRadarService {
    private final Map<ProtocolType, Predicate<Scan>> mapFilter = Map.of(
            ASSIST_ALLIES, s -> s.allies() > 0,
            AVOID_CROSSFIRE, s -> s.allies() == 0,
            PRIORITIZE_MECH, s -> s.enemies().type().equals(MECH),
            AVOID_MECH, s -> s.enemies().type().equals(SOLDIER)
    );
    @Override
    public Coordinates getAttackCoordinates(RadarEntry radarEntry) {
        // First filter by allies or mech type
        Set<Coordinates> filteredCoordinates = applyFilters(radarEntry);
        // Secondly sort the list of coordinates
        List<Coordinates> sortedCoordinates = filteredCoordinates.stream().toList();
        sortedCoordinates = sortedCoordinates.stream().sorted().toList();
        if(radarEntry.protocols().contains(FURTHEST_ENEMIES)) {
            return sortedCoordinates.get(sortedCoordinates.size() - 1);
        }
        return sortedCoordinates.get(0);
    }

    private Set<Coordinates> applyFilters(RadarEntry radarEntry) {
        List<ProtocolType> protocolTypes = radarEntry.protocols();
        protocolTypes = protocolTypes.stream()
                .filter(p -> !p.equals(CLOSEST_ENEMIES) && !p.equals(FURTHEST_ENEMIES))
                .toList();
        List<Scan> scans = radarEntry.scan();
        List<Scan> filteredScans = new ArrayList<>();
        if(protocolTypes.isEmpty()) {
            return scans.stream().map(Scan::coordinates).collect(Collectors.toSet());
        }
        for(ProtocolType pT : protocolTypes) {
            filteredScans.addAll(scans.stream().filter(mapFilter.get(pT)).toList());
        }
        return filteredScans.stream().map(Scan::coordinates).collect(Collectors.toSet());
    }
}
