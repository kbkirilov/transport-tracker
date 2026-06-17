package com.kiril.transporttracker.gtfs;

import com.kiril.transporttracker.publishers.VehicleUpdatePublisher;
import com.kiril.transporttracker.service.VehicleEnrichService;
import com.kiril.transporttracker.vehicle.EnrichedVehicle;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GtfsPollingService {

  private final VehicleEnrichService vehicleEnrichService;
  private final VehicleUpdatePublisher publisher;

  @Scheduled(fixedDelayString = "${transport.scheduler.polling-rate}")
  public void pollAndPublishVehicles() throws Exception {
    List<EnrichedVehicle> vehicles = vehicleEnrichService.enrichVehicles();
    publisher.publish(vehicles);
  }
}
