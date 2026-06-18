package com.kiril.transporttracker.vehicle;

public record RawVehicle(
    String vehicleId,
    String routeId,
    String tripId,
    String stopId,
    double latitude,
    double longitude,
    long timestamp) {}
