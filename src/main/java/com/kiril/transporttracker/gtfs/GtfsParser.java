package com.kiril.transporttracker.gtfs;

import static com.kiril.transporttracker.gtfs.GtfsStaticFileDownloader.GTFS_STATIC_DOWNLOAD_DIRECTORY_PATH;

import com.kiril.transporttracker.models.Route;
import com.kiril.transporttracker.models.Stop;
import com.kiril.transporttracker.models.Trip;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GtfsParser {

  // TODO How about if the routes files is old or have not yet been downloaded?
  public static String TXT_FILE_PATH_ROUTES = GTFS_STATIC_DOWNLOAD_DIRECTORY_PATH + "\\routes.txt";
  public static String TXT_FILE_PATH_STOPS = GTFS_STATIC_DOWNLOAD_DIRECTORY_PATH + "\\stops.txt";
  public static String TXT_FILE_PATH_TRIPS = GTFS_STATIC_DOWNLOAD_DIRECTORY_PATH + "\\trips.txt";

  public List<Route> getRoutes() {
    List<String[]> routesParts = splitFile(TXT_FILE_PATH_ROUTES);

    return routesParts.stream()
        .map(parts -> new Route(parts[0], parts[1], parts[2], parts[3], parts[5]))
        .collect(Collectors.toList());
  }

  public List<Stop> getStops() {
    List<String[]> stopParts = splitFile(TXT_FILE_PATH_STOPS);

    return stopParts.stream()
        .map(
            parts ->
                new Stop(
                    parts[0],
                    parts[1],
                    parts[2],
                    !parts[4].isEmpty() ? Double.parseDouble(parts[4]) : 0.00,
                    !parts[4].isEmpty() ? Double.parseDouble(parts[5]) : 0.00))
        .collect(Collectors.toList());
  }

  public List<Trip> getTrips() {
    List<String[]> tripParts = splitFile(TXT_FILE_PATH_TRIPS);

    return tripParts.stream()
        .map(parts -> new Trip(parts[0], parts[1], parts[2], parts[3], parts[6]))
        .collect(Collectors.toList());
  }

  private List<String[]> splitFile(String textFilePath) {
    List<String[]> result = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(textFilePath))) {
      String line;
      boolean isHeader = true;

      while ((line = reader.readLine()) != null) {
        if (isHeader) {
          isHeader = false;
          continue;
        }
        String[] parts = line.split(",");
        result.add(parts);
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
