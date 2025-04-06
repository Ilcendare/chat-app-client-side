package com.chatroom.app.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class FXMLLoaderUtility {

  private FXMLLoaderUtility() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  // Load FXML files
  public static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(FXMLLoaderUtility.class.getResource("/com/chatroom/app/" + fxml + ".fxml"));
    return fxmlLoader.load();
  }

}
