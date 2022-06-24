package com.seedtag.codetest.yvh.dto;

import java.util.List;

public record RadarEntryDTO(List<ProtocolTypeDTO> protocols, List<ScandDTO> scan) {
}
