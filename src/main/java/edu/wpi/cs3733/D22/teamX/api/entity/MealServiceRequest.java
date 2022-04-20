package edu.wpi.cs3733.D22.teamX.api.entity;

/** Represents a meal service request */
public class MealServiceRequest extends ServiceRequest {
  String mainCourse;
  String side;
  String drink;
  String patientFor;

  public MealServiceRequest() {
    super();
    this.mainCourse = "";
    this.side = "";
    this.drink = "";
    this.patientFor = "";
  }

  public MealServiceRequest(
      String requestID,
      Location destination,
      String status,
      Employee assignee,
      String mainCourse,
      String side,
      String drink,
      String patientFor) {
    super(requestID, destination, status, assignee);
    this.mainCourse = mainCourse;
    this.side = side;
    this.drink = drink;
    this.patientFor = patientFor;
  }

  public String getMainCourse() {
    return mainCourse;
  }

  public void setMainCourse(String mainCourse) {
    this.mainCourse = mainCourse;
  }

  public String getSide() {
    return side;
  }

  public void setSide(String side) {
    this.side = side;
  }

  public String getDrink() {
    return drink;
  }

  public void setDrink(String drink) {
    this.drink = drink;
  }

  public String getPatientFor() {
    return patientFor;
  }

  public void setPatientFor(String patientFor) {
    this.patientFor = patientFor;
  }

  public void setMealType(String mainCourse) {
    this.mainCourse = mainCourse;
  }

  @Override
  public String toString() {
    StringBuilder strr = new StringBuilder("MealServiceRequest ");
    strr.append(getRequestID()).append(": ");
    strr.append(getDestination().getNodeID()).append(", ");
    strr.append(getAssigneeID()).append(", ");
    strr.append(patientFor).append(", ");
    strr.append(mainCourse).append(", ");
    strr.append(side).append(", ");
    strr.append(drink);
    return strr.toString();
  }
}
