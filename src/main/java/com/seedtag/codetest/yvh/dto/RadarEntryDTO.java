package com.seedtag.codetest.yvh.dto;

import java.util.List;
import java.util.Objects;

public record RadarEntryDTO(List<String> protocols, List<ScanDTO> scan) {
    public RadarEntryDTO(List<String> protocols, List<ScanDTO> scan) {
        this.protocols = protocols.stream()
                .filter(Objects::nonNull)
                .toList();
        this.scan = scan;
    }
}
