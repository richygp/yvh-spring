package com.seedtag.codetest.yvh.dto;

import com.seedtag.codetest.yvh.model.ProtocolType;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.seedtag.codetest.yvh.model.ProtocolType.ASSIST_ALLIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.AVOID_CROSSFIRE;
import static com.seedtag.codetest.yvh.model.ProtocolType.AVOID_MECH;
import static com.seedtag.codetest.yvh.model.ProtocolType.CLOSEST_ENEMIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.FURTHEST_ENEMIES;
import static com.seedtag.codetest.yvh.model.ProtocolType.PRIORITIZE_MECH;

public class RadarEntryDTO {
    private final List<ProtocolType> protocols;
    private final List<ScanDTO> scan;

    public RadarEntryDTO(List<String> protocols, List<ScanDTO> scan) {
        Map<String, ProtocolType> protocolsMapper = Map.of(
                "closest-enemies", CLOSEST_ENEMIES,
                "furthest-enemies", FURTHEST_ENEMIES,
                "assist-allies", ASSIST_ALLIES,
                "avoid-crossfire", AVOID_CROSSFIRE,
                "prioritize-mech", PRIORITIZE_MECH,
                "avoid-mech", AVOID_MECH);
        this.protocols = protocols.stream()
                .map(protocolsMapper::get)
                .filter(Objects::nonNull)
                .toList();
        this.scan = scan;
    }

    public List<ProtocolType> getProtocols() {
        return protocols;
    }

    public List<ScanDTO> getScan() {
        return scan;
    }
}
