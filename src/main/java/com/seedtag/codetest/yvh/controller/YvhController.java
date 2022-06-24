package com.seedtag.codetest.yvh.controller;

import com.seedtag.codetest.yvh.dto.CoordinatesDTO;
import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.service.IRadarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YvhController {
    private final IRadarService radarService;

    public YvhController(IRadarService radarService) {
        this.radarService = radarService;
    }

    @PostMapping(value = "/radar")
    public CoordinatesDTO radar(@RequestBody RadarEntry radarEntry){
        Coordinates coordinatesResult = radarService.getAttackCoordinates(radarEntry);
        return new CoordinatesDTO(coordinatesResult.x(), coordinatesResult.y());
    }
}
