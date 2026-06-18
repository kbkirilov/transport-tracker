package com.kiril.transporttracker.models;

public record Trip(
    String tripId, String routeId, String serviceId, String tripShortName, String tripDirectionId) {
  public static final String UNKNOWN = "UNKNOWN";

  public static Trip unknown() {
    return new Trip(UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
  }
}
