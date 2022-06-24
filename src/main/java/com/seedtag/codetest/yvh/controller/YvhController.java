package com.seedtag.codetest.yvh.controller;

import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.model.RadarEntry;
import com.seedtag.codetest.yvh.service.IRadarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class YvhController {
    private final IRadarService radarService;

    public YvhController(IRadarService radarService) {
        this.radarService = radarService;
    }

    @GetMapping(value = "/greeting")
    public Coordinates greeting(){
        return radarService.getAttackCoordinates(
                new RadarEntry(new ArrayList<>(),new ArrayList<>()));
    }
}
