package com.kiril.transporttracker.models;

import lombok.Builder;

@Builder
public record Routes(
    String routeId,
    String agencyId,
    String routeShortName,
    String routeLongName,
    String routeType) {}
