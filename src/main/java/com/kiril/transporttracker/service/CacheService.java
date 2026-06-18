package com.kiril.transporttracker.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.kiril.transporttracker.gtfs.GtfsParser;
import com.kiril.transporttracker.models.Route;
import com.kiril.transporttracker.models.Stop;
import com.kiril.transporttracker.models.Trip;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheService {

  private final Cache<String, Route> routesCache;
  private final Cache<String, Stop> stopCache;
  private final Cache<String, Trip> tripCache;
  private final GtfsParser gtfsParser;

  @PostConstruct
  public void warmup() {
    gtfsParser.getRoutes().forEach(route -> routesCache.put(route.routeId(), route));
    gtfsParser.getStops().forEach(stop -> stopCache.put(stop.stopId(), stop));
    gtfsParser.getTrips().forEach(trip -> tripCache.put(trip.tripId(), trip));
  }

  public Optional<Route> findRoute(String routeId) {
    return Optional.ofNullable(routesCache.getIfPresent(routeId));
  }

  public Optional<Stop> findStop(String stopId) {
    return Optional.ofNullable(stopCache.getIfPresent(stopId));
  }

  public Optional<Trip> findTrip(String tripId) {
    return Optional.ofNullable(tripCache.getIfPresent(tripId));
  }
}
