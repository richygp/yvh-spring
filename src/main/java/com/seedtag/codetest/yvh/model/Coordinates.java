package com.seedtag.codetest.yvh.model;

public record Coordinates(int x, int y) {
    public double getModule() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }
}
