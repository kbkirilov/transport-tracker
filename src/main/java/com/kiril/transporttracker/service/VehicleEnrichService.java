package com.kiril.transporttracker.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.kiril.transporttracker.gtfs.GtfsRealtimeClient;
import com.kiril.transporttracker.models.Route;
import com.kiril.transporttracker.models.Stop;
import com.kiril.transporttracker.models.Trip;
import com.kiril.transporttracker.vehicle.EnrichedVehicle;
import com.kiril.transporttracker.vehicle.RawVehicle;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VehicleEnrichService {

  private final GtfsRealtimeClient gtfsClient;
  private final Cache<String, Route> routesCache;
  private final Cache<String, Stop> stopsCache;
  private final Cache<String, Trip> tripCache;

  public List<EnrichedVehicle> enrichVehicles() throws Exception {
    List<RawVehicle> rawVehicles = gtfsClient.getVehicles();

    return rawVehicles.stream().map(this::enrich).collect(Collectors.toList());
  }

  private EnrichedVehicle enrich(RawVehicle rawVehicle) {
    Route route = routesCache.getIfPresent(rawVehicle.routeId());
    Stop stop = stopsCache.getIfPresent(rawVehicle.stopId());
    Trip trip = tripCache.getIfPresent(rawVehicle.tripId());

    // TODO throw custom exception below
    if (route == null || stop == null) {
      throw new RuntimeException("X is null");
    }

    return new EnrichedVehicle(
        getVehicleLine(rawVehicle.vehicleId(), route.routeShortName()),
        route.routeShortName(),
        route.routeLongName(),
        trip == null ? "NONE" : trip.tripShortName(),
        stop.stopName(),
        rawVehicle.latitude(),
        rawVehicle.longitude(),
        rawVehicle.timestamp());
  }

  private String getVehicleLine(String rawVehicleId, String routeShortName) {
    if (rawVehicleId.isBlank()) return routeShortName;
    if (routeShortName.isBlank()) return "";

    int index = 0;
    while (!Character.isDigit(rawVehicleId.charAt(index))) {
      index++;
    }
    return rawVehicleId.substring(0, index) + routeShortName;
  }
}
