package com.kiril.transporttracker.service;

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
  private final CacheService cacheService;

  public List<EnrichedVehicle> enrichVehicles() throws Exception {
    List<RawVehicle> rawVehicles = gtfsClient.getVehicles();

    return rawVehicles.stream().map(this::enrich).collect(Collectors.toList());
  }

  private EnrichedVehicle enrich(RawVehicle rawVehicle) {
    Route route = cacheService.findRoute(rawVehicle.routeId()).orElse(Route.unknown());
    Stop stop = cacheService.findStop(rawVehicle.stopId()).orElse(Stop.unknown());
    Trip trip = cacheService.findTrip(rawVehicle.tripId()).orElse(Trip.unknown());

    return new EnrichedVehicle(
        getVehicleLine(rawVehicle.vehicleId(), route.routeShortName()),
        route.routeLongName(),
        trip.tripShortName(),
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
