package com.seedtag.codetest.yvh.service;

import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.ProtocolType;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.model.Scan;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public static final double MAX_DISTANCE = 100.0;
    /**
     * This Map defines the filter predicates which match for each protocol.
     * Bear in mind there are a couple of protocols missing here which are
     * not filters but sorters (CLOSEST and FURTHEST enemies).
     */
    private final Map<ProtocolType, Predicate<Scan>> mapFilter = Map.of(
            ASSIST_ALLIES, s -> s.allies() > 0,
            AVOID_CROSSFIRE, s -> s.allies() == 0,
            PRIORITIZE_MECH, s -> s.enemies().type().equals(MECH),
            AVOID_MECH, s -> s.enemies().type().equals(SOLDIER)
    );

    /**
     * This function calculates the proper {@link Coordinates} to attack in
     * the next movement.
     *
     * @param radarEntry the complete report of {@link RadarEntry} scanned.
     * @return the {@link Coordinates} of next reachable target.
     */
    @Override
    public Coordinates getAttackCoordinates(RadarEntry radarEntry) {
        // First filter by allies or mech type
        Set<Coordinates> filteredCoordinates = applyFilters(radarEntry);
        // Secondly sort the list of coordinates
        List<Coordinates> sortedCoordinates = sortCoordinates(filteredCoordinates);
        if(sortedCoordinates.isEmpty()) {
            return new Coordinates(0,0);
        }
        if(radarEntry.protocols().contains(FURTHEST_ENEMIES)) {
            return sortedCoordinates.get(sortedCoordinates.size() - 1);
        }
        return sortedCoordinates.get(0);
    }

    /**
     * This function sorts the list of {@link Coordinates} which could be attacked.
     * The minimum distance would be the sort criteria. Distance as the module of
     * two coordinates X and Y.
     *
     * @param filteredCoordinates a Set of already filtered target {@link Coordinates}
     * @return a sorted list of {@link Coordinates}.
     */
    private List<Coordinates> sortCoordinates(Set<Coordinates> filteredCoordinates) {
        Comparator<Coordinates> distance = Comparator.comparing(Coordinates::getModule);
        return filteredCoordinates.stream().sorted(distance).toList();
    }

    /**
     * This function filters out those records which are not interesting based on
     * the {@link ProtocolType} filters defined.
     * Those records which distance to origin is beyond a maximum defined will be
     * filtered out too.
     *
     * @param radarEntry complete scan reported as {@link RadarEntry}
     * @return a Set of {@link Coordinates} which have to be considered as a next target.
     */
    private Set<Coordinates> applyFilters(RadarEntry radarEntry) {
        List<ProtocolType> protocolTypes = radarEntry.protocols();
        protocolTypes = protocolTypes.stream()
                .filter(p -> !p.equals(CLOSEST_ENEMIES) && !p.equals(FURTHEST_ENEMIES))
                .toList();
        List<Scan> filteredScans = filterOutBeyondAllowedDistance(radarEntry.scan());
        if(protocolTypes.isEmpty()) {
            return filteredScans.stream().map(Scan::coordinates).collect(Collectors.toSet());
        }
        for(ProtocolType pT : protocolTypes) {
            filteredScans = filteredScans.stream().filter(mapFilter.get(pT)).toList();
        }
        return filteredScans.stream().map(Scan::coordinates).collect(Collectors.toSet());
    }

    /**
     * Filters {@link Scan} which module distance to origin is bigger than a given maximum.
     *
     * @param scans List of {@link Scan} to be filtered.
     * @return a list of {@link Scan} which distance to origin is not bigger than maximum allowed.
     */
    private List<Scan> filterOutBeyondAllowedDistance(List<Scan> scans) {
        return scans.stream()
                .filter(s -> s.coordinates().getModule() <= MAX_DISTANCE)
                .toList();
    }
}
