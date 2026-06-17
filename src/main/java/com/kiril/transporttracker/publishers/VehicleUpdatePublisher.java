package com.kiril.transporttracker.publishers;

import com.kiril.transporttracker.vehicle.EnrichedVehicle;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VehicleUpdatePublisher {

  private final SimpMessagingTemplate template;

  public void publish(List<EnrichedVehicle> vehicles) {
    template.convertAndSend("/topic/vehicles", vehicles);
  }
}
