package com.kiril.transporttracker.web;

import com.kiril.transporttracker.gtfs.GtfsPollingService;
import com.kiril.transporttracker.publishers.VehicleUpdatePublisher;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class VehicleController {

  private final GtfsPollingService gtfsPollingService;
  private final VehicleUpdatePublisher publisher;

  @MessageMapping("/vehicles/request")
  public void requestVehicles() throws Exception {
    gtfsPollingService.pollAndPublishVehicles();
  }
}
