package com.seedtag.codetest.yvh.model;

public class Scan {
    private final Coordinates coordinates;
    private final Enemy enemies;
    private final int allies;

    public Scan(Coordinates coordinates, Enemy enemies, int allies) {
        this.coordinates = coordinates;
        this.enemies = enemies;
        this.allies = allies;
    }
}
