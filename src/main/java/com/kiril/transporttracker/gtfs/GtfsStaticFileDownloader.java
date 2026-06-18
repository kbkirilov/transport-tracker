package com.kiril.transporttracker.gtfs;

import com.kiril.transporttracker.exceptions.FileDownloadException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GtfsStaticFileDownloader {
  public static String GTFS_STATIC_DOWNLOAD_URL = "https://gtfs.sofiatraffic.bg/api/v1/static";
  public static Path GTFS_STATIC_DOWNLOAD_DIRECTORY_PATH =
      Path.of("E:\\Programming\\PROJECTS\\transport-tracker\\src\\main\\resources\\gtfs-static");
  public static String GTFS_STATIC_FILE_NAME = "gtfs-static.zip";
  public static Path GTFS_STATIC_DOWNLOAD_FILE_PATH =
      Path.of(GTFS_STATIC_DOWNLOAD_DIRECTORY_PATH + "\\" + GTFS_STATIC_FILE_NAME);

  public void downloadAndExtract() throws IOException {
    downloadFile(GTFS_STATIC_DOWNLOAD_URL, GTFS_STATIC_DOWNLOAD_FILE_PATH);
    unzip(GTFS_STATIC_DOWNLOAD_FILE_PATH, GTFS_STATIC_DOWNLOAD_DIRECTORY_PATH);

    Files.deleteIfExists(GTFS_STATIC_DOWNLOAD_FILE_PATH);
  }

  private void downloadFile(String fileUrl, Path destinationFilePath) {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fileUrl)).GET().build();

      HttpResponse<Path> response =
          client.send(request, HttpResponse.BodyHandlers.ofFile(destinationFilePath));

      if (response.statusCode() != HttpStatus.OK.value()) {
        // TODO this does not look good. Improve the handling!
        throw new FileDownloadException(
            "A problem occurred while downloading the file!. Http status code: "
                + response.statusCode());
      }
    } catch (IOException | InterruptedException exception) {
      // TODO to handle this better
      throw new RuntimeException();
    }
  }

  private void unzip(Path zipFilePath, Path targetDir) {
    try {
      Files.createDirectories(GTFS_STATIC_DOWNLOAD_DIRECTORY_PATH);

      try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFilePath))) {

        ZipEntry entry;

        while ((entry = zis.getNextEntry()) != null) {

          Path outputPath = targetDir.resolve(entry.getName()).normalize();

          // Protect against Zip Slip attacks
          if (!outputPath.startsWith(targetDir)) {
            throw new IOException("Invalid zip entry: " + entry.getName());
          }

          if (entry.isDirectory()) {
            Files.createDirectories(outputPath);
          } else {
            Files.createDirectories(outputPath.getParent());

            try (OutputStream os = Files.newOutputStream(outputPath)) {
              zis.transferTo(os);
            }
          }

          zis.closeEntry();
        }
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
