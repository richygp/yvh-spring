package com.seedtag.codetest.yvh.service;

import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.RadarEntry;

public interface IRadarService {
    Coordinates getAttackCoordinates(RadarEntry radarEntry);
}
