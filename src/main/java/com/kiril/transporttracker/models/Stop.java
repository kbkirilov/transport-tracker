package com.kiril.transporttracker.models;

public record Stop(
    String stopId, String stopCode, String stopName, double latitude, double longitude) {}
