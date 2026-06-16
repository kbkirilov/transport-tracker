package com.kiril.transporttracker.vehicle;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

  public List<Vehicle> getFirstNVehicles(List<Vehicle> allVehicles, int n) {
    return allVehicles.stream().limit(n).collect(Collectors.toList());
  }
}
