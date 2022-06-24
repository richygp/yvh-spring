package com.seedtag.codetest.yvh.service;

import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.RadarEntry;
import org.springframework.stereotype.Service;

@Service
public class RadarServiceImpl implements IRadarService {
    @Override
    public Coordinates getAttackCoordinates(RadarEntry radarEntry) {
        return new Coordinates(0, 0);
    }
}
