package com.chatroom.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import com.chatroom.app.member.MemberManager;
import com.chatroom.app.server.MyServer;
import com.chatroom.app.server.ServerAddressHolder;
import com.chatroom.app.server.ServerConnectionHandler;
import com.chatroom.app.utils.FXMLLoaderUtility;
import com.chatroom.app.utils.SceneHandler;
import com.chatroom.app.utils.Theme;
import com.chatroom.app.utils.ThemeSwitcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX Starting point. It launches the app by setting a new scene to the
 * "login.fxml".
 *
 * @author Mahmoud Sayed
 * @version 1.0
 */
public class App extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    /*
     * 1: Handle and connect to the server
     */
    
    try {
      ServerAddressHolder myServer = new MyServer();
      ServerConnectionHandler.getInstance().setServerAddressHolder(myServer);
      ServerConnectionHandler.getInstance().connectServer();
    } catch (RemoteException | NotBoundException e) {
      System.out.println("     >>>>>     App:start  >>  Error during connection to the server");
    }

    /*
     * 2: Create a new scene and load the login page
     */
    Scene scene = new Scene(FXMLLoaderUtility.loadFXML("login"), 1280, 720);
    SceneHandler.setScene(scene);

    /*
     * 3: read the cached file to get the last stored theme
     */
    Properties cachedFile = new Properties();
    try (
        InputStream cacheStream = new FileInputStream("src/main/resources/com/chatroom/app/cache/session_info.cache")) {
      cachedFile.load(cacheStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    /*
     * 4: switch the theme based on the cached file
     */
    String mode = cachedFile.getProperty("mode");
    if (mode == null || mode.equals("light")) {
      ThemeSwitcher.switchTheme(Theme.LIGHT);
    } else {
      ThemeSwitcher.switchTheme(Theme.DARK);
    }

    /*
     * 5: Set the onCloseRequest event to logout and exit the app
     */
    stage.setOnCloseRequest(event -> {
      event.consume();
      if (MemberManager.getMember() != null) {
        try {
          MemberManager.logoutMember();
        } catch (RemoteException e) {
          System.out.println("App:start: Error logging out the member");
        }
      }
      System.exit(0);
    });

    /*
     * 6: Set the title of the application
     */
    stage.setTitle("Chat Room");

    /*
     * 7: Set the resizable of the application
     */
    stage.setResizable(true);

    /**
     * 8: Set the icon of the application
     */
    try {
      InputStream iconIS = App.class.getResourceAsStream("/com/chatroom/app/media/icon.png");
      Image icon = new Image(iconIS);
      stage.getIcons().add(icon);
    } catch (Exception e) {
      System.err.println(" >>>>> App:start >> Error during loading the icon: " + e.getMessage());
    }

    /*
     * 9: Start the application and show the login page
     */
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}