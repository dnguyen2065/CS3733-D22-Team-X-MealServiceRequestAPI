package edu.wpi.cs3733.D22.teamX.api;

import edu.wpi.cs3733.D22.teamX.api.entity.*;
import edu.wpi.cs3733.D22.teamX.api.exceptions.loadSaveFromCSVException;

public class DatabaseCreator {
  // Create impls in order to avoid FK errors
  private static LocationDAO locDAO = LocationDAO.getDAO();
  private static EmployeeDAO emplDAO = EmployeeDAO.getDAO();
  private static MealServiceRequestDAO pmsrDAO = MealServiceRequestDAO.getDAO();

  /** Initializes the database with tables and establishes a connection */
  public static void initializeDB()
      throws
          loadSaveFromCSVException { // method must be called after app startup, so user can choose
    // server type
    System.out.println("-----Testing Apache Derby Embedded Connection-----");
    // checks whether the driver is working
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
      System.exit(1);
    }
    System.out.println("Apache Derby driver registered!");
    if (LocationDAO.getDAO().getAllRecords().size() == 0) {
      dropAllTables();
      createAllTables();
      loadAllCSV();
    }
  }

  /** Saves the data from the database to the appropriate CSV files and closes the database. */
  public static boolean closeDB() throws loadSaveFromCSVException {
    try {
      ConnectionSingleton.getConnectionSingleton().getConnection().close();
      System.out.println("Connection closed successfully");
    } catch (Exception e) {
      System.out.println("No connection to close");
      return false;
    }
    return true;
  }

  /** Drops all database tables */
  public static void dropAllTables() {
    pmsrDAO.dropTable();
    emplDAO.dropTable();
    locDAO.dropTable();
  }

  /** Creates all database tables */
  public static void createAllTables() {
    locDAO.createTable();
    emplDAO.createTable();
    pmsrDAO.createTable();
  }

  /**
   * Reads data from all resource csv files and loads them into the database tables and DAO
   * Implementations
   */
  public static boolean loadAllCSV() throws loadSaveFromCSVException {
    if (!locDAO.loadCSV() || !emplDAO.loadCSV() || !pmsrDAO.loadCSV()) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    return true;
  }

  /**
   * Saves all CSV files to the specified directory
   *
   * @param dirPath path to folder on local machine
   * @return true if all CSV's are successfully saved
   * @throws loadSaveFromCSVException if save does not execute
   */
  public static boolean saveAllCSV(String dirPath) throws loadSaveFromCSVException {
    if (!locDAO.saveCSV(dirPath) || !emplDAO.saveCSV(dirPath) || !pmsrDAO.saveCSV(dirPath)) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    return true;
  }
}
