package com.kiril.transporttracker.web;

import com.kiril.transporttracker.gtfs.GtfsPollingService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class VehicleController {

  private final GtfsPollingService gtfsPollingService;

  @MessageMapping("/vehicles/request")
  public void requestVehicles() throws Exception {
    gtfsPollingService.pollAndPublishVehicles();
  }
}
