package com.seedtag.codetest.yvh.model;

import java.util.List;

public record RadarEntry(List<ProtocolType> protocols, List<Scan> scan) {
}
