package com.kiril.transporttracker.vehicle;

import lombok.Builder;

@Builder
public record EnrichedVehicle(
    String line,
    String routeShortName,
    String routeLongName,
    String tripShortName,
    String stopName,
    double latitude,
    double longitude,
    long timestamp) {}
