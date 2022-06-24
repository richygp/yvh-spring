package com.seedtag.codetest.yvh.model;

import java.util.List;

public class RadarEntry {
    private final List<ProtocolType> protocols;
    private final List<Scan> scan;

    public RadarEntry(List<ProtocolType> protocols, List<Scan> scan) {
        this.protocols = protocols;
        this.scan = scan;
    }
}
