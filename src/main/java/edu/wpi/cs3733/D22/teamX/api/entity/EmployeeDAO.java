package edu.wpi.cs3733.D22.teamX.api.entity;

import edu.wpi.cs3733.D22.teamX.api.MealRequestAPI;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class EmployeeDAO implements DAO<Employee> {
  private static List<Employee> employees = new ArrayList<Employee>();
  private static String csv = "Employees.csv";

  /** Creates a new EmployeeDAO object. */
  private EmployeeDAO() {}

  /** Singleton Helper Class. */
  private static class SingletonHelper {
    private static final EmployeeDAO employeeDAO = new EmployeeDAO();
  }

  /**
   * Returns the DAO Singleton.
   *
   * @return the DAO Singleton object.
   */
  public static EmployeeDAO getDAO() {
    return SingletonHelper.employeeDAO;
  }

  @Override
  public List<Employee> getAllRecords() {
    return employees;
  }

  @Override
  public Employee getRecord(String recordID) {
    for (Employee element : employees) {
      // if the object has the same nodeID
      if (element.getEmployeeID().equals(recordID)) {
        return element;
      }
    }
    throw new NoSuchElementException("Employee" + recordID + "does not exist");
  }

  @Override
  public void deleteRecord(Employee recordObject) {
    int employeeInd = 0;
    while (employeeInd < employees.size()) {
      if (employees.get(employeeInd).equals(recordObject)) {
        employees.remove(employeeInd);
        break;
      }
      employeeInd++;
    }
  }

  @Override
  public void updateRecord(Employee recordObject) {
    int employeeInd = 0;
    while (employeeInd < employees.size()) {
      if (employees.get(employeeInd).equals(recordObject)) {
        employees.set(employeeInd, recordObject);
        break;
      }
      employeeInd++;
    }
  }

  @Override
  public void addRecord(Employee recordObject) {
    employees.add(recordObject);
  }

  @Override
  public boolean loadCSV() {
    try {
      InputStream emplCSV = MealRequestAPI.class.getResourceAsStream(csvFolderPath + csv);
      BufferedReader emplCSVReader = new BufferedReader(new InputStreamReader(emplCSV));
      emplCSVReader.readLine();
      String nextFileLine;
      while ((nextFileLine = emplCSVReader.readLine()) != null) {
        String[] currLine = nextFileLine.replaceAll("\r\n", "").split(",");
        if (currLine.length == 5) {
          Employee emplNode =
              new Employee(currLine[0], currLine[1], currLine[2], currLine[3], currLine[4]);
          employees.add(emplNode);
        } else {
          System.out.println("Employee CSV file formatted improperly");
          System.exit(1);
        }
      }
      emplCSV.close();
      emplCSVReader.close();
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
    int nextIDFinalNum = this.getAllRecords().size() + 1;
    return String.format("EMPL%04d", nextIDFinalNum);
  }
}
