package com.kiril.transporttracker.service;

import com.kiril.transporttracker.exceptions.RouteNotFoundException;
import com.kiril.transporttracker.exceptions.StopNotFoundException;
import com.kiril.transporttracker.exceptions.TripNotFoundException;
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
    Route route =
        cacheService.findRoute(rawVehicle.routeId()).orElseThrow(RouteNotFoundException::new);
    Stop stop = cacheService.findStop(rawVehicle.stopId()).orElseThrow(StopNotFoundException::new);
    Trip trip = cacheService.findTrip(rawVehicle.tripId()).orElseThrow(TripNotFoundException::new);

    return new EnrichedVehicle(
        getVehicleLine(rawVehicle.vehicleId(), route.routeShortName()),
        route.routeShortName(),
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
