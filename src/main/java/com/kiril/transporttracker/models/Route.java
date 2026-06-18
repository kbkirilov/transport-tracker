package com.kiril.transporttracker.models;

import lombok.Builder;

@Builder
public record Route(
    String routeId,
    String agencyId,
    String routeShortName,
    String routeLongName,
    String routeType) {
  public static final String UNKNOWN = "UNKNOWN";

  public static Route unknown() {
    return new Route(UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN);
  }
}
