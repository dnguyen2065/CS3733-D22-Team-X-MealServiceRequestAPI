package edu.wpi.cs3733.D22.teamX.entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MealServiceRequestDAO implements DAO<MealServiceRequest> {
  private static List<MealServiceRequest> mealServiceRequests = new ArrayList<MealServiceRequest>();
  private static String csv = "MealRequests.csv";

  /** creates new MealServiceRequestDAO */
  private MealServiceRequestDAO() {}

  /** Singleton helper Class */
  private static class SingletonHelper {
    private static final MealServiceRequestDAO mealServiceRequestDAO = new MealServiceRequestDAO();
  }

  /**
   * returns the singleton thing
   *
   * @return MealServiceRequestDAO.
   */
  public static MealServiceRequestDAO getDAO() {
    return SingletonHelper.mealServiceRequestDAO;
  }

  @Override
  public List<MealServiceRequest> getAllRecords() {
    return mealServiceRequests;
  }

  @Override
  public MealServiceRequest getRecord(String recordID) {
    // iterate through the list of requests
    for (MealServiceRequest msr : mealServiceRequests) {
      if (msr.getRequestID().equals(recordID)) {
        return msr;
      }
    }
    throw new NoSuchElementException("request does not exist");
  }

  @Override
  public void deleteRecord(MealServiceRequest recordObject) {
    recordObject.getDestination().removeRequest(recordObject);
    // remove from list
    int index = 0; // create index for while loop
    int intitialSize = mealServiceRequests.size(); // get size
    // go thru list
    while (index < mealServiceRequests.size()) {
      if (mealServiceRequests.get(index).equals(recordObject)) {
        mealServiceRequests.remove(index); // remove item at index from list
        index--;
        break; // exit loop
      }
      index++;
    }
  }

  @Override
  public void updateRecord(MealServiceRequest recordObject) {
    if (!recordObject
        .getDestination()
        .equals(getRecord(recordObject.getRequestID()).getDestination())) {
      getRecord(recordObject.getRequestID()).getDestination().removeRequest(recordObject);
      recordObject.getDestination().addRequest(recordObject);
    }
    // add item to list
    int index = 0; // create index for while loop
    int intitialSize = mealServiceRequests.size(); // get size
    // go thru list
    while (index < mealServiceRequests.size()) {
      if (mealServiceRequests.get(index).equals(recordObject)) {
        mealServiceRequests.set(index, recordObject); // update lsr at index position
        break; // exit loop
      }
      index++;
    }
  }

  @Override
  public void addRecord(MealServiceRequest recordObject) {
    // list
    mealServiceRequests.add(recordObject);
    recordObject.getDestination().addRequest(recordObject);
  }

  @Override
  public boolean loadCSV() {
    try {
      LocationDAO locDestination = LocationDAO.getDAO();
      EmployeeDAO emplDAO = EmployeeDAO.getDAO();
      InputStream PMSRCSV = this.getClass().getResourceAsStream(csvFolderPath + csv);
      BufferedReader PMSRCSVReader = new BufferedReader(new InputStreamReader(PMSRCSV));
      PMSRCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = PMSRCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 8) {
          MealServiceRequest PMSRnode =
              new MealServiceRequest(
                  currLine[0],
                  locDestination.getRecord(currLine[1]),
                  currLine[2],
                  emplDAO.getRecord(currLine[3]),
                  currLine[4],
                  currLine[5],
                  currLine[6],
                  currLine[7]);
          mealServiceRequests.add(PMSRnode);
          PMSRnode.getDestination().addRequest(PMSRnode);
        } else {
          System.out.println("MealServiceRequests CSV file formatted improperly");
          System.exit(1);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("MealServiceRequest.CSV not found!");
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public String makeID() {
    int nextIDNum = mealServiceRequests.size() + 1;
    return String.format("PMSR%04d", nextIDNum);
  }
}
