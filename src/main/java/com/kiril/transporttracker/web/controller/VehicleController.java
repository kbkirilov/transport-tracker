package com.kiril.transporttracker.web.controller;

import com.kiril.transporttracker.gtfs.GtfsPollingService;
import com.kiril.transporttracker.web.VehicleUpdatePublisher;
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
