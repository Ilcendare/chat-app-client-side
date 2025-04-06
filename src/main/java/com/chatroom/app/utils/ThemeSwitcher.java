package com.chatroom.app.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ThemeSwitcher {
  private static Theme currentTheme;

  // Switch between light and dark themes
  public static void switchTheme(Theme theme) {
    // save the new theme in a cache file
    Properties properties = new Properties();
    try (OutputStream output = new FileOutputStream("src/main/resources/com/chatroom/app/cache/session_info.cache")) {
      properties.setProperty("mode", theme.toString().toLowerCase());
      properties.store(output, null);
    } catch (IOException io) {
      System.err.println("GUIController: switchTheme: " + io.getMessage());
    }

    currentTheme = theme;
    SceneHandler.getScene().getStylesheets()
        .remove(ThemeSwitcher.class.getResource("/com/chatroom/app/styles/light.css").toExternalForm());
    SceneHandler.getScene().getStylesheets()
        .remove(ThemeSwitcher.class.getResource("/com/chatroom/app/styles/dark.css").toExternalForm());
    SceneHandler.getScene().getStylesheets().add(ThemeSwitcher.class
        .getResource("/com/chatroom/app/styles/" + theme.toString().toLowerCase() + ".css").toExternalForm());
  }

  // Get the current theme
  public static Theme getCurrentTheme() {
    return currentTheme;
  }
}
