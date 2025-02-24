package com.matchoddsapi.model;

public enum Sport {
    FOOTBALL(1),
    BASKETBALL(2);

    private final int value;

    Sport(int value) {
        this.value = value;
    }
}