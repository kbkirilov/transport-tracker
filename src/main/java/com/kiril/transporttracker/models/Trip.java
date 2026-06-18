package com.kiril.transporttracker.models;

public record Trip(
    String tripId,
    String routeId,
    String serviceId,
    String tripShortName,
    String tripDirectionId) {}
