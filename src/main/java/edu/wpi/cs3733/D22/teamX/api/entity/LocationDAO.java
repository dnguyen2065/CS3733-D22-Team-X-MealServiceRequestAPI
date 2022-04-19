package edu.wpi.cs3733.D22.teamX.api.entity;

import edu.wpi.cs3733.D22.teamX.api.MealRequestAPI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class LocationDAO implements DAO<Location> {
  private static final List<Location> locations = new ArrayList<Location>();
  private static final String csv = "TowerLocationsX.csv";

  /** Creates a new LocationDAO object. */
  private LocationDAO() {}

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final LocationDAO locationDAO = new LocationDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static LocationDAO getDAO() {
    return SingletonHelper.locationDAO;
  }

  @Override
  public List<Location> getAllRecords() {
    return locations;
  }

  @Override
  public Location getRecord(String recordID) {
    // iterate through the list of locations to find the object
    for (Location element : locations) {
      // if the object has the same nodeID
      if (element.getNodeID().equals(recordID)) {
        return element;
      }
    }
    throw new NoSuchElementException("Location does not exist");
  }

  @Override
  public void deleteRecord(Location recordObject) {
    // iterate through the list of locations to find the location passed and remove it from
    // locations
    int locationInd = 0;
    while (locationInd < locations.size()) {
      if (locations.get(locationInd).equals(recordObject)) {
        locations.remove(locationInd);
        locationInd--; // decrement if found
        break;
      }
      locationInd++;
    }
  }

  @Override
  public void updateRecord(Location recordObject) {
    // iterate through the list of locations to find the location passed in and update it in
    // locations
    int locationInd = 0;
    while (locationInd < locations.size()) {
      if (locations.get(locationInd).equals(recordObject)) {
        locations.set(locationInd, recordObject);
        break;
      }
      locationInd++;
    }
  }

  @Override
  public void addRecord(Location recordObject) {
    locations.add(recordObject);
  }

  @Override
  public boolean loadCSV() {
    try {
      InputStream tlCSV = MealRequestAPI.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader tlCSVReader = new BufferedReader(new InputStreamReader(tlCSV));
      tlCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = tlCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 8) {
          Location lnode =
              new Location(
                  currLine[0],
                  Integer.parseInt(currLine[1]),
                  Integer.parseInt(currLine[2]),
                  currLine[3],
                  currLine[4],
                  currLine[5],
                  currLine[6],
                  currLine[7]);
          locations.add(lnode);
        } else {
          System.out.println("TowerLocationsX CSV file formatted improperly");
          System.exit(1);
        }
      }
      tlCSV.close();
      tlCSVReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("File not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    return null;
  }
}
