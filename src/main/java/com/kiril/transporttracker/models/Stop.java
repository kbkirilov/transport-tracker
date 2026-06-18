package com.kiril.transporttracker.models;

public record Stop(
    String stopId, String stopCode, String stopName, double latitude, double longitude) {
  public static final String UNKNOWN = "UNKNOWN";

  public static Stop unknown() {
    return new Stop(UNKNOWN, UNKNOWN, UNKNOWN, -1, -1);
  }
}
