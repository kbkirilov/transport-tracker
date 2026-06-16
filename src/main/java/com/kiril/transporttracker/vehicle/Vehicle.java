package com.kiril.transporttracker.vehicle;

public record Vehicle(
    String vehicleId,
    String routeId,
    String tripId,
    double latitude,
    double longitude,
    long timestamp) {}
