package com.chatroom.app.viewcontrollers;

import java.io.IOException;
import java.rmi.RemoteException;

// import com.chatroom.app.animations.TranslateButton;
import com.chatroom.app.animations.InFade;
import com.chatroom.app.animations.OutFade;
import com.chatroom.app.member.MemberManager;
import com.chatroom.app.utils.EventAnimationHandler;
import com.chatroom.app.utils.SceneHandler;
import com.chatroom.app.utils.Theme;
import com.chatroom.app.utils.ThemeSwitcher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class LoginController {
  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loginButton;

  @FXML
  private Text textLog;

  @FXML
  private Label usernameLabel;

  @FXML
  private Label passwordLabel;

  @FXML
  private Label textCreateUser;

  @FXML
  private Button createUserButton;

  @FXML
  private Circle themeButton;

  @FXML
  private Text switchThemeLabel;

  private Color successLogColor;

  private Color errorLogColor;

  /**
   * Initializes the login controller.
   * 
   * This method sets the colors for the text log based on the current theme and
   * sets the fade animation for all buttons and text fields. It also prints a
   * welcome message for the user or a success message if the user has just
   * created a new user.
   */

  @FXML
  private void initialize() {
    // Set text log colors based on the current theme
    if (ThemeSwitcher.getCurrentTheme() == Theme.DARK) {
      successLogColor = Color.LIGHTGREEN;
      errorLogColor = Color.LIGHTYELLOW;
    } else {
      successLogColor = Color.DARKGREEN;
      errorLogColor = Color.FIREBRICK;
    }

    // Set the switch theme label color
    switchThemeLabel.setFill(successLogColor);

    // Set the fade animation for all buttons
    new EventAnimationHandler(new InFade(loginButton), loginButton::setOnMouseEntered);
    new EventAnimationHandler(new InFade(createUserButton), createUserButton::setOnMouseEntered);
    new EventAnimationHandler(new InFade(themeButton), themeButton::setOnMouseEntered);

    new EventAnimationHandler(new OutFade(loginButton), loginButton::setOnMouseExited);
    new EventAnimationHandler(new OutFade(createUserButton), createUserButton::setOnMouseExited);
    new EventAnimationHandler(new OutFade(themeButton), themeButton::setOnMouseExited);

    // Set the fade animation for all text fields
    new EventAnimationHandler(new InFade(usernameField), usernameField::setOnMouseEntered);
    new EventAnimationHandler(new InFade(passwordField), passwordField::setOnMouseEntered);

    new EventAnimationHandler(new OutFade(usernameField), usernameField::setOnMouseExited);
    new EventAnimationHandler(new OutFade(passwordField), passwordField::setOnMouseExited);

    usernameField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          System.out.println("Enter pressed");
          this.login();
        } catch (Exception e) {
          System.out.println("Error ");
        }
      } else {
        textLog.setText("");
      }
    });

    passwordField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        try {
          System.out.println("Enter pressed");
          this.login();
        } catch (Exception e) {
          System.out.println("Error ");
        }
      } else {
        textLog.setText("");
      }
    });

    if (MemberManager.isCreated()) {
      // Print a message to the user after creating a new user
      textLog.setFill(successLogColor);
      textLog.setText("User created successfully");
    } else {
      // Set the text color for the login page
      textLog.setFill(successLogColor);
      textLog.setText("Welcome to the chat room!");
    }
  }

  /**
   * Handles the login process. If the username and password are empty, send a
   * message to the user. Otherwise, check if the member is registered in the
   * server. If the member is registered, login and switch the scene to the
   * "chatui". If the member is not registered, clear the member and send a
   * message to the user.
   * 
   * @throws RemoteException if an error occurs during login
   * @throws IOException     if an error occurs during switching scene
   */
  @FXML
  private void login() {
    // Check if the username and password are empty
    if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
      textLog.setFill(errorLogColor);
      textLog.setText("Please enter your username and password correctly");
    } else {
      if (MemberManager.isCreated()) {
        MemberManager.getMember().getMemberData().setUsername(usernameField.getText());
        MemberManager.getMember().getMemberData().setPassword(passwordField.getText(), false);
        MemberManager.clearIsCreatedFlag();
      } else {
        try {
          // Create a new member with the username and password
          MemberManager.createMember(usernameField.getText(), passwordField.getText());
        } catch (Exception e) {
          System.err.println("     >>>>>     LoginController:login  >>  Can't create a user");
        }
      }
      try {
        // Check if the member is registered in the server
        if (MemberManager.loginMember()) {
          // If the member is registered, login and switch the scene to the chatui
          SceneHandler.switchScene("chatui");
        } else {
          // If the member is not registered, clear the member and send a message to the
          // user
          MemberManager.clearMember();
          textLog.setFill(errorLogColor);
          textLog.setText("The username or password is incorrect");
        }
      } catch (RemoteException e) {
        System.err.println("     >>>>>     LoginController:login  >>  Error during login");
      } catch (IOException e) {
        System.err.println("     >>>>>     LoginController:login  >>  Error during switching scene");
      }
    }
  }

  @FXML
  private void createUser() {
    try {
      SceneHandler.switchScene("create_user");
    } catch (Exception e) {
      System.err.println("     >>>>>     LoginController:createUser  >>  Error during switching scene");
    }
  }

  @FXML
  private void switchTheme() {
    Color oldTextColor = (Color) textLog.getFill();

    if (ThemeSwitcher.getCurrentTheme() == Theme.LIGHT) {
      ThemeSwitcher.switchTheme(Theme.DARK);
      if (oldTextColor == successLogColor) {
        successLogColor = Color.LIGHTGREEN;
        errorLogColor = Color.LIGHTYELLOW;
        textLog.setFill(successLogColor);
      } else if (oldTextColor == errorLogColor) {
        successLogColor = Color.LIGHTGREEN;
        errorLogColor = Color.LIGHTYELLOW;
        textLog.setFill(errorLogColor);
      }
    } else {
      ThemeSwitcher.switchTheme(Theme.LIGHT);
      if (oldTextColor == successLogColor) {
        successLogColor = Color.DARKGREEN;
        errorLogColor = Color.FIREBRICK;
        textLog.setFill(successLogColor);
      } else if (oldTextColor == errorLogColor) {
        successLogColor = Color.DARKGREEN;
        errorLogColor = Color.FIREBRICK;
        textLog.setFill(errorLogColor);
      }
    }
    switchThemeLabel.setFill(successLogColor);
  }
}
