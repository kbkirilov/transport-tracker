package com.kiril.transporttracker.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.kiril.transporttracker.gtfs.parsers.GtfsParser;
import com.kiril.transporttracker.models.Route;
import com.kiril.transporttracker.models.Stop;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheService {

  private final Cache<String, Route> routesCache;
  private final Cache<String, Stop> stopCache;
  private final GtfsParser gtfsParser;

  @PostConstruct
  public void warmup() {
    gtfsParser.getRoutes().forEach(route -> routesCache.put(route.routeId(), route));
    gtfsParser.getStopsFromFile().forEach(stop -> stopCache.put(stop.stopId(), stop));
  }
}
