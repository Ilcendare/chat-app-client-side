package com.chatroom.app.utils;

import javafx.scene.Scene;
import java.io.IOException;

public class SceneHandler {
  private static Scene scene;

  private SceneHandler() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static void setScene(Scene newScene) {
    scene = newScene;
  }

  public static Scene getScene() {
    return scene;
  }

  public static void switchScene(String fxml) throws IOException {
    scene.setRoot(FXMLLoaderUtility.loadFXML(fxml));
  }
}
