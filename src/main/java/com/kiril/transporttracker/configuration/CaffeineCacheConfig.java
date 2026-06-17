package com.kiril.transporttracker.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.kiril.transporttracker.models.Route;
import com.kiril.transporttracker.models.Stop;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineCacheConfig {

  @Value("${cache.default.time-to-live}")
  private long ttlMinutes;

  @Value("${cache.default.max-size}")
  private long maxSize;

  @Bean
  public Cache<String, Route> routesCache() {
    return Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofMinutes(ttlMinutes))
        .maximumSize(maxSize)
        .recordStats()
        .build();
  }

  @Bean
  public Cache<String, Stop> stopesCache() {
    return Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofMinutes(ttlMinutes))
        .maximumSize(maxSize)
        .recordStats()
        .build();
  }
}
