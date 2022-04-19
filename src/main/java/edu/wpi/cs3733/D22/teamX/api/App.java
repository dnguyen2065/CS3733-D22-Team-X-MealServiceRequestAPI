package edu.wpi.cs3733.D22.teamX.api;

import edu.wpi.cs3733.D22.teamX.api.exceptions.ServiceException;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws ServiceException {
    try {
      new MealRequestAPI()
          .run(0, 0, 0, 0, "edu/wpi/cs3733/D22/teamX/api/stylesheets/default.css", "hello", "world");
    } catch (ServiceException se) {
      throw new ServiceException("MealRequestAPI couldn't run");
    }
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
