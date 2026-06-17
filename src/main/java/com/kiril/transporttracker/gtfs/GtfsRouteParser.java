package com.kiril.transporttracker.gtfs;

import com.kiril.transporttracker.models.Routes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GtfsRouteParser {

  // TODO Add the below path in application.yaml configuration
  public static String ROUTES_TXT_FILE_PATH =
      "E:\\Programming\\TransportLiveUpdate\\gtfs-static\\routes.txt";

  public List<Routes> processRoutes() {
    List<Routes> result = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(ROUTES_TXT_FILE_PATH))) {
      String line;
      boolean isHeader = true;

      while ((line = reader.readLine()) != null) {
        if (isHeader) {
          isHeader = false;
          continue;
        }
        // TODO add this in the configuration too ","
        String[] parts = line.split(",");
        result.add(new Routes(parts[0], parts[1], parts[2], parts[3], parts[5]));
      }
      return result;
    } catch (IOException e) {
      // TODO Need to think about proper exception handling
      throw new RuntimeException();
    } catch (ArrayIndexOutOfBoundsException e) {
      // TODO Need to think about proper exception handling
      throw new IllegalArgumentException();
    }
  }
}
