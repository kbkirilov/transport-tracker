package com.kiril.transporttracker.gtfs;

import com.google.transit.realtime.GtfsRealtime;
import com.kiril.transporttracker.vehicle.RawVehicle;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GtfsRealtimeClient {
  private static final String VEHICLE_POSITIONS_URL =
      "https://gtfs.sofiatraffic.bg/api/v1/vehicle-positions";

  public List<RawVehicle> getVehicles() throws Exception {
    HttpRequest request =
        HttpRequest.newBuilder().uri(URI.create(VEHICLE_POSITIONS_URL)).GET().build();

    HttpResponse<InputStream> response =
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());

    GtfsRealtime.FeedMessage feed = GtfsRealtime.FeedMessage.parseFrom(response.body());

    return getVehicles(feed);
  }

  private static List<RawVehicle> getVehicles(GtfsRealtime.FeedMessage feed) {
    List<RawVehicle> result = new ArrayList<>();
    int index = 0;

    for (GtfsRealtime.FeedEntity entity : feed.getEntityList()) {

      if (!entity.hasVehicle()) {
        continue;
      }

      GtfsRealtime.VehiclePosition vp = entity.getVehicle();

      if (!vp.hasPosition()) {
        continue;
      }

      if (index == 10) {
        continue;
      }

      result.add(
          new RawVehicle(
              vp.getVehicle().getId(),
              vp.getTrip().getRouteId(),
              vp.getTrip().getTripId(),
              vp.getStopId(),
              vp.getPosition().getLatitude(),
              vp.getPosition().getLongitude(),
              vp.getTimestamp()));
      index++;
    }
    return result;
  }
}
