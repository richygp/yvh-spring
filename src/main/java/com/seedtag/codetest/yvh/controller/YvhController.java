package com.seedtag.codetest.yvh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YvhController {
    @GetMapping("/greeting")
    public String greeting(){
        return "new RadarEntry(new ArrayList<>(), new ArrayList<>())";
    }
}
