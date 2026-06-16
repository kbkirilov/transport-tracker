package com.kiril.transporttracker.gtfs;

import com.kiril.transporttracker.publishers.VehicleUpdatePublisher;
import com.kiril.transporttracker.vehicle.Vehicle;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GtfsPollingService {

  private final GtfsRealtimeClient client;
  private final VehicleUpdatePublisher publisher;

  @Scheduled(fixedDelayString = "${transport.scheduler.polling-rate}")
  public void pollAndPublishVehicles() throws Exception {
    List<Vehicle> vehicles = client.getVehicles();
    publisher.publish(vehicles);
  }
}
