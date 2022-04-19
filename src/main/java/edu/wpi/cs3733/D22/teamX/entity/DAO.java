package edu.wpi.cs3733.D22.teamX.entity;

import java.util.List;

public interface DAO<T> {
  // each T overrides equals method

  String csvFolderPath = "csv/";

  /**
   * Returns a list of type T of all the records.
   *
   * @return a list of all the records in a table.
   */
  List<T> getAllRecords();

  /**
   * Given a recordID, returns a specific record.
   *
   * @param recordID the recordID of the record to be returned.
   * @return the record corresponding to the recordID.
   */
  T getRecord(String recordID);

  /**
   * Deletes a specified record object.
   *
   * @param recordObject the recordObject to be deleted.
   */
  void deleteRecord(T recordObject);

  /**
   * Updates a specified record object. If the record doesn't exist, it is added.
   *
   * @param recordObject the recordObject to be updated or added.
   */
  void updateRecord(T recordObject);

  /**
   * Adds a record object to the database.
   *
   * @param recordObject the recordObject to be added.
   */
  void addRecord(T recordObject);

  /**
   * Loads the data from the csv file into the table
   *
   * @return true if load is successful
   */
  boolean loadCSV();

  /**
   * Returns the next alphanumeric ID string.
   *
   * @return the next alphanumeric ID string.
   */
  String makeID();
}
