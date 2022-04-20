package edu.wpi.cs3733.D22.teamX.api;

import edu.wpi.cs3733.D22.teamX.api.entity.EmployeeDAO;
import edu.wpi.cs3733.D22.teamX.api.entity.LocationDAO;
import edu.wpi.cs3733.D22.teamX.api.entity.MealServiceRequestDAO;
import edu.wpi.cs3733.D22.teamX.api.exceptions.ServiceException;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MealRequestAPI {
  public static void run(
      int xCoord,
      int yCoord,
      int windowWidth,
      int windowLength,
      String cssPath,
      String destLocationID,
      String originLocationID)
      throws ServiceException {
    try {
      LocationDAO.getDAO().loadCSV();
      EmployeeDAO.getDAO().loadCSV();
      MealServiceRequestDAO.getDAO().loadCSV();
      Parent root = FXMLLoader.load(MealRequestAPI.class.getResource("views/mealRequest.fxml"));
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      String css = App.class.getResource(cssPath).toExternalForm();
      scene.getStylesheets().add(css);
      stage.initModality(Modality.WINDOW_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setTitle("MealRequestAPI");
      stage.setScene(scene);
      stage.setX(xCoord);
      stage.setY(yCoord);
      stage.setWidth(windowWidth);
      stage.setHeight(windowLength);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      throw new ServiceException("MealRequestAPI had a problem starting up");
    }
  }
}
