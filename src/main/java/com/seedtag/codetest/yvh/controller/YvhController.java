package com.seedtag.codetest.yvh.controller;

import com.seedtag.codetest.yvh.dto.CoordinatesDTO;
import com.seedtag.codetest.yvh.dto.RadarEntryDTO;
import com.seedtag.codetest.yvh.mapper.DtoMapper;
import com.seedtag.codetest.yvh.model.Coordinates;
import com.seedtag.codetest.yvh.service.IRadarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YvhController {
    private final IRadarService radarService;
    private final DtoMapper dtoMapper;

    public YvhController(IRadarService radarService, DtoMapper dtoMapper) {
        this.radarService = radarService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping(value = "/radar",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CoordinatesDTO radar(@RequestBody RadarEntryDTO radarEntryDTO) {
        Coordinates coordinatesResult = radarService.getAttackCoordinates(dtoMapper.mapRadarEntry(radarEntryDTO));
        return new CoordinatesDTO(coordinatesResult.x(), coordinatesResult.y());
    }
}
